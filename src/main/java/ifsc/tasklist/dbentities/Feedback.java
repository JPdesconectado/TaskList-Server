package ifsc.tasklist.dbentities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Feedback {

	@Id
	private String user;
	
	private int nota;
	private boolean gostou;
	private String comentario;
	
	public Feedback() {
		
	}

	public Feedback(String user, int nota, boolean gostou, String comentario) {
		super();
		this.user = user;
		this.nota = nota;
		this.gostou = gostou;
		this.comentario = comentario;
	}

	public int getNota() {
		return nota;
	}

	public void setNota(int nota) {
		this.nota = nota;
	}

	public boolean isGostou() {
		return gostou;
	}

	public void setGostou(boolean gostou) {
		this.gostou = gostou;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((comentario == null) ? 0 : comentario.hashCode());
		result = prime * result + (gostou ? 1231 : 1237);
		result = prime * result + nota;
		result = prime * result + ((user == null) ? 0 : user.hashCode());
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
		Feedback other = (Feedback) obj;
		if (comentario == null) {
			if (other.comentario != null)
				return false;
		} else if (!comentario.equals(other.comentario))
			return false;
		if (gostou != other.gostou)
			return false;
		if (nota != other.nota)
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Feedback [user=" + user + ", nota=" + nota + ", gostou=" + gostou + ", comentario=" + comentario + "]";
	}

	
	
	
}
