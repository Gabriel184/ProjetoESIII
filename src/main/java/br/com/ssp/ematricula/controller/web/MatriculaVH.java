package br.com.ssp.ematricula.controller.web;

import java.util.GregorianCalendar;

import javax.servlet.http.HttpServletRequest;

import br.com.ssp.ematricula.model.domain.Aluno;
import br.com.ssp.ematricula.model.domain.Categoria;
import br.com.ssp.ematricula.model.domain.Cidade;
import br.com.ssp.ematricula.model.domain.Curso;
import br.com.ssp.ematricula.model.domain.Endereco;
import br.com.ssp.ematricula.model.domain.EntidadeDominio;
import br.com.ssp.ematricula.model.domain.Estado;
import br.com.ssp.ematricula.model.domain.Matricula;
import br.com.ssp.ematricula.util.InsertParser;

public class MatriculaVH extends AbstractVH {

	@Override
	public EntidadeDominio getEntidade(HttpServletRequest request) {
		if(request.getParameter("operacao").equals("salvar")) {
			Matricula matricula = new Matricula(
				"",
				null,
				request.getParameter("turma"),
				new Aluno(
						request.getParameter("nome"),
						request.getParameter("email"),
						request.getParameter("cpf"),
						request.getParameter("telefone"),
						new GregorianCalendar(
								Integer.parseInt(request.getParameter("data_nascimento").substring(0,4)),
								Integer.parseInt(request.getParameter("data_nascimento").substring(5,7))-1,
								Integer.parseInt(request.getParameter("data_nascimento").substring(8,10))
						),
						new Endereco(
								"",
								request.getParameter("cep"),
								InsertParser.StrToInt(request.getParameter("numero"), Integer.MIN_VALUE),
								request.getParameter("logradouro"),
								request.getParameter("complemento"),
								request.getParameter("bairro"),
								new Cidade(
										request.getParameter("cidade"),
										new Estado(
												request.getParameter("estado")
										)
								)
						)
				),
				new Curso(
						request.getParameter("curso_nome"),
						InsertParser.StrToInt(request.getParameter("curso_duracao"), Integer.MIN_VALUE),
						new Categoria(
								request.getParameter("categoria")
						)
				)
			);
			return matricula;
		}
		if(request.getParameter("operacao").equals("consultar")) {
			Matricula matricula = new Matricula();
			matricula.setCodigo(request.getParameter("codigo"));
			return matricula;
		}
		if(request.getParameter("operacao").equals("atualizar")) {
			Matricula matricula = new Matricula(
				"",
				null,
				"",
				new Aluno(
						(request.getParameter("nome") == null) ? "" : request.getParameter("nome"),
						(request.getParameter("email") == null) ? "" : request.getParameter("email"),
						(request.getParameter("cpf") == null) ? "" : request.getParameter("cpf"),
						(request.getParameter("telefone") == null) ? "" : request.getParameter("telefone"),
						null,
						new Endereco(
								"",
								(request.getParameter("cep") == null) ? "" : request.getParameter("cep"),
								InsertParser.StrToInt(request.getParameter("numero"), Integer.MIN_VALUE),
								(request.getParameter("logradouro") == null) ? "" : request.getParameter("logradouro"),
								(request.getParameter("complemento") == null) ? "" : request.getParameter("complemento"),
								(request.getParameter("bairro") == null) ? "" : request.getParameter("bairro"),
								new Cidade(
										(request.getParameter("cidade") == null) ? "" : request.getParameter("cidade"),
										new Estado(
												(request.getParameter("estado") == null) ? "" : request.getParameter("estado")
										)
								)
						)
				),
				null
			);
			return matricula;
		}
		if(request.getParameter("operacao").equals("excluir")) {
			Matricula matricula = new Matricula();
			matricula.setCodigo(request.getParameter("codigo"));
			return matricula;
		}
		if(request.getParameter("operacao").equals("get")) {
			Matricula matricula = new Matricula();
			return matricula;
		}
		return null;
	}

}

