package br.com.ssp.ematricula.model.business;

import java.util.Calendar;

import br.com.ssp.ematricula.model.domain.EntidadeDominio;
import br.com.ssp.ematricula.model.domain.Matricula;

public class ValidarIdade implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		if(entidade instanceof Matricula) {
			Matricula m = (Matricula) entidade;
			if(m.getCurso().getCategoria().getDescricao().equals("Ensino Superior") ||
					m.getCurso().getCategoria().getDescricao().equals("Pós Graduação")) {
				if((m.getDataCadastro().get(Calendar.YEAR) - m.getAluno().getDataNascimento().get(Calendar.YEAR)) < 18) {
					return "O aluno precisa ser maior de 18 anos para se matricular nesse curso";
				}
			}
			if(m.getCurso().getCategoria().getDescricao().equals("Ensino Técnico")) {
				if((m.getDataCadastro().get(Calendar.YEAR) - m.getAluno().getDataNascimento().get(Calendar.YEAR)) < 15) {
					return "O aluno precisa ser maior de 15 anos para se matricular nesse curso";
				}
			}
			return null;
		}
		return "Entidade incorreta";
	}

}
