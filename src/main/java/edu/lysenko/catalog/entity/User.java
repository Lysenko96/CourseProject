package edu.lysenko.catalog.entity;

import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String email;
	private String password;
	private String name;
	private String surname;
	@ManyToMany(mappedBy = "users", fetch = FetchType.EAGER)
	private List<Task> tasks;
	private Role role;

	public User() {
	}

	public User(String email, String password, String name, String surname, List<Task> tasks, Role role) {
		this.email = email;
		this.password = password;
		this.name = name;
		this.tasks = tasks;
		this.surname = surname;
		this.role = role;
	}

	public User(int id, String email, String password, String name, String surname, List<Task> tasks, Role role) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.name = name;
		this.tasks = tasks;
		this.surname = surname;
		this.role = role;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	public int hashCode() {
		return Objects.hash(email, id, name, password, role, surname, tasks);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof User)) {
			return false;
		}
		User other = (User) obj;
		return Objects.equals(email, other.email) && id == other.id && Objects.equals(name, other.name)
				&& Objects.equals(password, other.password) && role == other.role
				&& Objects.equals(surname, other.surname) && Objects.equals(tasks, other.tasks);
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", password=" + password + ", name=" + name + ", surname="
				+ surname + ", tasks=" + tasks + ", role=" + role + "]";
	}

}