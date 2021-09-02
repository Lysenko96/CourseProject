package edu.lysenko.catalog.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import edu.lysenko.catalog.dao.hibernate.HibernateUserDao;
import edu.lysenko.catalog.entity.User;
import edu.lysenko.catalog.service.UserService;

@Controller
public class UserController {

	private HibernateUserDao userDao;
	private UserService userService;

	public UserController(HibernateUserDao userDao, UserService userService) {
		this.userDao = userDao;
		this.userService = userService;
	}

	@GetMapping(value = "/register")
	public String menuRegister() {
		return "register";
	}

	@GetMapping(value = "/login")
	public String menuLogin() {
		return "login";
	}

	@GetMapping("/admin")
	public ModelAndView list(ModelAndView model) {
		List<User> listUser = userDao.getAll();
		model.addObject("listUser", listUser);
		model.setViewName("admin");
		return model;
	}

	@GetMapping(value = "/edit")
	public ModelAndView edit(@RequestParam("id") int id) {
		User user = userDao.getById(id);
		user.setPassword("");
		ModelAndView model = new ModelAndView("edit");
		model.addObject("user", user);
		return model;
	}

	@GetMapping(value = "/delete")
	public String delete(Model model, @ModelAttribute("user") User user) {
		return userService.delete(user);
	}

	@PostMapping(value = "/login")
	public String login(Model model, @ModelAttribute("user") User user) {
		return userService.authorize(user);
	}

	@PostMapping(value = "/register")
	public String register(Model model, @ModelAttribute("user") User user) {
		return userService.add(user);
	}

	@PostMapping(value = "/update")
	public String update(Model model, @ModelAttribute("user") User user) {
		return userService.update(user);
	}
}