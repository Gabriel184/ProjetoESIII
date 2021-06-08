package br.com.ssp.ematricula.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import br.com.ssp.ematricula.model.domain.Aluno;
import br.com.ssp.ematricula.model.domain.Categoria;
import br.com.ssp.ematricula.model.domain.Cidade;
import br.com.ssp.ematricula.model.domain.Curso;
import br.com.ssp.ematricula.model.domain.Endereco;
import br.com.ssp.ematricula.model.domain.EntidadeDominio;
import br.com.ssp.ematricula.model.domain.Estado;
import br.com.ssp.ematricula.model.domain.Matricula;

public class MatriculaDAO extends AbstractJdbcDAO {
	
	private static MatriculaDAO INSTANCIA = null;
	
	public synchronized static MatriculaDAO getInstancia() {
		if(INSTANCIA == null)
			INSTANCIA = new MatriculaDAO();
		return INSTANCIA;
	}

	MatriculaDAO(Connection cx) {
		super(cx,"matricula","id_mat");
	}
	
	private MatriculaDAO() {
		super("matricula","id_mat","codigo");
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
		Matricula mat = (Matricula) entidade;
		StringBuilder sql = new StringBuilder();
		
		sql.append("INSERT INTO matricula");
		sql.append(" (alu_id, cur_id, codigo, data_cadastro, turma) ");
		sql.append("VALUES (?, ?, ?, ?, ?)");
		
		try {
			connection.setAutoCommit(false);
			
			pst = connection.prepareStatement(sql.toString(),
					Statement.RETURN_GENERATED_KEYS);
			
			
			AlunoDAO aluDAO = new AlunoDAO(connection);
			aluDAO.ctrlTransaction = false;
			Aluno alu = mat.getAluno();
			if(aluDAO.verifyAluno(alu))
				alu.setId(((Aluno) aluDAO.read(alu)).getId());
			else
				aluDAO.create(alu);
			mat.getAluno().setId(alu.getId());
			
			CursoDAO curDAO = new CursoDAO(connection);
			curDAO.ctrlTransaction = false;
			mat.getCurso().setId(((Curso) curDAO.read(mat.getCurso())).getId());
			
			SimpleDateFormat sdf = new SimpleDateFormat("y-MM-dd");
			
			pst.setInt(1, alu.getId());
			pst.setInt(2, mat.getCurso().getId());
			pst.setString(3, mat.getCodigo());
			pst.setDate(4, java.sql.Date.valueOf(sdf.format(mat.getDataCadastro().getTime())));
			pst.setString(5, mat.getTurma());
			
			pst.executeUpdate();
			ResultSet rs = pst.getGeneratedKeys();
			
			int idMat = -1;
			if(rs.next())
				idMat = rs.getInt(1);
			mat.setId(idMat);
				
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
			} catch (SQLException e) {
				e.printStackTrace();
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
		
		Matricula mat = null;
		
		sql.append("SELECT ");
		sql.append("m1.id_mat, m1.alu_id, m1.cur_id, m1.codigo, m1.data_cadastro, m1.turma, ");
		sql.append("a1.id_alu, a1.end_id, a1.nome, a1.cpf, a1.telefone, a1.data_nascimento, ");
		sql.append("a1.email, e1.id_end, e1.cep, e1.logradouro, e1.cidade, e1.estado, e1.numero, ");
		sql.append("e1.bairro, e1.complemento, e1.codigo AS end_codigo, c1.id_cur, c1.descricao, ");
		sql.append("c1.duracao, c1.cat_id, c2.id_cat, c2.descricao AS cat_descricao ");
		sql.append("FROM matricula AS m1 ");
		sql.append("INNER JOIN aluno AS a1 ON m1.alu_id = a1.id_alu ");
		sql.append("INNER JOIN endereco AS e1 ON a1.end_id = e1.id_end ");
		sql.append("INNER JOIN curso AS c1 ON m1.cur_id = c1.id_cur ");
		sql.append("INNER JOIN categoria AS c2 ON c1.cat_id = c2.id_cat ");
		sql.append("WHERE m1.codigo = ?");
		
		try {
			connection.setAutoCommit(false);
			pst = connection.prepareStatement(sql.toString());
			
			pst.setString(1, ((Matricula) entidade).getCodigo());
			
			ResultSet rs = pst.executeQuery();
			
			if(rs.next()) {
				mat = new Matricula(
						rs.getString("codigo"),
						new GregorianCalendar(
								Integer.parseInt(rs.getString("data_cadastro").substring(0,4)),
								Integer.parseInt(rs.getString("data_cadastro").substring(5,7))-1,
								Integer.parseInt(rs.getString("data_cadastro").substring(8,10))
						),
						rs.getString("turma"),
						new Aluno(
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
										rs.getString("end_codigo"),
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
						),
						new Curso(
								rs.getString("descricao"),
								rs.getInt("duracao"),
								new Categoria(
										rs.getString("cat_descricao")
								)
						)
				);
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
		return mat;
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
	
	public int count(Matricula matricula) {
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
		int contagem = 0;
		
		int year = matricula.getDataCadastro().get(Calendar.YEAR);
		int month = matricula.getDataCadastro().get(Calendar.MONTH)+1;
		
		int firstMonth, lastDay;
		if(month < 7) {
			firstMonth = 1;
			lastDay = 30;
		}
		else {
			firstMonth = 7;
			lastDay = 31;
		}
		
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT COUNT(*) FROM matricula AS m1 ");
		sql.append("INNER JOIN curso AS c1 ON m1.cur_id = c1.id_cur ");
		sql.append("WHERE m1.data_cadastro >= ");
		sql.append("'" + year + "-" + firstMonth + "-" + "01' ");
		sql.append("AND m1.data_cadastro <= ");
		sql.append("'" + year + "-" + (firstMonth+5) + "-" + lastDay +"' ");
		sql.append("AND c1.descricao = ? ");
		sql.append("AND m1.turma = ?");
		
		try {
			connection.setAutoCommit(false);
			pst = connection.prepareStatement(sql.toString());
			
			pst.setString(1, matricula.getCurso().getDescricao());
			pst.setString(2, matricula.getTurma());
			
			ResultSet rs = pst.executeQuery();
			if(rs.next()) {
				contagem = (int) rs.getLong("count");
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
			try {
				pst.close();
				connection.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return contagem;
	}
	
	public boolean verifyMatricula(Matricula matricula) {
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
		
		Matricula mat = matricula;
		
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT CASE ");
			sql.append("WHEN EXISTS ");
				sql.append("(SELECT ");
				sql.append("m1.id_mat FROM matricula AS m1");
				sql.append(" INNER JOIN aluno AS a1 ");
				sql.append("ON m1.alu_id = a1.id_alu");
				sql.append(" WHERE a1.cpf = ?) ");
			sql.append("THEN true ");
			sql.append("ELSE false ");
		sql.append("END");
		
		try {
			connection.setAutoCommit(false);
			pst = connection.prepareStatement(sql.toString());
			
			pst.setString(1, mat.getAluno().getCpf());
			
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
