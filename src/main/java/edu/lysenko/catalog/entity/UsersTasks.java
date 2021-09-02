package edu.lysenko.catalog.entity;

public class UsersTasks {

	private int userId;
	private int taskId;

	public UsersTasks() {
	}

	public UsersTasks(int userId, int taskId) {
		this.userId = userId;
		this.taskId = taskId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}
}
