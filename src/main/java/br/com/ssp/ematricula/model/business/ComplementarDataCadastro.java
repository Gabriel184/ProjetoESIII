package br.com.ssp.ematricula.model.business;

import java.util.GregorianCalendar;

import br.com.ssp.ematricula.model.domain.EntidadeDominio;
import br.com.ssp.ematricula.model.domain.Matricula;

public class ComplementarDataCadastro implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		if(entidade instanceof Matricula) {
			Matricula m = (Matricula) entidade;
			m.setDataCadastro(new GregorianCalendar());
			return null;
		}
		return "Entidade incorreta";
	}

}
