package br.com.ssp.ematricula.controller.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.ssp.ematricula.controller.command.AtualizarCommand;
import br.com.ssp.ematricula.controller.command.ConsultarCommand;
import br.com.ssp.ematricula.controller.command.ExcluirCommand;
import br.com.ssp.ematricula.controller.command.GetCommand;
import br.com.ssp.ematricula.controller.command.ICommand;
import br.com.ssp.ematricula.controller.command.SalvarCommand;
import br.com.ssp.ematricula.model.domain.EntidadeDominio;

public class Boundary extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Map<String, IViewHelper> viewhelpers;
	private static Map<String, ICommand> commands;

    public Boundary() {
    	viewhelpers = new HashMap<String, IViewHelper>();
    	viewhelpers.put("/e-matricula/Matricula", new MatriculaVH());
    	viewhelpers.put("/e-matricula/Curso", new CursoVH());
    	
    	commands = new HashMap<String, ICommand>();
    	commands.put("salvar", new SalvarCommand());
    	commands.put("atualizar", new AtualizarCommand());
    	commands.put("excluir", new ExcluirCommand());
    	commands.put("consultar", new ConsultarCommand());
    	commands.put("get", new GetCommand());
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri = request.getRequestURI();
		String operacao = request.getParameter("operacao");
		System.out.println(operacao + " -> " + uri);
		
		IViewHelper vh = viewhelpers.get(uri);
		ICommand cmd = commands.get(operacao);
		
		EntidadeDominio entidade = vh.getEntidade(request);
		Object msg = cmd.execute(entidade);
		
		vh.setView(msg,request,response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);

	}
}
