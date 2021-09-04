package edu.lysenko.catalog.service;

import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import edu.lysenko.catalog.config.WebConfig;
import edu.lysenko.catalog.dao.hibernate.HibernateUserDao;
import edu.lysenko.catalog.entity.Role;
import edu.lysenko.catalog.entity.Task;
import edu.lysenko.catalog.entity.User;

@Service
public class UserService {

	static final String REDIRECT = "redirect:";
	static final String USER = "/user?id=";
	static final String ADMIN = "/admin";
	private static final String REGISTER = "/register";
	private static final String LOGIN = "/login";

	private WebApplicationContext context;

	private HibernateUserDao userDao;
	private TaskService taskService;
	private static int id;

	public UserService(HibernateUserDao userDao, WebApplicationContext context, TaskService taskService) {
		this.context = context;
		DatabasePopulatorUtils.execute(new ResourceDatabasePopulator(new ClassPathResource("schema.sql")),
				context.getBean(WebConfig.class).createSchema());
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
			return REDIRECT + REGISTER;
		}
		return REDIRECT + LOGIN;
	}

	public String update(User user) {
		if (user.getRole() != null && userDao.getById(id).getRole().equals(Role.ADMIN) && !user.getPassword().isEmpty()
				&& !user.getEmail().isEmpty() && !user.getName().isEmpty() && !user.getSurname().isEmpty()) {
			userDao.update(user);
		}
		return REDIRECT + ADMIN;
	}

	public String delete(User user) {
		if (userDao.getById(id).getRole().equals(Role.ADMIN)) {
			User userDb = userDao.getById(user.getId());
			for (Task task : userDb.getTasks()) {
				taskService.delete(task, user.getId());
			}
			userDao.deleteById(userDb.getId());
		}
		return REDIRECT + ADMIN;
	}

	public String authorize(User user) {
		if (!user.getEmail().isEmpty() && !user.getPassword().isEmpty()) {
			User userDb = userDao.findUserByEmailPass(user.getEmail(), user.getPassword());
			if (userDb != null) {
				setId(userDb.getId());
				if (userDb.getRole().name().equals(Role.USER.name())) {
					return REDIRECT + USER + userDb.getId();
				} else if (userDb.getRole().name().equals(Role.ADMIN.name())) {
					return REDIRECT + ADMIN;
				}
			}
		}
		return REDIRECT + LOGIN;
	}

	public static int getId() {
		return id;
	}

	public static void setId(int id) {
		UserService.id = id;
	}
}