package br.abner.JDBC.dao;
//import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.abner.JDBC.entity.Produto;

public class ProdutoJDBCDAO implements ProdutoDAO {

	public ProdutoJDBCDAO() { }
	
	public void save(Produto entity) {
		Connection con = null;
		try {
			con = ConnectionFactory.getConnection();
			String insert_sql = "insert into Produtos (codigo, descricao, preco, quantidade, data) values (?, ?, ?, ?, ?)";
			String update_sql = "update Produtos set codigo = ?, descricao = ?, preco = ?, quantidade = ?, data = ? where id = ?";
			PreparedStatement pst;
			if (entity.getId() == 0) {
				pst = con.prepareStatement(insert_sql);
			} else {
				pst = con.prepareStatement(update_sql);
				pst.setInt(6, entity.getId());
			}
			pst.setString(1, entity.getCodigo());
			pst.setString(2, entity.getDescricao());
			pst.setDouble(3, entity.getPreco());
			pst.setInt(4, entity.getQuantidadeEmEstoque());
            pst.setDate(5, entity.getDataUltimaEntrada());
			pst.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException("Operação não realizada com sucesso.", e);
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (SQLException e) {
				throw new DAOException("Não foi possível fechar a conexão.",e);
			}
		}
	}

	public void delete(int id) {
		Connection con = null;
		try {
			con = ConnectionFactory.getConnection();
			String sql = "delete from Produtos where id = ?";
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setInt(1, id);
			pst.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException("Operação não realizada com sucesso.", e);
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (SQLException e) {
				throw new DAOException("Não foi possível fechar a conexão.",e);
			}
		}
	}

	public Produto find(int id) {
		Connection con = null;
		Produto cl = null;
		try {
			con = ConnectionFactory.getConnection();
			String sql = "select id, codigo, descricao, preco, quantidade, data from Produtos where id = ?";
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				cl = map(rs);
			}
		} catch (SQLException e) {
			throw new DAOException("Operação não realizada com sucesso.", e);
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (SQLException e) {
				throw new DAOException("Não foi possível fechar a conexão.",e);
			}
		}
		return cl;
	}

	private Produto map(ResultSet rs) throws SQLException {
		Produto cl = new Produto();
		cl.setId(rs.getInt("id"));
		cl.setCodigo(rs.getString("codigo"));
		cl.setDescricao(rs.getString("descricao"));
		cl.setPreco(rs.getDouble("preco"));
		cl.setQuantidadeEmEstoque(rs.getInt("quantidade"));
        cl.setDataUltimaEntrada(rs.getDate("data"));
		return cl;
	}

	public List<Produto> find() {
		Connection con = null;
		List<Produto> result = null;
		try {
			con = ConnectionFactory.getConnection();
			PreparedStatement pst;
			String sql = "select id, codigo, descricao, preco, quantidade, data from Produtos";
			pst = con.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			result = new ArrayList<Produto>();
			while (rs.next()) {
				Produto cl = map(rs);
				result.add(cl);
			}
		} catch (SQLException e) {
			throw new DAOException("Operação não realizada com sucesso.", e);
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (SQLException e) {
				throw new DAOException("Não foi possível fechar a conexão.",e);
			}
		}
		return result;
	}

	// public Produto findBycodigo(String codigo) {
	// 	Connection con = null;
	// 	Produto cl = null;
	// 	try {
	// 		con = ConnectionFactory.getConnection();
	// 		PreparedStatement pst;
	// 		String sql = "select codigo, descricao, preco, quantidade, data from Produtos where codigo = ?";
	// 		pst = con.prepareStatement(sql);
	// 		pst.setString(1, codigo);
	// 		ResultSet rs = pst.executeQuery();
	// 		if (rs.next()) {
	// 			cl = map(rs);
	// 		}
	// 	} catch (SQLException e) {
	// 		throw new DAOException("Operação não realizada com sucesso.", e);
	// 	} finally {
	// 		try {
	// 			if (con != null)
	// 				con.close();
	// 		} catch (SQLException e) {
	// 			throw new DAOException("Não foi possível fechar a conexão.",e);
	// 		}
	// 	}
	// 	return cl;
	// }

    @Override
    public Produto findByCodigo(String codigo) {
        Connection con = null;
		Produto cl = null;
		try {
			con = ConnectionFactory.getConnection();
			PreparedStatement pst;
			String sql = "select id, codigo, descricao, preco, quantidade, data from Produtos where codigo = ?";
			pst = con.prepareStatement(sql);
			pst.setString(1, codigo);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				cl = map(rs);
			}
		} catch (SQLException e) {
			throw new DAOException("Operação não realizada com sucesso.", e);
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (SQLException e) {
				throw new DAOException("Não foi possível fechar a conexão.",e);
			}
		}
		return cl;
    }

    @Override
    public List<Produto> findByDescricao(String str) {
        Connection con = null;
		List<Produto> result = null;
		try {
			con = ConnectionFactory.getConnection();
			PreparedStatement pst;
			String sql = "select id, codigo, descricao, preco, quantidade, data from Produtos where upper(descricao) like ?";
			pst = con.prepareStatement(sql);
			pst.setString(1, "%" + str.toUpperCase() + "%");
			ResultSet rs = pst.executeQuery();
			result = new ArrayList<Produto>();
			while (rs.next()) {
				Produto cl = map(rs);
				result.add(cl);
			}
		} catch (SQLException e) {
			throw new DAOException("Operação não realizada com sucesso.", e);
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (SQLException e) {
				throw new DAOException("Não foi possível fechar a conexão.",e);
			}
		}
		return result;
    }

    // public Produto findByID(String codigo) {
	// 	Connection con = null;
	// 	Produto cl = null;
	// 	try {
	// 		con = ConnectionFactory.getConnection();
	// 		PreparedStatement pst;
	// 		String sql = "select codigo, descricao, preco, quantidade, data from Produtos where codigo = ?";
	// 		pst = con.prepareStatement(sql);
	// 		pst.setString(1, codigo);
	// 		ResultSet rs = pst.executeQuery();
	// 		if (rs.next()) {
	// 			cl = map(rs);
	// 		}
	// 	} catch (SQLException e) {
	// 		throw new DAOException("Operação não realizada com sucesso.", e);
	// 	} finally {
	// 		try {
	// 			if (con != null)
	// 				con.close();
	// 		} catch (SQLException e) {
	// 			throw new DAOException("Não foi possível fechar a conexão.",e);
	// 		}
	// 	}
	// 	return cl;
	// }

	// public List<Produto> findByNome(String str) {
	// 	Connection con = null;
	// 	List<Produto> result = null;
	// 	try {
	// 		con = ConnectionFactory.getConnection();
	// 		PreparedStatement pst;
	// 		String sql = "select codigo, descricao, preco, quantidade, data from Produtos where upper(descricao) like ?";
	// 		pst = con.prepareStatement(sql);
	// 		pst.setString(1, "%" + str.toUpperCase() + "%");
	// 		ResultSet rs = pst.executeQuery();
	// 		result = new ArrayList<Produto>();
	// 		while (rs.next()) {
	// 			Produto cl = map(rs);
	// 			result.add(cl);
	// 		}
	// 	} catch (SQLException e) {
	// 		throw new DAOException("Operação não realizada com sucesso.", e);
	// 	} finally {
	// 		try {
	// 			if (con != null)
	// 				con.close();
	// 		} catch (SQLException e) {
	// 			throw new DAOException("Não foi possível fechar a conexão.",e);
	// 		}
	// 	}
	// 	return result;
	// }
	
}
