package br.com.ssp.ematricula.model.business;

import br.com.ssp.ematricula.model.domain.Endereco;
import br.com.ssp.ematricula.model.domain.EntidadeDominio;
import br.com.ssp.ematricula.model.domain.Matricula;

public class ValidarEnderecoUpdate implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		if(entidade instanceof Matricula) {
			Matricula mat = (Matricula) entidade;
			Endereco end = mat.getAluno().getEndereco();
			if(!end.getCep().equals("") && end.getNumero() == Integer.MIN_VALUE)
				return "Não se pode alterar o endereço sem definir o número da residência";
			return null;
		}
		return "Entidade incorreta";
	}

}
