package ifsc.tasklist.dbentities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Project {

	@Id
	private String titulo;
	private String objetivo;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private List<TarefaProjeto> tarefaprojeto = new ArrayList<>();
	
	public Project() {
		
	}
	
	public Project(String titulo, String objetivo) {
		super();
		this.titulo = titulo;
		this.objetivo = objetivo;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getObjetivo() {
		return objetivo;
	}

	public void setObjetivo(String objetivo) {
		this.objetivo = objetivo;
	}

	public void addTarefa(TarefaProjeto tarefa) {
		if (this.tarefaprojeto == null)
			this.tarefaprojeto = new ArrayList<TarefaProjeto>();
		this.tarefaprojeto.add(tarefa);
	}
	
	public List<TarefaProjeto> getTarefaprojeto() {
		return tarefaprojeto;
	}

	public void setTarefaprojeto(List<TarefaProjeto> tarefaprojeto) {
		this.tarefaprojeto = tarefaprojeto;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((objetivo == null) ? 0 : objetivo.hashCode());
		result = prime * result + ((tarefaprojeto == null) ? 0 : tarefaprojeto.hashCode());
		result = prime * result + ((titulo == null) ? 0 : titulo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Project other = (Project) obj;
		if (objetivo == null) {
			if (other.objetivo != null)
				return false;
		} else if (!objetivo.equals(other.objetivo))
			return false;
		if (tarefaprojeto == null) {
			if (other.tarefaprojeto != null)
				return false;
		} else if (!tarefaprojeto.equals(other.tarefaprojeto))
			return false;
		if (titulo == null) {
			if (other.titulo != null)
				return false;
		} else if (!titulo.equals(other.titulo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Título: " + titulo;
	}

}
