package edu.lysenko.catalog.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import edu.lysenko.catalog.dao.jdbc.HibernateTaskDao;
import edu.lysenko.catalog.dao.jdbc.HibernateUserDao;
import edu.lysenko.catalog.entity.Role;
import edu.lysenko.catalog.entity.Task;
import edu.lysenko.catalog.entity.User;

@Service
public class TaskService {

	HibernateTaskDao taskDao;
	HibernateUserDao userDao;

	public TaskService(HibernateTaskDao taskDao, HibernateUserDao userDao) {
		this.taskDao = taskDao;
		this.userDao = userDao;
	}

	public String add(Task task, int userId) {
		User userDb = userDao.getById(UserService.getId());
		try {
			if (UserService.getId() == userId || userDb.getRole().equals(Role.ADMIN)) {
				if (!task.getTag().isEmpty() && !task.getTitle().isEmpty()) {
					taskDao.add(task);
					task.setUsers(Arrays.asList(userDao.getById(userId)));
					taskDao.update(task);
				}
			}
			if (userDao.getById(UserService.getId()).getRole().equals(Role.ADMIN)) {
				return "redirect:/admin";
			} else {
				return "redirect:/user?id=" + UserService.getId();
			}
		} catch (DuplicateKeyException e) {
			return "redirect:/task";
		}
	}

	public String update(Task task, int userId) {
		User userDb = userDao.getById(UserService.getId());
		if (UserService.getId() == userId || userDb.getRole().equals(Role.ADMIN)) {
			if (!task.getTag().isEmpty() && !task.getTitle().isEmpty()) {
				taskDao.update(task);
				task.setUsers(Arrays.asList(userDao.getById(userId)));
				taskDao.update(task);
			} else if (task.getTag().isEmpty() || task.getTitle().isEmpty()) {
				return "redirect:/editTask?id=" + task.getId();
			}
		}
		if (userDao.getById(UserService.getId()).getRole().equals(Role.ADMIN)) {
			return "redirect:/admin";
		} else {
			return "redirect:/user?id=" + UserService.getId();
		}

	}

	public String delete(Task task, int userId) {
		User userDb = userDao.getById(UserService.getId());
		if (UserService.getId() == userId || userDb.getRole().equals(Role.ADMIN)) {
			Task taskDb = taskDao.getById(task.getId());
			taskDao.deleteFromUsersTasksByTaskId(task.getId(), userId);
			taskDao.deleteById(taskDb.getId());
		}
		if (userDao.getById(UserService.getId()).getRole().equals(Role.ADMIN)) {
			return "redirect:/admin";
		} else {
			return "redirect:/user?id=" + userId;
		}
	}

	public List<Task> search(String tag, int userId) {
		List<Integer> taskIds = new ArrayList<>();
		List<Task> filter = new ArrayList<>();
		for (Task task : taskDao.searchAllByTag(tag)) {
			taskIds.add(task.getId());
		}
		for (Task task : userDao.getById(userId).getTasks()) {
			if (taskIds.contains(task.getId())) {
				filter.add(taskDao.getById(task.getId()));
			}
		}
		return filter;
	}
}
