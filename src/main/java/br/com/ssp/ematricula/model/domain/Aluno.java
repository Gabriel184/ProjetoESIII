package br.com.ssp.ematricula.model.domain;

import java.util.GregorianCalendar;

public class Aluno extends EntidadeDominio {
	private String nome;
	private String email;
	private String cpf;
	private String telefone;
	private GregorianCalendar dataNascimento;
	private Endereco endereco;
	public Aluno(String nome, String email, String cpf, String telefone, GregorianCalendar dataNascimento,
			Endereco endereco) {
		super();
		this.nome = nome;
		this.email = email;
		this.cpf = cpf;
		this.telefone = telefone;
		this.dataNascimento = dataNascimento;
		this.endereco = endereco;
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
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public GregorianCalendar getDataNascimento() {
		return dataNascimento;
	}
	public void setDataNascimento(GregorianCalendar dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	public Endereco getEndereco() {
		return endereco;
	}
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	
	public boolean isEmpty() {
		if(nome.equals("") &&
				email.equals("") &&
				cpf.equals("") &&
				telefone.equals("") &&
				dataNascimento == null &&
				endereco.isEmpty())
			return true;
		return false;
	}
	
}
