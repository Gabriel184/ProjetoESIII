package br.com.ssp.ematricula.model.business;

import br.com.ssp.ematricula.model.dao.MatriculaDAO;
import br.com.ssp.ematricula.model.domain.EntidadeDominio;
import br.com.ssp.ematricula.model.domain.Matricula;

public class ValidarLimiteMatriculas implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		if(entidade instanceof Matricula) {
			Matricula mat = (Matricula) entidade;
			MatriculaDAO matDAO = MatriculaDAO.getInstancia();
			if(mat.getCurso().getCategoria().getDescricao().equals("Ensino Superior") ||
					mat.getCurso().getCategoria().getDescricao().equals("Pós Graduação")) {
				if(matDAO.count(mat) > 60)
					return "Essa turma está cheia";
			} else {
				if(matDAO.count(mat) > 35)
					return "Essa turma está cheia";
			}
			return null;
		}
		return "Entidade incorreta";
	}

}
