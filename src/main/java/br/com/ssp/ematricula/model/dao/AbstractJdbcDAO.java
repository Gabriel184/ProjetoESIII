package br.com.ssp.ematricula.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.ssp.ematricula.model.domain.EntidadeDominio;
import br.com.ssp.ematricula.util.Conexao;

public abstract class AbstractJdbcDAO implements IDAO {
	protected Connection connection;
	protected String table;
	protected String idTable;
	protected String uniqueKey;
	protected boolean ctrlTransaction = true;
	
	protected AbstractJdbcDAO(Connection connection, String table, String idTable, String uniqueKey) {
		super();
		this.connection = connection;
		this.table = table;
		this.idTable = idTable;
		this.uniqueKey = uniqueKey;
	}
	
	protected AbstractJdbcDAO(Connection connection, String table, String idTable) {
		super();
		this.connection = connection;
		this.table = table;
		this.idTable = idTable;
	}

	protected AbstractJdbcDAO(String table, String idTable, String uniqueKey) {
		super();
		this.table = table;
		this.idTable = idTable;
		this.uniqueKey = uniqueKey;
	}
	
	protected AbstractJdbcDAO(String table, String idTable) {
		super();
		this.table = table;
		this.idTable = idTable;
	}
	
	@Override
	public void delete(EntidadeDominio entidade) {
		openConnection();
		PreparedStatement pst = null;
		StringBuilder sb = new StringBuilder();
		
		sb.append("DELETE FROM ");
		sb.append(table);
		sb.append(" WHERE ");
		sb.append(idTable);
		sb.append(" = ");
		sb.append("?");
		
		try {
			connection.setAutoCommit(false);
			pst = connection.prepareStatement(sb.toString());
			pst.setInt(1, entidade.getId());
			
			pst.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();			
		} finally {
			try {
				pst.close();
				if(ctrlTransaction)
					connection.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public boolean existId(EntidadeDominio entidade) {
		openConnection();
		PreparedStatement pst = null;
		StringBuilder sb = new StringBuilder();
		
		boolean verification = false;
		
		sb.append("SELECT * FROM ");
		sb.append(table);
		sb.append(" WHERE ");
		sb.append(idTable);
		sb.append(" = ");
		sb.append("?");
		
		try {
			connection.setAutoCommit(false);
			pst = connection.prepareStatement(sb.toString());
			
			pst.setInt(1, entidade.getId());
			
			ResultSet rs = pst.executeQuery();
			
			if(rs.next())
				verification = true;
			
			connection.commit();
		} catch(SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				pst.close();
				connection.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return verification;
	}
	
	public boolean isEmpty() {
		openConnection();
		boolean verification = true;
		PreparedStatement pst = null;
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT CASE ");
			sb.append("WHEN EXISTS ");
				sb.append("(SELECT ");
				sb.append("(" + idTable + ")");
				sb.append(" FROM ");
				sb.append(table);
				sb.append(" LIMIT 1) ");
			sb.append("THEN false ");
			sb.append("ELSE true ");
		sb.append("END");
		
		try {
			connection.setAutoCommit(false);
			pst = connection.prepareStatement(sb.toString());
			
			ResultSet rs = pst.executeQuery();
			
			while(rs.next())
				verification = rs.getBoolean(1);
			
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();			
		} finally {
			try {
				pst.close();
				connection.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return verification;
	}
	
	protected void openConnection() {
		try {
			if(connection == null || connection.isClosed())
				connection = Conexao.getConnectionPostgres();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}

}
