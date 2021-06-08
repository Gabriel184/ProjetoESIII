package br.com.ssp.ematricula.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import br.com.ssp.ematricula.model.domain.Cidade;
import br.com.ssp.ematricula.model.domain.Endereco;
import br.com.ssp.ematricula.model.domain.EntidadeDominio;
import br.com.ssp.ematricula.model.domain.Estado;

public class EnderecoDAO extends AbstractJdbcDAO {

	private static EnderecoDAO INSTANCIA = null;
	
	public synchronized static EnderecoDAO getInstancia() {
		if(INSTANCIA == null)
			INSTANCIA = new EnderecoDAO();
		return INSTANCIA;
	}

	EnderecoDAO(Connection cx) {
		super(cx,"endereco","id_end","codigo");
	}
	
	private EnderecoDAO() {
		super("endereco","id_end","codigo");
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
		
		Endereco end = (Endereco) entidade;
		
		sql.append("INSERT INTO endereco");
		sql.append(" (cep, logradouro, cidade, estado, numero, bairro, complemento, codigo) ");
		sql.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
		
		try {
			connection.setAutoCommit(false);
			
			pst = connection.prepareStatement(sql.toString(),
					Statement.RETURN_GENERATED_KEYS);
			
			pst.setString(1, end.getCep());
			pst.setString(2, end.getLogradouro());
			pst.setString(3, end.getCidade().getDescricao());
			pst.setString(4, end.getCidade().getEstado().getDescricao());
			pst.setInt(5, end.getNumero());
			pst.setString(6, end.getBairro());
			pst.setString(7, end.getComplemento());
			pst.setString(8, end.getCodigo());
			
			pst.executeUpdate();
			
			ResultSet rs = pst.getGeneratedKeys();
			
			int idEnd = -1;
			if(rs.next())
				idEnd = rs.getInt(1);
			end.setId(idEnd);
			
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
		
		Endereco end = null;
		
		sql.append("SELECT * FROM ");
		sql.append(table);
		sql.append(" WHERE ");
		sql.append(uniqueKey);
		sql.append(" = ");
		sql.append("?");
		
		try {
			connection.setAutoCommit(false);
			pst = connection.prepareStatement(sql.toString());
			
			pst.setString(1, ((Endereco) entidade).getCodigo());
			
			ResultSet rs = pst.executeQuery();
			
			if(rs.next()) {
				end = new Endereco(
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
				);
				end.setId(rs.getInt("id_end"));
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
		return end;
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
	
	public boolean verifyEndereco(Endereco endereco) {
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
		
		Endereco end = endereco;
		
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
			
			pst.setString(1, end.getCodigo());
			
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
