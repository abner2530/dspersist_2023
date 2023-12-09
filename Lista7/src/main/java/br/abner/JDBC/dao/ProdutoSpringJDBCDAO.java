package br.abner.JDBC.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import br.abner.JDBC.entity.Produto;
import lombok.extern.slf4j.Slf4j;

@Repository
@Primary
@Slf4j
public class ProdutoSpringJDBCDAO implements ProdutoDAO {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public ProdutoSpringJDBCDAO() {
    }

    public void save(Produto entity) {
        String insert_sql = "insert into Produtos (codigo, descricao, preco, quantidade, data) values (:codigo, :descricao, :preco, :quantidade, :data)";
        String update_sql = "update Produtos set codigo = :codigo, descricao = :descricao, preco = :preco, quantidade = :quantidade, data = :data where id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource()
            .addValue("codigo", entity.getCodigo())
            .addValue("descricao", entity.getDescricao())
            .addValue("preco", entity.getPreco())
            .addValue("quantidade", entity.getQuantidadeEmEstoque())
            .addValue("data", new java.sql.Date(entity.getDataUltimaEntrada().getTime()));

        if (entity.getId() == null) {
            jdbcTemplate.update(insert_sql, params);
        } else {
            params.addValue("id", entity.getId());
            jdbcTemplate.update(update_sql, params);
        }
    }

    public void delete(int id) {
        String sql = "delete from Produtos where id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource()
            .addValue("id", id);
        jdbcTemplate.update(sql, params);
    }

    public Produto find(int id) {
        Produto produto = null;
        try {
            String sql = "select id, codigo, descricao, preco, quantidade, data from Produtos where id = :id";
            MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", id);
            produto = jdbcTemplate.queryForObject(sql, params, (rs, rowNum) -> map(rs));
        } catch (EmptyResultDataAccessException e) {
            log.debug(e.getMessage());
        }
        return produto;
    }

    private Produto map(ResultSet rs) throws SQLException {
        Produto produto = new Produto();
        produto.setId(rs.getInt("id"));
        produto.setCodigo(rs.getString("codigo"));
        produto.setDescricao(rs.getString("descricao"));
        produto.setPreco(rs.getDouble("preco"));
        produto.setQuantidadeEmEstoque(rs.getInt("quantidade"));
        produto.setDataUltimaEntrada(rs.getDate("data"));
        return produto;
    }

    public List<Produto> find() {
        String sql = "select id, codigo, descricao, preco, quantidade, data from Produtos";
        return jdbcTemplate.query(sql, (rs, rowNum) -> map(rs));
    }

    public Produto findByCodigo(String codigo) {
        Produto produto = null;
        try {
            String sql = "select id, codigo, descricao, preco, quantidade, data from Produtos where codigo = :codigo";
            MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("codigo", codigo);
            produto = jdbcTemplate.queryForObject(sql, params, (rs, rowNum) -> map(rs));
        } catch (EmptyResultDataAccessException e) {
            log.debug(e.getMessage());
        }
        return produto;
    }

    public List<Produto> findByDescricao(String str) {
        String sql = "select id, codigo, descricao, preco, quantidade, data from Produtos where upper(descricao) like :descricao";
        MapSqlParameterSource params = new MapSqlParameterSource()
            .addValue("descricao", "%" + str.toUpperCase() + "%");
        return jdbcTemplate.query(sql, params, (rs, rowNum) -> map(rs));
    }

    public List<Produto> obterProdutosComPrecoMenorOuIgual(double precoMaximo) {
        String sql = "select id, codigo, descricao, preco, quantidade, data from Produtos where preco <= :precoMaximo";
        MapSqlParameterSource params = new MapSqlParameterSource()
            .addValue("precoMaximo", precoMaximo);
        return jdbcTemplate.query(sql, params, (rs, rowNum) -> map(rs));
    }

    public List<Produto> obterProdutosPorDataUltimaEntrada(Date dataInicial, Date dataFinal) {
        String sql = "select id, codigo, descricao, preco, quantidade, data from Produtos where data >= :dataInicial and data <= :dataFinal";
        MapSqlParameterSource params = new MapSqlParameterSource()
            .addValue("dataInicial", new java.sql.Date(dataInicial.getTime()))
            .addValue("dataFinal", new java.sql.Date(dataFinal.getTime()));
        return jdbcTemplate.query(sql, params, (rs, rowNum) -> map(rs));
    }
}
