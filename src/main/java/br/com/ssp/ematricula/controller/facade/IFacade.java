package br.com.ssp.ematricula.controller.facade;

import br.com.ssp.ematricula.model.domain.EntidadeDominio;

public interface IFacade {
	public Object salvar(EntidadeDominio entidade);
	public Object consultar(EntidadeDominio entidade);
	public Object atualizar(EntidadeDominio entidade);
	public Object excluir(EntidadeDominio entidade);
	public Object get(EntidadeDominio entidade);
}
