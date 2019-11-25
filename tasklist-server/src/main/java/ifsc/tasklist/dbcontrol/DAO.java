package ifsc.tasklist.dbcontrol;

import java.util.List;

public interface DAO<T> {
	
	public T get(String id);
	
	public List<T> getAll();
	
	public void add(T obj);
	
	public void delete(T obj);
	
	public void update(T obj);
}
