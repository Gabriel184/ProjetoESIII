package br.com.ssp.ematricula.model.domain;

public class Categoria extends EntidadeDominio {
	private String descricao;
	
	public Categoria() {
		super();
	}

	public Categoria(String descricao) {
		super();
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
}
