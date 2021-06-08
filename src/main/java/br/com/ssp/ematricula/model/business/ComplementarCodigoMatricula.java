package br.com.ssp.ematricula.model.business;

import java.util.Calendar;

import br.com.ssp.ematricula.model.dao.MatriculaDAO;
import br.com.ssp.ematricula.model.domain.EntidadeDominio;
import br.com.ssp.ematricula.model.domain.Matricula;

public class ComplementarCodigoMatricula implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		if(entidade instanceof Matricula) {
			Matricula m = (Matricula) entidade;
			StringBuilder sb = new StringBuilder();
			MatriculaDAO matDAO = MatriculaDAO.getInstancia();
			sb.append(m.getDataCadastro().get(Calendar.YEAR));
			sb.append(m.getDataCadastro().get(Calendar.MONTH));
			sb.append(m.getDataCadastro().get(Calendar.DAY_OF_MONTH));
			sb.append("-");
			sb.append(m.getCurso().getDescricao().toLowerCase().trim());
			sb.append("-");
			sb.append(m.getTurma().trim().toLowerCase().charAt(0));
			sb.append(matDAO.count(m));
			m.setCodigo(sb.toString());
			return null;
		}
		return "Entidade incorreta";
	}

}
