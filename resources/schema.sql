DROP TABLE IF EXISTS users_tasks;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS tasks;
CREATE TABLE users(
		id INT AUTO_INCREMENT PRIMARY KEY,
		email VARCHAR(35) UNIQUE,
		password VARCHAR(32),
		name VARCHAR(50),
		surname VARCHAR(50)
		);
CREATE TABLE tasks(
		id INT AUTO_INCREMENT PRIMARY KEY,
		tag VARCHAR(50) UNIQUE,
		title VARCHAR(50)
		);
CREATE TABLE users_tasks (
		user_id INT,
		FOREIGN KEY (user_id) REFERENCES users (id),
		task_id INT,
		FOREIGN KEY (task_id) REFERENCES tasks (id),
		UNIQUE(user_id, task_id)
		);