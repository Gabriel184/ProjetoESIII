package br.com.ssp.ematricula.test;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import br.com.ssp.ematricula.model.dao.CursoDAO;
import br.com.ssp.ematricula.model.domain.Categoria;
import br.com.ssp.ematricula.model.domain.Curso;
import br.com.ssp.ematricula.model.domain.EntidadeDominio;

public class TestDAO {
	public static void main(String[] args) {
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
