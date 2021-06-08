package br.com.ssp.ematricula.model.domain;

public abstract class EntidadeDominio {
	int id;

	public EntidadeDominio() {
		id = -1;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public boolean isEmpty() {
		return (id == -1) ? true : false;
	}
}
