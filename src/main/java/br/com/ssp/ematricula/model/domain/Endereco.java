package br.com.ssp.ematricula.model.domain;

public class Endereco extends EntidadeDominio {
	private String codigo;
	private String cep;
	private int numero;
	private String logradouro;
	private String complemento;
	private String bairro;
	private Cidade cidade;
	public Endereco(String codigo, String cep, int numero, String logradouro, String complemento, String bairro,
			Cidade cidade) {
		super();
		this.codigo = codigo;
		this.cep = cep;
		this.numero = numero;
		this.logradouro = logradouro;
		this.complemento = complemento;
		this.bairro = bairro;
		this.cidade = cidade;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	public String getLogradouro() {
		return logradouro;
	}
	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}
	public String getComplemento() {
		return complemento;
	}
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	public Cidade getCidade() {
		return cidade;
	}
	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}
	
	public boolean isEmpty() {
		if(this.codigo.equals("") && 
				this.cep.equals("") &&
				this.numero == Integer.MIN_VALUE &&
				this.logradouro.equals("") &&
				this.complemento.equals("") &&
				this.bairro.equals("") &&
				this.cidade.getDescricao().equals("") &&
				this.cidade.getEstado().getDescricao().equals(""))
			return true;
		return false;
	}
	
}
