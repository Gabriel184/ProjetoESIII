package br.com.ssp.ematricula.controller.command;

import br.com.ssp.ematricula.model.domain.EntidadeDominio;

public class ExcluirCommand extends AbstractCommand {

	@Override
	public Object execute(EntidadeDominio entidade) {
		return fachada.excluir(entidade);
	}

}
