package br.com.ssp.ematricula.controller.web;

import javax.servlet.http.HttpServletRequest;

import br.com.ssp.ematricula.model.domain.Categoria;
import br.com.ssp.ematricula.model.domain.Curso;
import br.com.ssp.ematricula.model.domain.EntidadeDominio;

public class CursoVH extends AbstractVH {

	@Override
	public EntidadeDominio getEntidade(HttpServletRequest request) {
		if(request.getParameter("operacao").equals("get")) {
			Curso c = new Curso();
			c.setCategoria(new Categoria());
			c.getCategoria().setDescricao(request.getParameter("categoria"));
			return c;
		}
		return null;
	}

}
