package br.com.ssp.ematricula.controller.command;

import br.com.ssp.ematricula.model.domain.EntidadeDominio;

public class GetCommand extends AbstractCommand {

	@Override
	public Object execute(EntidadeDominio entidade) {
		return fachada.get(entidade);
	}

}
