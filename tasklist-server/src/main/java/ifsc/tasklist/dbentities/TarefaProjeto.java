package ifsc.tasklist.dbentities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class TarefaProjeto {

	@Id
	private String titulo;
	private String descricao;
	private String projeto;
	private String data;
	
	public TarefaProjeto() {
		
	}

	public TarefaProjeto(String titulo, String descricao, String projeto, String data) {
		super();
		this.titulo = titulo;
		this.descricao = descricao;
		this.projeto = projeto;
		this.data = data;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getProjeto() {
		return projeto;
	}

	public void setProjeto(String projeto) {
		this.projeto = projeto;
	}
	
	@Override
	public String toString() {
		return "Título: " + titulo + ": " + descricao + " Proj: " + projeto + ". Data: " + data + ".";
	}

	
}
