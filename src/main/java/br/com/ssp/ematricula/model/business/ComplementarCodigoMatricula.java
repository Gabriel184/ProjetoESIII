package br.com.ssp.ematricula.model.business;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
			DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyyMMddhhmmss");
			sb.append(LocalDateTime.now().format(pattern));
			sb.append("-");
			sb.append(m.getCurso().getDescricao().toLowerCase().trim());
			sb.append("-");
			sb.append(m.getTurma().trim().toLowerCase().charAt(0));
			m.setCodigo(sb.toString());
			return null;
		}
		return "Entidade incorreta";
	}

}
