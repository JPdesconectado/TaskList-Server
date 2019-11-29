package ifsc.tasklist.dbcontrol;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import ifsc.tasklist.dbentities.TarefaProjeto;


public class TarefaProjetoDAO implements DAO<TarefaProjeto>{

@Override
public TarefaProjeto get(String id) {
	EntityManager entityMng = Conn.getEntityManager();
	TarefaProjeto tafproj = entityMng.find(TarefaProjeto.class, id);
	entityMng.close();
	return tafproj;
}

@Override
public List<TarefaProjeto> getAll() {
	List<TarefaProjeto> tarefaprojeto = new ArrayList<TarefaProjeto>();
	EntityManager entityMng = Conn.getEntityManager();
	tarefaprojeto  = entityMng.createQuery("select tarefaprojeto from TarefaProjeto as tarefaprojeto", TarefaProjeto.class).getResultList();
	entityMng.close();
	return tarefaprojeto;
}

@Override
public void add(TarefaProjeto obj) {
	EntityManager entityMng = Conn.getEntityManager();
	entityMng.getTransaction().begin();
	entityMng.persist(obj);
	entityMng.getTransaction().commit();
	entityMng.close();

}

@Override
public void delete(TarefaProjeto obj) {
	EntityManager entityMng = Conn.getEntityManager();
	entityMng.getTransaction().begin();
	TarefaProjeto tpDB = entityMng.find(TarefaProjeto.class, obj.getTitulo());
	entityMng.remove(tpDB);
	entityMng.getTransaction().commit();
	entityMng.close();
}

@Override
public void update(TarefaProjeto obj) {
	EntityManager entityMng = Conn.getEntityManager();
	entityMng.getTransaction().begin();
	TarefaProjeto tpDB = entityMng.find(TarefaProjeto.class, obj.getTitulo());
	tpDB.setDescricao(obj.getDescricao());
	tpDB.setData(obj.getData());
	tpDB.setProjeto(obj.getProjeto());
	entityMng.getTransaction().commit();
	entityMng.close();

}


}
