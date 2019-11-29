package ifsc.tasklist.dbentities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Goals {

	@Id
	private String nomeUser;
	private String metaTarefaCumprida;
	private String metaProjetoCumprida;
	private String metaTPCumprida;
	private int objDiario;
	
	public Goals() {
		
	}

	public Goals(String nomeUser, String metaTarefaCumprida, String metaProjetoCumprida, String metaTPCumprida,
			int objDiario) {
		super();
		this.nomeUser = nomeUser;
		this.metaTarefaCumprida = metaTarefaCumprida;
		this.metaProjetoCumprida = metaProjetoCumprida;
		this.metaTPCumprida = metaTPCumprida;
		this.objDiario = objDiario;
	}

	public String getNomeUser() {
		return nomeUser;
	}

	public void setNomeUser(String nomeUser) {
		this.nomeUser = nomeUser;
	}

	public String getMetaTarefaCumprida() {
		return metaTarefaCumprida;
	}

	public void setMetaTarefaCumprida(String metaTarefaCumprida) {
		this.metaTarefaCumprida = metaTarefaCumprida;
	}

	public String getMetaProjetoCumprida() {
		return metaProjetoCumprida;
	}

	public void setMetaProjetoCumprida(String metaProjetoCumprida) {
		this.metaProjetoCumprida = metaProjetoCumprida;
	}

	public String getMetaTPCumprida() {
		return metaTPCumprida;
	}

	public void setMetaTPCumprida(String metaTPCumprida) {
		this.metaTPCumprida = metaTPCumprida;
	}

	public int getObjDiario() {
		return objDiario;
	}

	public void setObjDiario(int objDiario) {
		this.objDiario = objDiario;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((metaProjetoCumprida == null) ? 0 : metaProjetoCumprida.hashCode());
		result = prime * result + ((metaTPCumprida == null) ? 0 : metaTPCumprida.hashCode());
		result = prime * result + ((metaTarefaCumprida == null) ? 0 : metaTarefaCumprida.hashCode());
		result = prime * result + ((nomeUser == null) ? 0 : nomeUser.hashCode());
		result = prime * result + objDiario;
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
		Goals other = (Goals) obj;
		if (metaProjetoCumprida == null) {
			if (other.metaProjetoCumprida != null)
				return false;
		} else if (!metaProjetoCumprida.equals(other.metaProjetoCumprida))
			return false;
		if (metaTPCumprida == null) {
			if (other.metaTPCumprida != null)
				return false;
		} else if (!metaTPCumprida.equals(other.metaTPCumprida))
			return false;
		if (metaTarefaCumprida == null) {
			if (other.metaTarefaCumprida != null)
				return false;
		} else if (!metaTarefaCumprida.equals(other.metaTarefaCumprida))
			return false;
		if (nomeUser == null) {
			if (other.nomeUser != null)
				return false;
		} else if (!nomeUser.equals(other.nomeUser))
			return false;
		if (objDiario != other.objDiario)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Goals [nomeUser=" + nomeUser + ", metaTarefaCumprida=" + metaTarefaCumprida + ", metaProjetoCumprida="
				+ metaProjetoCumprida + ", metaTPCumprida=" + metaTPCumprida + ", objDiario=" + objDiario + "]";
	}

	
	
	
	
}
