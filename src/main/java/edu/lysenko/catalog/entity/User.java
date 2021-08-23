package edu.lysenko.catalog.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

	private int id;
	private String email;
	private String password;
	private String name;
	private String surname;
	private List<Task> tasks;
	private Role role;

	public User(String email, String password, String name, String surname, List<Task> tasks, Role role) {
		this.email = email;
		this.password = password;
		this.name = name;
		this.tasks = tasks;
		this.surname = surname;
		this.role = role;
	}
}