package br.abner.JDBC.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import br.abner.JDBC.entity.Produto;

@Repository
//@Primary
public class ProdutoJDBCDAO implements ProdutoDAO {

    @Autowired
    private DataSource ds;

    public ProdutoJDBCDAO() { }

    public void save(Produto entity) {
        Connection con = null;
        try {
            con = ds.getConnection();
            String insert_sql = "insert into Produtos (codigo, descricao, preco, quantidade, data) values (?, ?, ?, ?, ?)";
            String update_sql = "update Produtos set codigo = ?, descricao = ?, preco = ?, quantidade = ?, data = ? where id = ?";
            PreparedStatement pst;
            if (entity.getId() == null || entity.getId() == 0) {
                pst = con.prepareStatement(insert_sql);
            } else {
                pst = con.prepareStatement(update_sql);
                pst.setInt(6, entity.getId());
            }
            pst.setString(1, entity.getCodigo());
            pst.setString(2, entity.getDescricao());
            pst.setDouble(3, entity.getPreco());
            pst.setInt(4, entity.getQuantidadeEmEstoque());
            pst.setDate(5, new java.sql.Date(entity.getDataUltimaEntrada().getTime()));
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
        try (Connection con = ds.getConnection()) {
            String sql = "delete from Produtos where id = ?";
            try (PreparedStatement pst = con.prepareStatement(sql)) {
                pst.setInt(1, id);
                pst.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DAOException("Operação não realizada com sucesso.", e);
        }
    }

    public Produto find(int id) {
        try (Connection con = ds.getConnection()) {
            String sql = "select id, codigo, descricao, preco, quantidade, data from Produtos where id = ?";
            try (PreparedStatement pst = con.prepareStatement(sql)) {
                pst.setInt(1, id);
                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        return map(rs);
                    }
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Operação não realizada com sucesso.", e);
        }
        return null;
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
        List<Produto> result = new ArrayList<>();
        try (Connection con = ds.getConnection()) {
            String sql = "select id, codigo, descricao, preco, quantidade, data from Produtos";
            try (PreparedStatement pst = con.prepareStatement(sql);
                 ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    result.add(map(rs));
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Operação não realizada com sucesso.", e);
        }
        return result;
    }

    public Produto findByCodigo(String codigo) {
        try (Connection con = ds.getConnection()) {
            String sql = "select id, codigo, descricao, preco, quantidade, data from Produtos where codigo = ?";
            try (PreparedStatement pst = con.prepareStatement(sql)) {
                pst.setString(1, codigo);
                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        return map(rs);
                    }
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Operação não realizada com sucesso.", e);
        }
        return null;
    }

    public List<Produto> findByDescricao(String str) {
        List<Produto> result = new ArrayList<>();
        try (Connection con = ds.getConnection()) {
            String sql = "select id, codigo, descricao, preco, quantidade, data from Produtos where upper(descricao) like ?";
            try (PreparedStatement pst = con.prepareStatement(sql)) {
                pst.setString(1, "%" + str.toUpperCase() + "%");
                try (ResultSet rs = pst.executeQuery()) {
                    while (rs.next()) {
                        result.add(map(rs));
                    }
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Operação não realizada com sucesso.", e);
        }
        return result;
    }

    public List<Produto> obterProdutosComPrecoMenorOuIgual(double precoMaximo) {
        List<Produto> result = new ArrayList<>();
        try (Connection con = ds.getConnection()) {
            String sql = "select id, codigo, descricao, preco, quantidade, data from Produtos where preco <= ?";
            try (PreparedStatement pst = con.prepareStatement(sql)) {
                pst.setDouble(1, precoMaximo);
                try (ResultSet rs = pst.executeQuery()) {
                    while (rs.next()) {
                        result.add(map(rs));
                    }
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Operação não realizada com sucesso.", e);
        }
        return result;
    }

    public List<Produto> obterProdutosPorDataUltimaEntrada(Date dataInicial, Date dataFinal) {
        List<Produto> result = new ArrayList<>();
        try (Connection con = ds.getConnection()) {
            String sql = "select id, codigo, descricao, preco, quantidade, data from Produtos where data >= ? and data <= ?";
            try (PreparedStatement pst = con.prepareStatement(sql)) {
                pst.setDate(1, new java.sql.Date(dataInicial.getTime()));
                pst.setDate(2, new java.sql.Date(dataFinal.getTime()));
                try (ResultSet rs = pst.executeQuery()) {
                    while (rs.next()) {
                        result.add(map(rs));
                    }
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Operação não realizada com sucesso.", e);
        }
        return result;
    }
}
