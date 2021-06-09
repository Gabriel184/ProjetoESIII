package br.com.ssp.ematricula.controller.facade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.ssp.ematricula.model.business.ComplementarCodigoEndereco;
import br.com.ssp.ematricula.model.business.ComplementarCodigoMatricula;
import br.com.ssp.ematricula.model.business.ComplementarDataCadastro;
import br.com.ssp.ematricula.model.business.IStrategy;
import br.com.ssp.ematricula.model.business.ValidarEnderecoUpdate;
import br.com.ssp.ematricula.model.business.ValidarIdade;
import br.com.ssp.ematricula.model.business.ValidarLimiteMatriculas;
import br.com.ssp.ematricula.model.business.ValidarMatriculaUnica;
import br.com.ssp.ematricula.model.dao.CursoDAO;
import br.com.ssp.ematricula.model.dao.IDAO;
import br.com.ssp.ematricula.model.dao.MatriculaDAO;
import br.com.ssp.ematricula.model.domain.Curso;
import br.com.ssp.ematricula.model.domain.EntidadeDominio;
import br.com.ssp.ematricula.model.domain.Matricula;

public class Facade implements IFacade {
	
	private Map<String, IDAO> daos;
	private Map<String, Map<String, List<IStrategy>>> rnsCRUD;
	
	public Facade() {
		definirDAOS();
		definirRNS();
	}
	
	private void definirDAOS() {
		daos = new HashMap<String, IDAO>();
		daos.put(Curso.class.getName(), CursoDAO.getInstancia());
		daos.put(Matricula.class.getName(), MatriculaDAO.getInstancia());
	}
	
	private void definirRNS() {
		rnsCRUD = new HashMap<String, Map<String, List<IStrategy>>>();
		
		Map<String, List<IStrategy>> rnsCreate = new HashMap<String, List<IStrategy>>();
		
		ComplementarDataCadastro cDataCad = new ComplementarDataCadastro();
		ValidarIdade vIdade = new ValidarIdade();
		ComplementarCodigoMatricula cCodMat = new ComplementarCodigoMatricula();
		ComplementarCodigoEndereco cCodEnd = new ComplementarCodigoEndereco();
		ValidarMatriculaUnica vMatUnica = new ValidarMatriculaUnica();
		ValidarLimiteMatriculas vLimiteMat = new ValidarLimiteMatriculas();
		
		List<IStrategy> rnsCreateMatricula = new ArrayList<IStrategy>();
		rnsCreateMatricula.add(cDataCad);
		rnsCreateMatricula.add(vIdade);
		rnsCreateMatricula.add(cCodMat);
		rnsCreateMatricula.add(cCodEnd);
		rnsCreateMatricula.add(vMatUnica);
		rnsCreateMatricula.add(vLimiteMat);
		
		rnsCreate.put(Matricula.class.getName(), rnsCreateMatricula);
		
		rnsCRUD.put("CREATE", rnsCreate);
		
		Map<String, List<IStrategy>> rnsRead = new HashMap<String, List<IStrategy>>();
		rnsCRUD.put("READ", rnsRead);
		
		Map<String, List<IStrategy>> rnsUpdate = new HashMap<String, List<IStrategy>>();
		
		ValidarEnderecoUpdate vEndUpdate = new ValidarEnderecoUpdate();
		
		List<IStrategy> rnsUpdateMatricula = new ArrayList<IStrategy>();
		rnsUpdateMatricula.add(cCodEnd);
		rnsUpdateMatricula.add(vEndUpdate);
		
		rnsUpdate.put(Matricula.class.getName(), rnsUpdateMatricula);
		rnsCRUD.put("UPDATE", rnsUpdate);
		
		Map<String, List<IStrategy>> rnsDelete = new HashMap<String, List<IStrategy>>();
		rnsCRUD.put("DELETE", rnsDelete);		
		
		Map<String, List<IStrategy>> rnsGet = new HashMap<String, List<IStrategy>>();
		rnsCRUD.put("GET", rnsGet);
	}
	
	private String executarRegras(EntidadeDominio entidade, String operacao) {
		String nomeClasse = entidade.getClass().getCanonicalName();
		StringBuilder msg = new StringBuilder();
		
		Map<String, List<IStrategy>> rns = rnsCRUD.get(operacao);
		
		List<IStrategy> regras = rns.get(nomeClasse);
		
		if(regras != null) {
			for(IStrategy s : regras) {
				String m = s.processar(entidade);
				if(m != null) {
					msg.append(m);
					msg.append("\n");
				}
			}
		}
		if(msg.length() > 0)
			return msg.toString();
		else
			return null;
	}

	@Override
	public Object salvar(EntidadeDominio entidade) {
		String nomeClasse = entidade.getClass().getName();
		String msg = executarRegras(entidade, "CREATE");
		if(msg == null)
			daos.get(nomeClasse).create(entidade);
		else
			return msg;
		return null;
	}

	@Override
	public Object consultar(EntidadeDominio entidade) {
		String nomeClasse = entidade.getClass().getName();
		String msg = executarRegras(entidade, "READ");
		if(msg == null)
			return daos.get(nomeClasse).read(entidade);
		else
			return msg;
	}

	@Override
	public Object atualizar(EntidadeDominio entidade) {
		String nomeClasse = entidade.getClass().getName();
		String msg = executarRegras(entidade, "UPDATE");
		if(msg == null)
			daos.get(nomeClasse).update(entidade);
		else
			return msg;
		return null;
	}

	@Override
	public Object excluir(EntidadeDominio entidade) {
		String nomeClasse = entidade.getClass().getName();
		String msg = executarRegras(entidade, "DELETE");
		if(msg == null)
			daos.get(nomeClasse).delete(entidade);
		else
			return msg;
		return null;
	}
	
	@Override
	public Object get(EntidadeDominio entidade) {
		String nomeClasse = entidade.getClass().getName();
		String msg = executarRegras(entidade, "GET");
		if(msg == null)
			return daos.get(nomeClasse).get(entidade);
		else
			return msg;
	}

}
