package br.com.ssp.ematricula.model.dao;

import java.util.List;

import br.com.ssp.ematricula.model.domain.EntidadeDominio;

public interface IDAO {
	public void create(EntidadeDominio entidade);
	public EntidadeDominio read(EntidadeDominio entidade);
	public void update(EntidadeDominio entidade);
	public void delete(EntidadeDominio entidade);
	public List<EntidadeDominio> get(EntidadeDominio entidade);
}
