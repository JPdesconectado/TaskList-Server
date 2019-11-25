package ifsc.tasklist.dbcontrol;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import ifsc.tasklist.dbentities.User;

public class UserDAO implements DAO<User>{

	@Override
	public User get(String id) {
		EntityManager entityMng = Conn.getEntityManager();
		User user = entityMng.find(User.class, id);
		entityMng.close();
		return user;
	}

	@Override
	public List<User> getAll() {
		List<User> users = new ArrayList<User>();
		EntityManager entityMng = Conn.getEntityManager();
		users = entityMng.createQuery("select user from User as user", User.class).getResultList();
		entityMng.close();
		return users;
	}

	@Override
	public void add(User obj) {
		EntityManager entityMng = Conn.getEntityManager();
		entityMng.getTransaction().begin();
		entityMng.persist(obj);
		entityMng.getTransaction().commit();
		entityMng.close();
	}

	@Override
	public void delete(User obj) {
		EntityManager entityMng = Conn.getEntityManager();
		entityMng.getTransaction().begin();
		User userDB = entityMng.find(User.class, obj.getUsuario());
		entityMng.remove(userDB);
		entityMng.getTransaction().commit();
		entityMng.close();
	}

	@Override
	public void update(User obj) {
		EntityManager entityMng = Conn.getEntityManager();
		entityMng.getTransaction().begin();
		User userDB = entityMng.find(User.class, obj.getUsuario());
		userDB.setSenha(obj.getSenha());
		entityMng.getTransaction().commit();
		entityMng.close();
	}

}
