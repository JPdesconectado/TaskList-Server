package ifsc.tasklist.dbcontrol;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import ifsc.tasklist.dbentities.Goals;
public class GoalsDAO implements DAO<Goals>{

	@Override
	public Goals get(String id) {
		EntityManager entityMng = Conn.getEntityManager();
		Goals goal = entityMng.find(Goals.class, id);
		entityMng.close();
		return goal;
	}

	@Override
	public List<Goals> getAll() {
		List<Goals> goals = new ArrayList<Goals>();
		EntityManager entityMng = Conn.getEntityManager();
		goals = entityMng.createQuery("select goals from Goals as goals", Goals.class).getResultList();
		entityMng.close();
		return goals;
	}

	@Override
	public void add(Goals obj) {
		EntityManager entityMng = Conn.getEntityManager();
		entityMng.getTransaction().begin();
		entityMng.persist(obj);
		entityMng.getTransaction().commit();
		entityMng.close();
	}

	@Override
	public void delete(Goals obj) {
		EntityManager entityMng = Conn.getEntityManager();
		entityMng.getTransaction().begin();
		Goals goalDB = entityMng.find(Goals.class, obj.getNomeUser());
		entityMng.remove(goalDB);
		entityMng.getTransaction().commit();
		entityMng.close();
	}

	@Override
	public void update(Goals obj) {
		EntityManager entityMng = Conn.getEntityManager();
		entityMng.getTransaction().begin();
		Goals goalDB = entityMng.find(Goals.class, obj.getNomeUser());
		goalDB.setMetaTarefaCumprida(obj.getMetaTarefaCumprida());
		goalDB.setMetaProjetoCumprida(obj.getMetaProjetoCumprida());
		goalDB.setMetaTPCumprida(obj.getMetaTPCumprida());
		goalDB.setObjDiario(obj.getObjDiario());
		entityMng.getTransaction().commit();
		entityMng.close();
	}

}
