package br.com.ssp.ematricula.model.business;

import br.com.ssp.ematricula.model.dao.MatriculaDAO;
import br.com.ssp.ematricula.model.domain.EntidadeDominio;
import br.com.ssp.ematricula.model.domain.Matricula;

public class ValidarMatriculaUnica implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		if(entidade instanceof Matricula) {
			Matricula m = (Matricula) entidade;
			MatriculaDAO matDAO = MatriculaDAO.getInstancia();
			if(matDAO.verifyMatricula(m))
				return "O aluno já possui uma matrícula e não pode cadastrar outra";
			return null;
		}
		return "Entidade incorreta";
	}

}