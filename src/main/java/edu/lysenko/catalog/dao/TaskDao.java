package edu.lysenko.catalog.dao;

import java.util.List;

import edu.lysenko.catalog.entity.Task;

public interface TaskDao {

	void add(Task task);

	Task getById(int id);

	List<Task> getAll();

	int update(Task task);

	void deleteById(int id);
}
