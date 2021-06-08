package br.com.ssp.ematricula.model.business;

import br.com.ssp.ematricula.model.domain.Endereco;
import br.com.ssp.ematricula.model.domain.EntidadeDominio;
import br.com.ssp.ematricula.model.domain.Matricula;

public class ComplementarCodigoEndereco implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		if(entidade instanceof Matricula) {
			Endereco e = ((Matricula) entidade).getAluno().getEndereco();
			StringBuilder sb = new StringBuilder();
			sb.append(e.getCep());
			sb.append(e.getNumero());
			sb.append(e.getComplemento().trim());
			e.setCodigo(sb.toString());
			return null;
		}
		return "Entidade incorreta";
	}

}
