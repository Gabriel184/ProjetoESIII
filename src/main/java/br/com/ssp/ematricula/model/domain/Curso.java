package br.com.ssp.ematricula.model.domain;

public class Curso extends EntidadeDominio {
	private String descricao;
	private int duracao;
	private Categoria categoria;
	public Curso() {
		super();
	}
	public Curso(String descricao, int duracao, Categoria categoria) {
		super();
		this.descricao = descricao;
		this.duracao = duracao;
		this.categoria = categoria;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public int getDuracao() {
		return duracao;
	}
	public void setDuracao(int duracao) {
		this.duracao = duracao;
	}
	public Categoria getCategoria() {
		return categoria;
	}
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
		
	
}
