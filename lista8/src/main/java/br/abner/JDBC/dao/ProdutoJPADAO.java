package br.abner.JDBC.dao;

import java.time.LocalDate;
import java.util.List;

import br.abner.JDBC.entity.Produto;
import jakarta.persistence.EntityManager;

public class ProdutoJPADAO extends GenericJPADAO<Produto> implements ProdutoDAO {

    public ProdutoJPADAO() {
        super(Produto.class);
    }

    public void delete(int id) {
        delete(new Produto(id));
    }

    public Produto find(int id) {
        return find(Integer.valueOf(id));
    }

    public Produto findByCodigo(String codigo) {
        EntityManager em = JPAUtil.getEntityManager();
        Produto produto = em.createQuery("SELECT p FROM Produto p WHERE p.codigo = :codigo",
                Produto.class).setParameter("codigo", codigo).getSingleResult();
        JPAUtil.closeEntityManager();
        return produto;
    }

    public List<Produto> findByDescricao(String descricao) {
        EntityManager em = JPAUtil.getEntityManager();
        List<Produto> produtos = em
                .createQuery("SELECT p FROM Produto p WHERE upper(p.descricao) LIKE upper(:descricao)",
                        Produto.class)
                .setParameter("descricao", "%" + descricao + "%").getResultList();
        JPAUtil.closeEntityManager();
        return produtos;
    }

    public List<Produto> obterProdutosComPrecoMenorOuIgual(double precoMaximo) {
        EntityManager em = JPAUtil.getEntityManager();
        List<Produto> produtos = em.createQuery("SELECT p FROM Produto p WHERE p.preco <= :preco", Produto.class)
                .setParameter("preco", precoMaximo)
                .getResultList();
        JPAUtil.closeEntityManager();
        return produtos;
    }

    public List<Produto> obterProdutosPorDataUltimaEntrada(LocalDate dataInicial, LocalDate dataFinal) {
        EntityManager em = JPAUtil.getEntityManager();
        List<Produto> produtos = em
                .createQuery("SELECT p FROM Produto p WHERE p.dataUltimaEntrada BETWEEN :dataInicial AND :dataFinal",
                        Produto.class)
                .setParameter("dataInicial", dataInicial)
                .setParameter("dataFinal", dataFinal)
                .getResultList();
        JPAUtil.closeEntityManager();
        return produtos;
    }

}
