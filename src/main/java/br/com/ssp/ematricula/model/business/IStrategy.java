package br.com.ssp.ematricula.model.business;

import br.com.ssp.ematricula.model.domain.EntidadeDominio;

public interface IStrategy {
	public String processar(EntidadeDominio entidade);
}