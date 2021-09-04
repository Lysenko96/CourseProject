package edu.lysenko.catalog.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import edu.lysenko.catalog.dao.hibernate.HibernateTaskDao;
import edu.lysenko.catalog.dao.hibernate.HibernateUserDao;
import edu.lysenko.catalog.entity.Role;
import edu.lysenko.catalog.entity.Task;
import edu.lysenko.catalog.entity.User;

import static edu.lysenko.catalog.service.UserService.REDIRECT;
import static edu.lysenko.catalog.service.UserService.ADMIN;
import static edu.lysenko.catalog.service.UserService.USER;

@Service
public class TaskService {

	private static final String TASK = "/task";
	private static final String EDIT_TASK = "/editTask?id=";

	private HibernateTaskDao taskDao;
	private HibernateUserDao userDao;

	public TaskService(HibernateTaskDao taskDao, HibernateUserDao userDao) {
		this.taskDao = taskDao;
		this.userDao = userDao;
	}

	public String add(Task task, int userId) {
		User userDb = userDao.getById(UserService.getId());
		try {
			if ((UserService.getId() == userId || userDb.getRole().equals(Role.ADMIN)) && !task.getTag().isEmpty()
					&& !task.getTitle().isEmpty()) {
				taskDao.add(task);
				task.setUsers(Arrays.asList(userDao.getById(userId)));
				taskDao.update(task);
			}
			if (userDao.getById(UserService.getId()).getRole().equals(Role.ADMIN)) {
				return REDIRECT + ADMIN;
			} else {
				return REDIRECT + USER + UserService.getId();
			}
		} catch (DuplicateKeyException e) {
			return REDIRECT + TASK;
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
				return REDIRECT + EDIT_TASK + task.getId();
			}
		}
		if (userDao.getById(UserService.getId()).getRole().equals(Role.ADMIN)) {
			return REDIRECT + ADMIN;
		} else {
			return REDIRECT + USER + UserService.getId();
		}

	}

	public String delete(Task task, int userId) {
		User userDb = userDao.getById(UserService.getId());
		if (UserService.getId() == userId || userDb.getRole().equals(Role.ADMIN)) {
			Task taskDb = taskDao.getById(task.getId());
			taskDao.deleteById(taskDb.getId());
		}
		if (userDao.getById(UserService.getId()).getRole().equals(Role.ADMIN)) {
			return REDIRECT + ADMIN;
		} else {
			return REDIRECT + USER + userId;
		}
	}

	public List<Task> search(String tag, int userId) {
		return userDao
				.getById(userId).getTasks().stream().filter(task -> taskDao.searchAllByTag(tag).stream()
						.map(Task::getId).collect(Collectors.toList()).contains(task.getId()))
				.collect(Collectors.toList());

	}
}
