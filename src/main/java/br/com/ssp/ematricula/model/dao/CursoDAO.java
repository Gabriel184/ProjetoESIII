package br.com.ssp.ematricula.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.ssp.ematricula.model.domain.Categoria;
import br.com.ssp.ematricula.model.domain.Curso;
import br.com.ssp.ematricula.model.domain.EntidadeDominio;

public class CursoDAO extends AbstractJdbcDAO {
	
	private static CursoDAO INSTANCIA = null;
	
	public synchronized static CursoDAO getInstancia() {
		if(INSTANCIA == null)
			INSTANCIA = new CursoDAO();
		return INSTANCIA;
	}
	
	CursoDAO(Connection cx) {
		super(cx,"curso", "id_cur");
	}

	private CursoDAO() {
		super("curso", "id_cur");
	}

	@Override
	public void create(EntidadeDominio entidade) {
		// TODO Auto-generated method stub

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
		
		Curso cur = null;
		
		sql.append("SELECT ");
		sql.append("id_cur, c1.descricao AS cur_descricao, duracao, cat_id, id_cat, c2.descricao AS cat_descricao");
		sql.append(" FROM curso AS c1 ");
		sql.append("INNER JOIN categoria AS c2 ");
		sql.append("ON c1.cat_id = c2.id_cat");
		sql.append(" WHERE ");
		sql.append("c1.descricao");
		sql.append(" = ");
		sql.append("?");
		
		try {
			connection.setAutoCommit(false);
			pst = connection.prepareStatement(sql.toString());
			
			pst.setString(1, ((Curso) entidade).getDescricao());
			
			ResultSet rs = pst.executeQuery();
			
			if(rs.next()) {
				cur = new Curso(
						rs.getString("cur_descricao"),
						rs.getInt("duracao"),
						new Categoria(
								rs.getString("cat_descricao")
						)
				);
				cur.setId(rs.getInt("id_cur"));
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
		return cur;
	}

	@Override
	public void update(EntidadeDominio entidade) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<EntidadeDominio> get(EntidadeDominio entidade) {
		openConnection();
		PreparedStatement pst = null;
		StringBuilder sb = new StringBuilder();
		
		Curso curso = (Curso) entidade;
		
		sb.append("SELECT curso.id_cur, curso.descricao, curso.duracao, "
				+ "categoria.id_cat, categoria.descricao AS cat_descricao ");
		sb.append("FROM curso ");
		sb.append("INNER JOIN categoria ON curso.cat_id = categoria.id_cat ");
		sb.append("WHERE categoria.descricao = ?");
		
		List<EntidadeDominio> cursos = new ArrayList<EntidadeDominio>();
		
		try {
			connection.setAutoCommit(false);
			pst = connection.prepareStatement(sb.toString(),
					Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, curso.getCategoria().getDescricao());
			
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) {
				Curso c = new Curso(
						rs.getString("descricao"),
						rs.getInt("duracao"),
						new Categoria(rs.getString("cat_descricao"))
				);
				c.setId(rs.getInt("id_cur"));
				c.getCategoria().setId(rs.getInt("id_cat"));
				cursos.add(c);
			}
			connection.commit();
		} catch(SQLException e) {
			try {
				connection.rollback();
			} catch(SQLException e1) {
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
		return cursos;
	}

}
