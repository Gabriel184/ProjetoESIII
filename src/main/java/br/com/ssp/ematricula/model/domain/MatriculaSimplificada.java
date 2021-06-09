package br.com.ssp.ematricula.model.domain;

import java.util.GregorianCalendar;

public class MatriculaSimplificada extends EntidadeDominio {
	private String codigo;
	private GregorianCalendar dataCadastro;
	private String nome;
	private String email;
	private String cidade;
	private String curso;
	public MatriculaSimplificada(String codigo, GregorianCalendar dataCadastro, String nome, String email,
			String cidade, String curso) {
		super();
		this.codigo = codigo;
		this.dataCadastro = dataCadastro;
		this.nome = nome;
		this.email = email;
		this.cidade = cidade;
		this.curso = curso;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public GregorianCalendar getDataCadastro() {
		return dataCadastro;
	}
	public void setDataCadastro(GregorianCalendar dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public String getCurso() {
		return curso;
	}
	public void setCurso(String curso) {
		this.curso = curso;
	}
	
}
