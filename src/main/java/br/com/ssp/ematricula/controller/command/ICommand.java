package br.com.ssp.ematricula.controller.command;

import br.com.ssp.ematricula.model.domain.EntidadeDominio;

public interface ICommand {
	public Object execute(EntidadeDominio entidade);
}
