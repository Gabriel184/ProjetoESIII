package br.com.ssp.ematricula.model.domain;

import java.util.GregorianCalendar;

public class Matricula extends EntidadeDominio {
	private String codigo;
	private GregorianCalendar dataCadastro;
	private String turma;
	private Aluno aluno;
	private Curso curso;
	public Matricula() {
		super();
	}
	public Matricula(String codigo, GregorianCalendar dataCadastro, String turma, Aluno aluno, Curso curso) {
		super();
		this.codigo = codigo;
		this.dataCadastro = dataCadastro;
		this.turma = turma;
		this.aluno = aluno;
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
	public String getTurma() {
		return turma;
	}
	public void setTurma(String turma) {
		this.turma = turma;
	}
	public Aluno getAluno() {
		return aluno;
	}
	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}
	public Curso getCurso() {
		return curso;
	}
	public void setCurso(Curso curso) {
		this.curso = curso;
	}
	
	
}