package br.persist.etapa.dao.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import br.persist.etapa.dao.AnimalDAO;
import br.persist.etapa.entity.Animal;

@Repository
public interface AnimalJPADAO extends AnimalDAO, JpaRepository<Animal, String> {

    // Consulta baseada no nome do método
    Animal findFirstByNome(String nome);

    // Consulta JPQL
    @Query("SELECT a FROM Animal a WHERE a.nome = :nome")
    Animal buscaPrimeiroPorNome(String nome);

    // Consulta nomeada
    @Query(name = "animalPorNome")
    Animal buscaPorNomeViaConsultaNomeada(String nome);

    // Consulta baseada no nome do método
    List<Animal> findByTipoStartingWithIgnoreCase(String tipo);

    // Consulta JPQL com ILIKE para consulta por tipo contendo uma string
    @Query("SELECT a FROM Animal a WHERE lower(a.tipo) LIKE lower(concat('%', :tipo, '%'))")
    List<Animal> buscaPorTipoContendoString(String tipo);

    // Consulta nativa
    @Query(value = "SELECT count(*) FROM animais a", nativeQuery = true)
    int conta();

}
