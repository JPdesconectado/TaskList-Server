package ifsc.tasklist.dbcontrol;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import ifsc.tasklist.dbentities.Project;

public class ProjectDAO implements DAO<Project>{
	
	@Override
	public Project get(String id) {
		EntityManager entityMng = Conn.getEntityManager();
		Project project = entityMng.find(Project.class, id);
		entityMng.close();
		return project;
	}

	@Override
	public List<Project> getAll() {
		List<Project> projects = new ArrayList<Project>();
		EntityManager entityMng = Conn.getEntityManager();
		projects = entityMng.createQuery("select project from Project as project", Project.class).getResultList();
		entityMng.close();
		return projects;

	}

	@Override
	public void add(Project project) {
		EntityManager entityMng = Conn.getEntityManager();
		entityMng.getTransaction().begin();
		entityMng.persist(project);
		entityMng.getTransaction().commit();
		entityMng.close();
	}

	@Override
	public void delete(Project proj) {
		EntityManager entityMng = Conn.getEntityManager();
		entityMng.getTransaction().begin();
		Project projectDB = entityMng.find(Project.class, proj.getTitulo());
		entityMng.remove(projectDB);
		entityMng.getTransaction().commit();
		entityMng.close();

	}

	@Override
	public void update(Project proj) {
		EntityManager entityMng = Conn.getEntityManager();
		entityMng.getTransaction().begin();
		Project projectDB = entityMng.find(Project.class, proj.getTitulo());
		projectDB.setObjetivo(proj.getObjetivo());
		entityMng.getTransaction().commit();
		entityMng.close();

	}

}
