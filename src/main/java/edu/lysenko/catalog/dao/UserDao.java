package edu.lysenko.catalog.dao;

import java.util.List;

import edu.lysenko.catalog.entity.User;

public interface UserDao {

	void add(User user);
	
	User getById(int id);
	
	List<User> getAll();
	
	int update(User user);
	
	void deleteById(int id);
}
