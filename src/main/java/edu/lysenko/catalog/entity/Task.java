package edu.lysenko.catalog.entity;

import java.util.Objects;

public class Task {

	private int id;
	private String tag;
	private String title;

	public Task() {
	}

	public Task(String tag, String title) {
		this.tag = tag;
		this.title = title;
	}

	public Task(int id, String tag, String title) {
		this.id = id;
		this.tag = tag;
		this.title = title;
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

	@Override
	public int hashCode() {
		return Objects.hash(id, tag, title);
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
		return id == other.id && Objects.equals(tag, other.tag) && Objects.equals(title, other.title);
	}

	@Override
	public String toString() {
		return "Task [id=" + id + ", tag=" + tag + ", title=" + title + "]";
	}
}
