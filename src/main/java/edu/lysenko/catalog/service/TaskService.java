package edu.lysenko.catalog.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import edu.lysenko.catalog.dao.jdbc.JdbcTaskDao;
import edu.lysenko.catalog.dao.jdbc.JdbcUserDao;
import edu.lysenko.catalog.entity.Role;
import edu.lysenko.catalog.entity.Task;
import edu.lysenko.catalog.entity.UsersTasks;

@Service
public class TaskService {

	private JdbcTaskDao taskDao;
	private JdbcUserDao userDao;

	public TaskService(JdbcTaskDao taskDao, JdbcUserDao userDao) {
		this.taskDao = taskDao;
		this.userDao = userDao;
	}

	public String add(Task task, int userId) {
		try {
			if (UserService.getId() == userId || userDao.getById(UserService.getId()).getRole().equals(Role.ADMIN)) {
				if (!task.getTag().isEmpty() && !task.getTitle().isEmpty()
						&& taskDao.findTaskByName(task.getTag()) == null) {
					taskDao.add(task);
					Task taskDb = taskDao.findTaskByName(task.getTag());
					if (userDao.getById(UserService.getId()).getRole().equals(Role.ADMIN)) {
						taskDao.add(userId, taskDb.getId());
					} else {
						taskDao.add(UserService.getId(), taskDb.getId());
					}
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
		Task taskDb = taskDao.findTaskByName(task.getTag());
		if (UserService.getId() == userId || userDao.getById(UserService.getId()).getRole().equals(Role.ADMIN)) {
			if (!task.getTag().isEmpty() && !task.getTitle().isEmpty() && taskDb != null) {
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
		if (UserService.getId() == userId || userDao.getById(UserService.getId()).getRole().equals(Role.ADMIN)) {
			Task taskDb = taskDao.getById(task.getId());
			taskDao.deleteFromUsersTasksByTaskId(task.getId());
			taskDao.deleteById(taskDb.getId());
		}
		if (userDao.getById(UserService.getId()).getRole().equals(Role.ADMIN)) {
			return "redirect:/admin";
		} else {
			return "redirect:/user?id=" + userId;
		}
	}

	public List<Task> search(String keyword, int userId) {
		List<Integer> taskIds = new ArrayList<>();
		List<Task> filter = new ArrayList<>();
		for (Task task : taskDao.searchAllByTag(keyword)) {
			taskIds.add(task.getId());
		}
		for (UsersTasks usersTasks : taskDao.getAllTaskIdsByUserId(userId)) {
			if (taskIds.contains(usersTasks.getTaskId())) {
				filter.add(taskDao.getById(usersTasks.getTaskId()));
			}
		}
		return filter;
	}
}
