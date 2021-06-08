package br.com.ssp.ematricula.controller.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import br.com.ssp.ematricula.model.util.MensagemResposta;

public abstract class AbstractVH implements IViewHelper {

	Map<String,Object> msgsRetorno;
	
	private void definirRetorno(Object obj) {
		msgsRetorno = new HashMap<String,Object>();
		msgsRetorno.put("salvar", "Cadastro realizado com Sucesso!!!");
		msgsRetorno.put("consultar", obj);
		msgsRetorno.put("atualizar", "Atualização realizado com Sucesso!!!");
		msgsRetorno.put("excluir", "Exclusão realizado com Sucesso!!!");
		msgsRetorno.put("get", obj);
	}
	

	protected void dispatchResponse(MensagemResposta msg, HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out;
		try {
			out = response.getWriter();
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			out.print(new Gson().toJson(msg));
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void setView(Object result, HttpServletRequest request, HttpServletResponse response) {
		if(result instanceof String) {
			MensagemResposta msg = new MensagemResposta("error", result);
			dispatchResponse(msg, request, response);
		} else {
			definirRetorno(result);
			MensagemResposta msg = new MensagemResposta("success", msgsRetorno.get(request.getParameter("operacao")));
			dispatchResponse(msg, request, response);
		}
	}

}
