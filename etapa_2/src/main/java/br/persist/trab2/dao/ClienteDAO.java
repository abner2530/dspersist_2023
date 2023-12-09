package br.persist.trab2.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.persist.trab2.entity.Cliente;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ClienteDAO extends JpaRepository<Cliente, Integer> {

    public Cliente findFirstByCpf(String cpf);

    // consulta derivada do Spring Data JPA
    @Query("select c from Cliente c where c.cpf = :cpf")
    public Cliente buscaPrimeiroPorCpf(String cpf);

    // consulta nomeada
    @Query(name = "clientePorCpf")
    public Cliente buscaPorCpfViaConsultaNomeada(String cpf);

    // Spring Data JPA 
    public List<Cliente> findByNomeStartingWithIgnoreCase(String str);

    // JPQL
    @Query("select c from Cliente c where c.nome ilike %:nome%")
    public List<Cliente> buscaPorNomeContendoString(String nome);

    // consulta SQL nativa
    @Query(value = "select count(*) from clientes c", nativeQuery=true)
    public int conta();
}
