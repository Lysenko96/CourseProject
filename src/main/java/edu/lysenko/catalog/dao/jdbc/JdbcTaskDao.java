package edu.lysenko.catalog.dao.jdbc;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import edu.lysenko.catalog.dao.TaskDao;
import edu.lysenko.catalog.entity.Task;
import edu.lysenko.catalog.entity.UsersTasks;

@Component
public class JdbcTaskDao implements TaskDao {

	private static final String ADD_TASK = "INSERT INTO tasks (tag, title) VALUES (?,?)";
	private static final String GET_TASK = "SELECT * FROM tasks WHERE id=?";
	private static final String GET_ALL_TASKS = "SELECT * FROM tasks";
	private static final String UPDATE_TASK = "UPDATE tasks SET tag=?, title=? WHERE id=?";
	private static final String DELETE_TASK = "DELETE FROM tasks WHERE id=?";
	private static final String GET_TASK_BY_TAG = "SELECT * FROM tasks WHERE tag=?";
	private static final String ADD_TASK_USER_ID = "INSERT INTO users_tasks(user_id, task_id) VALUES (?,?)";
	private static final String GET_ALL_TASK_ID_BY_USER_ID = "SELECT * FROM users_tasks WHERE user_id=?";
	private static final String DELETE_TASK_FROM_USERSTASKS_BY_TASK_ID = "DELETE FROM users_tasks WHERE task_id=?";
	private static final String SEARCH_TASK_BY_TAG = "SELECT * FROM tasks WHERE tag LIKE '%' ? '%'";

	private Logger log = Logger.getLogger(JdbcTaskDao.class.getName());

	private JdbcTemplate jdbcTemplate;
	private BeanPropertyRowMapper<Task> taskMapper = BeanPropertyRowMapper.newInstance(Task.class);

	public JdbcTaskDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<UsersTasks> getAllTaskIdsByUserId(int id) {
		return jdbcTemplate.query(GET_ALL_TASK_ID_BY_USER_ID, BeanPropertyRowMapper.newInstance(UsersTasks.class), id);
	}

	public void deleteFromUsersTasksByTaskId(int id) {
		jdbcTemplate.update(DELETE_TASK_FROM_USERSTASKS_BY_TASK_ID, id);
	}

	public void add(int userId, int taskId) {
		jdbcTemplate.update(connection -> {
			PreparedStatement statement = connection.prepareStatement(ADD_TASK_USER_ID);
			statement.setInt(1, userId);
			statement.setInt(2, taskId);
			return statement;
		});
	}

	public List<Task> searchAllByTag(String keyword) {
		return jdbcTemplate.query(SEARCH_TASK_BY_TAG, taskMapper, keyword);
	}

	@Override
	public void add(Task task) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(connection -> {
			PreparedStatement statement = connection.prepareStatement(ADD_TASK, new String[] { "id" });
			statement.setString(1, task.getTag());
			statement.setString(2, task.getTitle());
			return statement;
		}, keyHolder);
	}

	@Override
	public Task getById(int id) {
		return jdbcTemplate.queryForObject(GET_TASK, taskMapper, id);
	}

	@Override
	public List<Task> getAll() {
		return jdbcTemplate.query(GET_ALL_TASKS, taskMapper);
	}

	@Override
	public int update(Task task) {
		jdbcTemplate.update(UPDATE_TASK, task.getTag(), task.getTitle(), task.getId());
		return task.getId();
	}

	@Override
	public void deleteById(int id) {
		jdbcTemplate.update(DELETE_TASK, id);
	}

	public Task findTaskByName(String tag) {
		try {
			return jdbcTemplate.queryForObject(GET_TASK_BY_TAG, taskMapper, tag);
		} catch (EmptyResultDataAccessException e) {
			log.info("No task in db");
		}
		return null;
	}

}
