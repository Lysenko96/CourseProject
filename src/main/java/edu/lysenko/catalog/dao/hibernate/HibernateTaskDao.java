package edu.lysenko.catalog.dao.jdbc;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import edu.lysenko.catalog.dao.TaskDao;
import edu.lysenko.catalog.entity.Task;
import edu.lysenko.catalog.entity.User;
import edu.lysenko.catalog.util.HibernateUtil;

@Component
public class HibernateTaskDao implements TaskDao {

	public void deleteFromUsersTasksByTaskId(int id, int userId) {
		Transaction transaction = null;
		Task task = null;
		User user = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			task = session.get(Task.class, id);
			user = session.get(User.class, userId);
			List<Integer> userNumberInList = new ArrayList<>();
			for (User aUser : task.getUsers()) {
				userNumberInList.add(task.getUsers().indexOf(aUser));
			}
			int index = task.getUsers().indexOf(user);
			task.getUsers().remove((int) userNumberInList.get(index));
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
	}

	public List<Task> searchAllByTag(String tag) {
		Transaction transaction = null;
		List<Task> tasks = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			Query query = session.createQuery("from Task where tag LIKE :tag");
			query.setParameter("tag", "%" + tag + "%");
			tasks = (List<Task>) query.getResultList();
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
		return tasks;
	}

	@Override
	public void add(Task task) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.save(task);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
	}

	@Override
	public Task getById(int id) {
		Transaction transaction = null;
		Task task = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			task = session.get(Task.class, id);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
		return task;
	}

	@Override
	public List<Task> getAll() {
		Transaction transaction = null;
		List<Task> tasks = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			CriteriaBuilder cb = session.getCriteriaBuilder();
			CriteriaQuery<Task> cq = cb.createQuery(Task.class);
			Root<Task> rootEntry = cq.from(Task.class);
			CriteriaQuery<Task> all = cq.select(rootEntry);
			TypedQuery<Task> allQuery = session.createQuery(all);
			tasks = allQuery.getResultList();
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
		return tasks;
	}

	@Override
	public int update(Task task) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.update(task);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
		return task.getId();
	}

	@Override
	public void deleteById(int id) {
		Transaction transaction = null;
		Task task = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			task = session.get(Task.class, id);
			session.delete(task);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
	}

	public Task findTaskByTag(String tag) {
		Transaction transaction = null;
		Task task = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			Query query = session.createQuery("from Task where tag =:tag");
			query.setParameter("tag", tag);
			task = (Task) query.getResultList();
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
		return task;
	}

}
