package edu.lysenko.catalog.service;

import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import edu.lysenko.catalog.config.WebConfig;
import edu.lysenko.catalog.dao.hibernate.HibernateUserDao;
import edu.lysenko.catalog.entity.Role;
import edu.lysenko.catalog.entity.Task;
import edu.lysenko.catalog.entity.User;

@Service
public class UserService {

	WebApplicationContext context;

	private HibernateUserDao userDao;
	private TaskService taskService;
	private static int id;

	public UserService(HibernateUserDao userDao, WebApplicationContext context, TaskService taskService) {
		this.context = context;
		this.context.getBean(WebConfig.class).createSchema();
		this.userDao = userDao;
		this.taskService = taskService;
	}

	public String add(User user) {
		if (user.getRole() != null && !user.getEmail().isEmpty() && !user.getPassword().isEmpty()
				&& !user.getName().isEmpty() && !user.getSurname().isEmpty()) {
			userDao.add(user);
		}
		if (user.getRole() == null || user.getPassword().isEmpty() || user.getName().isEmpty()
				|| user.getSurname().isEmpty() || user.getEmail().isEmpty() || user.getRole().name().isEmpty()) {
			return "redirect:/register";
		}
		return "redirect:/login";
	}

	public String update(User user) {
		if (user.getRole() != null && userDao.getById(id).getRole().equals(Role.ADMIN) && !user.getPassword().isEmpty()
				&& !user.getEmail().isEmpty() && !user.getName().isEmpty() && !user.getSurname().isEmpty()) {
			userDao.update(user);
		}
		return "redirect:/admin";
	}

	public String delete(User user) {
		if (userDao.getById(id).getRole().equals(Role.ADMIN)) {
			User userDb = userDao.getById(user.getId());
			for (Task task : userDb.getTasks()) {
				taskService.delete(task, user.getId());
			}
			userDao.deleteById(userDb.getId());
		}
		return "redirect:/admin";
	}

	public String authorize(User user) {
		if (!user.getEmail().isEmpty() && !user.getPassword().isEmpty()) {
			User userDb = userDao.findUserByEmailPass(user.getEmail(), user.getPassword());
			if (userDb != null) {
				id = userDb.getId();
				if (userDb.getRole().name().equals(Role.USER.name())) {
					return "redirect:/user?id=" + userDb.getId();
				} else if (userDb.getRole().name().equals(Role.ADMIN.name())) {
					return "redirect:/admin";
				}
			}
		}
		return "redirect:/login";
	}

	public static int getId() {
		return id;
	}

	public static void setId(int id) {
		UserService.id = id;
	}
}