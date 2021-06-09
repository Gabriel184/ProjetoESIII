package br.com.ssp.ematricula.test;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import br.com.ssp.ematricula.controller.facade.Facade;
import br.com.ssp.ematricula.model.dao.CursoDAO;
import br.com.ssp.ematricula.model.domain.Aluno;
import br.com.ssp.ematricula.model.domain.Categoria;
import br.com.ssp.ematricula.model.domain.Cidade;
import br.com.ssp.ematricula.model.domain.Curso;
import br.com.ssp.ematricula.model.domain.Endereco;
import br.com.ssp.ematricula.model.domain.EntidadeDominio;
import br.com.ssp.ematricula.model.domain.Estado;
import br.com.ssp.ematricula.model.domain.Matricula;

public class TestDAO {
	public static void main(String[] args) {
		testDeleteMatriculaDAO();
	}
	public static void testDeleteMatriculaDAO() {
		Matricula m = new Matricula();
		m.setCodigo("20210609033448-análise e desenvolvimento de sistemas-n");
		Facade facade = new Facade();
		Gson gson = new Gson();
		System.out.println(gson.toJson(facade.excluir(m)));
	}
	
	public static void testGetMatriculaDAO() {
		Matricula m = new Matricula();
		Facade facade = new Facade();
		Gson gson = new Gson();
		System.out.println(gson.toJson(facade.get(m)));
	}
	
	public static void testUpdateMatriculaDAO() {
		Matricula matricula = new Matricula(
				"",
				null,
				"",
				new Aluno(
						"Azumi Sayuri Watanabe",
						"",
						"333.333.333-34",
						"+5521840372111",
						null,
						new Endereco(
								"",
								"20530000",
								7030,
								"Rua Conde de Bonfim",
								"de 0715 a 0999 - lado ímpar",
								"Tijuca",
								new Cidade(
										"Rio de Janeiro",
										new Estado(
												"RJ"
										)
								)
						)
				),
				null
			);
		Facade facade = new Facade();
		Gson gson = new Gson();
		System.out.println(gson.toJson(facade.atualizar(matricula)));
	}
	
	public static void testCursoDAO() {
		Curso cur = new Curso();
		CursoDAO curDAO = CursoDAO.getInstancia();
		cur.setCategoria(new Categoria());
		cur.getCategoria().setDescricao("Ensino Superior");
		List<EntidadeDominio> cursos = new ArrayList<EntidadeDominio>();
		cursos = (List<EntidadeDominio>) curDAO.get(cur);
		cursos.forEach(entidade -> {
			Curso curso = (Curso) entidade;
			System.out.print(curso.getId() + " ");
			System.out.print(curso.getDescricao() + " ");
			System.out.print(curso.getDuracao() + " ");
			System.out.print(curso.getCategoria().getId() + " ");
			System.out.print(curso.getCategoria().getDescricao());
			System.out.println();
		});
		Gson gson = new Gson();
		System.out.println(gson.toJson(cursos));
	}
}