package edu.lysenko.catalog.entity;

import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "task")
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String tag;
	private String title;
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_task", joinColumns = @JoinColumn(name = "task_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
	private List<User> users;

	public Task() {
	}

	public Task(String tag, String title, List<User> users) {
		this.tag = tag;
		this.title = title;
		this.users = users;
	}

	public Task(int id, String tag, String title, List<User> users) {
		this.id = id;
		this.tag = tag;
		this.title = title;
		this.users = users;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, tag, title, users);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Task)) {
			return false;
		}
		Task other = (Task) obj;
		return id == other.id && Objects.equals(tag, other.tag) && Objects.equals(title, other.title)
				&& Objects.equals(users, other.users);
	}

	@Override
	public String toString() {
		return "Task [id=" + id + ", tag=" + tag + ", title=" + title + "]";
	}
}