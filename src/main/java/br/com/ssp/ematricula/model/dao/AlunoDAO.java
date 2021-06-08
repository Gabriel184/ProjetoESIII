package br.com.ssp.ematricula.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.List;

import br.com.ssp.ematricula.model.domain.Aluno;
import br.com.ssp.ematricula.model.domain.Cidade;
import br.com.ssp.ematricula.model.domain.Endereco;
import br.com.ssp.ematricula.model.domain.EntidadeDominio;
import br.com.ssp.ematricula.model.domain.Estado;

public class AlunoDAO extends AbstractJdbcDAO {
	private static AlunoDAO INSTANCIA = null;
	
	public synchronized static AlunoDAO getInstancia() {
		if(INSTANCIA == null)
			INSTANCIA = new AlunoDAO();
		return INSTANCIA;
	}

	AlunoDAO(Connection cx) {
		super(cx,"aluno","id_alu","cpf");
	}
	
	private AlunoDAO() {
		super("aluno","id_alu","cpf");
	}
	@Override
	public void create(EntidadeDominio entidade) {
		if(connection == null) {
			openConnection();
		} else {
			try {
				if(connection.isClosed())
					openConnection();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		PreparedStatement pst = null;
		StringBuilder sql = new StringBuilder();
		
		Aluno alu = (Aluno) entidade;
		
		sql.append("INSERT INTO aluno");
		sql.append(" (end_id, nome, cpf, telefone, data_nascimento, email) ");
		sql.append("VALUES (?, ?, ?, ?, ?, ?)");
		
		try {
			connection.setAutoCommit(false);
			
			pst = connection.prepareStatement(sql.toString(),
					Statement.RETURN_GENERATED_KEYS);
			
			EnderecoDAO endDAO = new EnderecoDAO(connection);
			endDAO.ctrlTransaction = false;
			Endereco end = alu.getEndereco();
			if(endDAO.verifyEndereco(end))
				end.setId(((Endereco) endDAO.read(end)).getId());
			else
				endDAO.create(end);
			alu.getEndereco().setId(end.getId());
			
			SimpleDateFormat sdf = new SimpleDateFormat("y-MM-dd");
			
			pst.setInt(1, end.getId());
			pst.setString(2, alu.getNome());
			pst.setString(3, alu.getCpf());
			pst.setString(4, alu.getTelefone());
			pst.setDate(5, java.sql.Date.valueOf(sdf.format(alu.getDataNascimento().getTime())));
			pst.setString(6, alu.getEmail());
			
			pst.executeUpdate();			
			ResultSet rs = pst.getGeneratedKeys();
			
			int idAlu = -1;
			if(rs.next())
				idAlu = rs.getInt(1);
			alu.setId(idAlu);
			
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			if(ctrlTransaction) {
				try {
					pst.close();
					if(ctrlTransaction) {
						connection.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

	}

	@Override
	public EntidadeDominio read(EntidadeDominio entidade) {
		if(connection == null) {
			openConnection();
		} else {
			try {
				if(connection.isClosed())
					openConnection();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		PreparedStatement pst = null;
		StringBuilder sql = new StringBuilder();
		
		Aluno alu = null;
		
		sql.append("SELECT * FROM ");
		sql.append("aluno AS a1 ");
		sql.append("INNER JOIN endereco AS e1 ");
		sql.append("ON a1.end_id = e1.id_end");
		sql.append(" WHERE ");
		sql.append("a1.cpf");
		sql.append(" = ");
		sql.append("?");
		
		try {
			connection.setAutoCommit(false);
			pst = connection.prepareStatement(sql.toString());
			
			pst.setString(1, ((Aluno) entidade).getCpf());
			
			ResultSet rs = pst.executeQuery();
			
			if(rs.next()) {
				alu = new Aluno(
						rs.getString("nome"),
						rs.getString("email"),
						rs.getString("cpf"),
						rs.getString("telefone"),
						new GregorianCalendar(
								Integer.parseInt(rs.getString("data_nascimento").substring(0,4)),
								Integer.parseInt(rs.getString("data_nascimento").substring(5,7))-1,
								Integer.parseInt(rs.getString("data_nascimento").substring(8,10))
						),
						new Endereco(
								rs.getString("codigo"),
								rs.getString("cep"),
								rs.getInt("numero"),
								rs.getString("logradouro"),
								rs.getString("complemento"),
								rs.getString("bairro"),
								new Cidade(
										rs.getString("cidade"),
										new Estado(
												rs.getString("estado")
										)
								)
						)
				);
				alu.getEndereco().setId(rs.getInt("end_id"));
				alu.setId(rs.getInt("id_alu"));
			}
			connection.commit();
		} catch(SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			if(ctrlTransaction) {
				try {
					pst.close();
					if(ctrlTransaction) {
						connection.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return alu;
	}

	@Override
	public void update(EntidadeDominio entidade) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<EntidadeDominio> get(EntidadeDominio entidade) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean verifyAluno(Aluno aluno) {
		if(connection == null) {
			openConnection();
		} else {
			try {
				if(connection.isClosed())
					openConnection();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		PreparedStatement pst = null;
		boolean resposta = false;
		
		Aluno alu = aluno;
		
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT CASE ");
			sql.append("WHEN EXISTS ");
				sql.append("(SELECT ");
				sql.append("(" + idTable + ")");
				sql.append(" FROM ");
				sql.append(table);
				sql.append(" WHERE " + uniqueKey + " = ?");
				sql.append(" ) ");
			sql.append("THEN true ");
			sql.append("ELSE false ");
		sql.append("END");
		
		try {
			connection.setAutoCommit(false);
			pst = connection.prepareStatement(sql.toString());
			
			pst.setString(1, alu.getCpf());
			
			ResultSet rs = pst.executeQuery();
			if(rs.next()) {
				resposta = rs.getBoolean("case");
			}
			connection.commit();
		} catch(SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			if(ctrlTransaction) {
				try {
					pst.close();
					if(ctrlTransaction) {
						connection.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return resposta;
	}
	
}
