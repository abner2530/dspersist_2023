package br.abner.JDBC.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import br.abner.JDBC.entity.Produto;

public class ProdutoJDBCDAO implements ProdutoDAO {

    public ProdutoJDBCDAO() {
    }

    public void save(Produto entity) {
        Connection con = null;
        try {
            con = ConnectionFactory.getConnection();

            String insert_sql = "INSERT INTO produto (codigo, descricao, preco, quantidadeEmEstoque, dataUltimaEntrada) VALUES (?, ?, ?, ?, ?)";
            String update_sql = "UPDATE produto SET codigo = ?, descricao = ?, preco = ?, quantidadeEmEstoque = ?, dataUltimaEntrada = ? WHERE id = ?";
            PreparedStatement pst;
            if (entity.getId() == null) {
                pst = con.prepareStatement(insert_sql);
            } else {
                pst = con.prepareStatement(update_sql);
                pst.setInt(6, entity.getId());
            }
            pst.setString(1, entity.getCodigo());
            pst.setString(2, entity.getDescricao());
            pst.setBigDecimal(3, new BigDecimal(entity.getPreco()));
            pst.setInt(4, entity.getQuantidadeEmEstoque());
            pst.setDate(5, java.sql.Date.valueOf(entity.getDataUltimaEntrada()));
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Operação não realizada com sucesso.", e);
        } finally {
            try {
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                throw new DAOException("Não foi possível fechar a conexão.", e);
            }
        }
    }

    public void delete(int id) {
        Connection con = null;
        try {
            con = ConnectionFactory.getConnection();
            String sql = "DELETE FROM produto WHERE id = ?";
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
                throw new DAOException("Não foi possível fechar a conexão.", e);
            }
        }
    }

    public Produto find(int id) {
        Connection con = null;
        Produto produto = null;
        try {
            con = ConnectionFactory.getConnection();
            String sql = "SELECT id, codigo, descricao, preco, quantidadeEmEstoque, dataUltimaEntrada FROM produto WHERE id = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                produto = map(rs);
            }
        } catch (SQLException e) {
            throw new DAOException("Operação não realizada com sucesso.", e);
        } finally {
            try {
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                throw new DAOException("Não foi possível fechar a conexão.", e);
            }
        }
        return produto;
    }

    private Produto map(ResultSet rs) throws SQLException {
        Produto produto = new Produto();
        produto.setId(rs.getInt("id"));
        produto.setCodigo(rs.getString("codigo"));
        produto.setDescricao(rs.getString("descricao"));
        produto.setPreco(rs.getBigDecimal("preco").doubleValue());
        produto.setQuantidadeEmEstoque(rs.getInt("quantidadeEmEstoque"));
        produto.setDataUltimaEntrada(rs.getDate("dataUltimaEntrada").toLocalDate());
        return produto;
    }

    public List<Produto> find() {
        Connection con = null;
        List<Produto> result = null;
        try {
            con = ConnectionFactory.getConnection();
            PreparedStatement pst;
            String sql = "SELECT id, codigo, descricao, preco, quantidadeEmEstoque, dataUltimaEntrada FROM produto";
            pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            result = new ArrayList<Produto>();
            while (rs.next()) {
                Produto produto = map(rs);
                result.add(produto);
            }
        } catch (SQLException e) {
            throw new DAOException("Operação não realizada com sucesso.", e);
        } finally {
            try {
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                throw new DAOException("Não foi possível fechar a conexão.", e);
            }
        }
        return result;
    }

    public Produto findByCodigo(String codigo) {
        Connection con = null;
        Produto produto = null;
        try {
            con = ConnectionFactory.getConnection();
            PreparedStatement pst;
            String sql = "SELECT id, codigo, descricao, preco, quantidadeEmEstoque, dataUltimaEntrada FROM produto WHERE codigo = ?";
            pst = con.prepareStatement(sql);
            pst.setString(1, codigo);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                produto = map(rs);
            }
        } catch (SQLException e) {
            throw new DAOException("Operação não realizada com sucesso.", e);
        } finally {
            try {
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                throw new DAOException("Não foi possível fechar a conexão.", e);
            }
        }
        return produto;
    }

    public List<Produto> findByDescricao(String str) {
        Connection con = null;
        List<Produto> result = null;
        try {
            con = ConnectionFactory.getConnection();
            PreparedStatement pst;
            String sql = "SELECT id, codigo, descricao, preco, quantidadeEmEstoque, dataUltimaEntrada FROM produto WHERE upper(descricao) LIKE ?";
            pst = con.prepareStatement(sql);
            pst.setString(1, "%" + str.toUpperCase() + "%");
            ResultSet rs = pst.executeQuery();
            result = new ArrayList<Produto>();
            while (rs.next()) {
                Produto produto = map(rs);
                result.add(produto);
            }
        } catch (SQLException e) {
            throw new DAOException("Operação não realizada com sucesso.", e);
        } finally {
            try {
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                throw new DAOException("Não foi possível fechar a conexão.", e);
            }
        }
        return result;
    }

    @Override
    public List<Produto> obterProdutosComPrecoMenorOuIgual(double precoMaximo) {
        Connection con = null;
        List<Produto> result = new ArrayList<>();
        try {
            con = ConnectionFactory.getConnection();
            String sql = "SELECT id, codigo, descricao, preco, quantidadeEmEstoque, dataUltimaEntrada FROM produto WHERE preco <= ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setBigDecimal(1, new BigDecimal(precoMaximo));
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Produto produto = map(rs);
                result.add(produto);
            }
        } catch (SQLException e) {
            throw new DAOException("Operação não realizada com sucesso.", e);
        } finally {
            try {
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                throw new DAOException("Não foi possível fechar a conexão.", e);
            }
        }
        return result;
    }

    @Override
    public List<Produto> obterProdutosPorDataUltimaEntrada(LocalDate dataInicial, LocalDate dataFinal) {
        Connection con = null;
        List<Produto> result = new ArrayList<>();
        try {
            con = ConnectionFactory.getConnection();
            String sql = "SELECT id, codigo, descricao, preco, quantidadeEmEstoque, dataUltimaEntrada FROM produto WHERE dataUltimaEntrada BETWEEN ? AND ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setDate(1, java.sql.Date.valueOf(dataInicial));
            pst.setDate(2, java.sql.Date.valueOf(dataFinal));
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Produto produto = map(rs);
                result.add(produto);
            }
        } catch (SQLException e) {
            throw new DAOException("Operação não realizada com sucesso.", e);
        } finally {
            try {
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                throw new DAOException("Não foi possível fechar a conexão.", e);
            }
        }
        return result;
    }

}
