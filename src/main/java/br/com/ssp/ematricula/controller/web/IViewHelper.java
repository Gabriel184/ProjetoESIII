package br.com.ssp.ematricula.controller.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.ssp.ematricula.model.domain.EntidadeDominio;

public interface IViewHelper {
	public EntidadeDominio getEntidade(HttpServletRequest request);
	public void setView(Object obj, HttpServletRequest request, HttpServletResponse response);
}
