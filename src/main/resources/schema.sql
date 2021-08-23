DROP TABLE IF EXISTS users_tasks;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS tasks;
CREATE TABLE users(
		id INT PRIMARY KEY AUTO_INCREMENT,
		email VARCHAR(35) UNIQUE,
		password VARCHAR(60),
		name VARCHAR(50),
		surname VARCHAR(50),
		role VARCHAR(30)
		);
CREATE TABLE tasks(
		id INT PRIMARY KEY AUTO_INCREMENT,
		tag VARCHAR(50) UNIQUE,
		title VARCHAR(100)
		);
CREATE TABLE users_tasks(
		user_id INT,
		FOREIGN KEY (user_id) REFERENCES users (id),
		task_id INT,
		FOREIGN KEY (task_id) REFERENCES tasks (id),
		UNIQUE (user_id, task_id)
		);
