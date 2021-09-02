DROP TABLE IF EXISTS users_tasks;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS tasks;
DROP TABLE IF EXISTS user_task;
DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS task;
CREATE TABLE user(
		id INT PRIMARY KEY AUTO_INCREMENT,
		email VARCHAR(35) UNIQUE,
		password VARCHAR(60),
		name VARCHAR(50),
		surname VARCHAR(50),
		role VARCHAR(30)
		);
CREATE TABLE task(
		id INT PRIMARY KEY AUTO_INCREMENT,
		tag VARCHAR(50) UNIQUE,
		title VARCHAR(100)
		);
CREATE TABLE user_task(
		user_id INT,
		FOREIGN KEY (user_id) REFERENCES user (id),
		task_id INT,
		FOREIGN KEY (task_id) REFERENCES task (id),
		UNIQUE (user_id, task_id)
		);