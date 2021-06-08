package br.com.ssp.ematricula.controller.command;

import br.com.ssp.ematricula.model.domain.EntidadeDominio;

public class SalvarCommand extends AbstractCommand {

	@Override
	public Object execute(EntidadeDominio entidade) {
		return fachada.salvar(entidade);
	}

}
