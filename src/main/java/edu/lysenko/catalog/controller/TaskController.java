package edu.lysenko.catalog.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import edu.lysenko.catalog.dao.jdbc.JdbcTaskDao;
import edu.lysenko.catalog.entity.Task;
import edu.lysenko.catalog.service.TaskService;

import static java.util.stream.Collectors.toList;

@Controller
public class TaskController {

	private JdbcTaskDao taskDao;
	private TaskService taskService;
	private static int userId;

	public TaskController(JdbcTaskDao taskDao, TaskService taskService) {
		this.taskDao = taskDao;
		this.taskService = taskService;
	}

	@GetMapping(value = "/task")
	public String menu() {
		return "task";
	}

	@GetMapping(value = "/editTask")
	public ModelAndView edit(@RequestParam("id") int id) {
		ModelAndView model = new ModelAndView("editTask");
		model.addObject("task", taskDao.getById(id));
		return model;
	}

	@GetMapping("/user")
	public ModelAndView list(@RequestParam("id") int id, ModelAndView model) {
		userId = id;
		List<Integer> taskIds = taskDao.getAllTaskIdsByUserId(id).stream().map(userIdTaskId -> {
			return userIdTaskId.getTaskId();
		}).collect(toList());
		List<Task> userTasks = taskIds.stream().map(taskId -> {
			return taskDao.getById(taskId);
		}).collect(toList());
		model.addObject("listTask", userTasks);
		model.setViewName("user");
		return model;
	}

	@GetMapping("/deleteTask")
	public String delete(Model model, @ModelAttribute("task") Task task) {
		return taskService.delete(task, userId);
	}

	@PostMapping("/task")
	public String add(Model model, @ModelAttribute("task") Task task) {
		return taskService.add(task, userId);
	}

	@PostMapping("/updateTask")
	public String update(Model model, @ModelAttribute("task") Task task) {
		return taskService.update(task, userId);
	}

	@GetMapping("/search")
	public ModelAndView search(@RequestParam String keyword) {
		List<Task> result = taskService.search(keyword, userId);
		ModelAndView model = new ModelAndView("search");
		model.addObject("result", result);
		return model;
	}

}
