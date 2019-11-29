package ifsc.tasklist.dbcontrol;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import ifsc.tasklist.dbcontrol.Conn;
import ifsc.tasklist.dbentities.Task;

public class TaskDAO implements DAO<Task>{

	@Override
	public Task get(String id) {
		EntityManager entityMng = Conn.getEntityManager();
		Task task = entityMng.find(Task.class, id);
		entityMng.close();
		return task;
	}

	@Override
	public List<Task> getAll() {
		List<Task> tasks = new ArrayList<Task>();
		EntityManager entityMng = Conn.getEntityManager();
		tasks = entityMng.createQuery("select task from Task as task", Task.class).getResultList();
		entityMng.close();
		return tasks;
	}

	@Override
	public void add(Task task) {
		EntityManager entityMng = Conn.getEntityManager();
		entityMng.getTransaction().begin();
		entityMng.persist(task);
		entityMng.getTransaction().commit();
		entityMng.close();
	}

	@Override
	public void delete(Task tks) {
		EntityManager entityMng = Conn.getEntityManager();
		entityMng.getTransaction().begin();
		Task taskDB = entityMng.find(Task.class, tks.getTitulo());
		entityMng.remove(taskDB);
		entityMng.getTransaction().commit();
		entityMng.close();
	}

	@Override
	public void update(Task tks) {
		EntityManager entityMng = Conn.getEntityManager();
		entityMng.getTransaction().begin();
		Task taskDB = entityMng.find(Task.class, tks.getTitulo());
		taskDB.setDescricao(tks.getDescricao());
		taskDB.setData(tks.getData());
		entityMng.getTransaction().commit();
		entityMng.close();

		
	}

}
