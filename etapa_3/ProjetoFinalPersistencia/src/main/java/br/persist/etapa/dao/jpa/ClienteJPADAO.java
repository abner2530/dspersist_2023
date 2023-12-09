package br.persist.etapa.dao.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import br.persist.etapa.dao.ClienteDAO; 
import br.persist.etapa.entity.Cliente;

@Repository
public interface ClienteJPADAO extends ClienteDAO, JpaRepository<Cliente, String> {

    // Consulta baseada no nome do método
    Cliente findFirstByCpf(String cpf);

    // Consulta JPQL
    @Query("SELECT c FROM Cliente c WHERE c.cpf = :cpf")
    Cliente buscaPrimeiroPorCpf(String cpf);

    // Consulta nomeada
    @Query(name = "clientePorCpf")
    public Cliente buscaPorCpfViaConsultaNomeada(String cpf);

    // Consulta baseada no nome do método
    List<Cliente> findByNomeStartingWithIgnoreCase(String str);

    // Consulta JPQL com ILIKE para consulta por nome contendo uma string
    @Query("SELECT c FROM Cliente c WHERE lower(c.nome) LIKE lower(concat('%', :nome, '%'))")
    List<Cliente> buscaPorNomeContendoString(String nome);

    // Consulta nativa
    @Query(value = "SELECT COUNT(*) FROM clientes c", nativeQuery = true)
    int conta();
}
