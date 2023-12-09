package br.persist.trab2.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.persist.trab2.entity.Adocao;

import java.util.List;

@Repository
public interface AdocaoDAO extends JpaRepository<Adocao, Integer> {

        @Query("SELECT a FROM Adocao a " +
                        "left join fetch a.cliente " +
                        "left join fetch a.itens i " +
                        "left join fetch i.animal " +
                        "where a.id in " +
                        "(SELECT a.id FROM Adocao a join a.itens i group by a having count(i) >= :quantidadeMinima)")
        public List<Adocao> findAdocoesComQuantidadeMinima(int quantidadeMinima);

        @Query("SELECT DISTINCT animal.tipo FROM Adocao adocao " +
                        "JOIN adocao.itens item " +
                        "JOIN item.animal animal " +
                        "WHERE adocao.id = :adocaoId")
        public List<String> findTiposAnimaisPorAdocaoId(@Param("adocaoId") int adocaoId);

        @Query("SELECT a FROM Adocao a WHERE a.cliente.id = :clienteId")
        List<Adocao> findAdocoesPorCliente(@Param("clienteId") int clienteId);

        @Query("SELECT DISTINCT animal.tipo FROM Adocao adocao " +
                        "JOIN adocao.itens item " +
                        "JOIN item.animal animal " +
                        "WHERE adocao.cliente.id = :clienteId")
        List<String> findTiposAnimaisPorCliente(@Param("clienteId") int clienteId);

        @Query("SELECT DISTINCT cliente.nome FROM Adocao adocao " +
                        "JOIN adocao.cliente cliente " +
                        "JOIN adocao.itens item " +
                        "JOIN item.animal animal " +
                        "WHERE animal.tipo = :tipoAnimal")
        List<String> findClientesPorTipoAnimal(@Param("tipoAnimal") String tipoAnimal);

}
