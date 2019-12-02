package ifsc.tasklist.dbcontrol;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import ifsc.tasklist.dbentities.Feedback;

public class FeedbackDAO implements DAO<Feedback>{

	@Override
	public Feedback get(String id) {
		EntityManager entityMng = Conn.getEntityManager();
		Feedback fb = entityMng.find(Feedback.class, id);
		entityMng.close();
		return fb;
	}

	@Override
	public List<Feedback> getAll() {
		List<Feedback> fbs = new ArrayList<Feedback>();
		EntityManager entityMng = Conn.getEntityManager();
		fbs = entityMng.createQuery("select feedback from Feedback as feedback", Feedback.class).getResultList();
		entityMng.close();
		return fbs;
	}

	@Override
	public void add(Feedback obj) {
		EntityManager entityMng = Conn.getEntityManager();
		entityMng.getTransaction().begin();
		entityMng.persist(obj);
		entityMng.getTransaction().commit();
		entityMng.close();
	}

	@Override
	public void delete(Feedback obj) {
		EntityManager entityMng = Conn.getEntityManager();
		entityMng.getTransaction().begin();
		Feedback fbDB = entityMng.find(Feedback.class, obj.getUser());
		entityMng.remove(fbDB);
		entityMng.getTransaction().commit();
		entityMng.close();
	}

	@Override
	public void update(Feedback obj) {
		EntityManager entityMng = Conn.getEntityManager();
		entityMng.getTransaction().begin();
		Feedback fbDB = entityMng.find(Feedback.class, obj.getUser());
		fbDB.setNota(obj.getNota());
		fbDB.setGostou(obj.isGostou());
		fbDB.setComentario(obj.getComentario());
		entityMng.getTransaction().commit();
		entityMng.close();
	}

}
