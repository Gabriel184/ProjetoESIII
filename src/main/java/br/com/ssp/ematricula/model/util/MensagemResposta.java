package br.com.ssp.ematricula.model.util;

public class MensagemResposta {
	public String status;
	public Object mensagem;
	
	public MensagemResposta(String status, Object mensagem) {
		super();
		this.status = status;
		this.mensagem = mensagem;
	}
}
