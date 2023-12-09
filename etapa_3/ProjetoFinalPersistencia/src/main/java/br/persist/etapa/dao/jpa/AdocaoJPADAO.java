package br.persist.etapa.dao.jpa;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.persist.etapa.dao.AdocaoDAO;
import br.persist.etapa.entity.Adocao;
import br.persist.etapa.entity.Cliente;

@Repository
public interface AdocaoJPADAO extends AdocaoDAO, JpaRepository<Adocao, String> {

    // Consulta baseada no nome do método
    Adocao findFirstByCliente_CpfAndAnimal_Nome(String cpf, String nome);

    // Consulta JPQL
    @Query("SELECT a FROM Adocao a WHERE a.cliente.cpf = :cpf AND a.animal.nome = :nome")
    Adocao buscaPrimeiraPorClienteCpfEAnimalNome(String cpf, String nome);

    @Query(name = "adocaoPorClienteCpfEAnimalNomeViaConsultaNomeada")
    public Adocao findFirstByCliente_CpfAndAnimal_NomeViaConsultaNomeada(String cpf, String nome);

    List<Adocao> findByDataAdocaoBetween(LocalDate dataInicio, LocalDate dataFim);

    // Consulta JPQL para buscar adoções não finalizadas
    @Query("SELECT a FROM Adocao a WHERE a.dataAdocao IS NULL")
    List<Adocao> buscaAdocoesNaoFinalizadas();

    // Consulta nativa para contar o número de adoções
    @Query(value = "SELECT COUNT(*) FROM adocoes a", nativeQuery = true)
    int conta();

    List<Adocao> findByAnimalNome(String nome);

    public List<Adocao> findByClienteNome(String nome);

    List<Adocao> findByCliente(Cliente cliente);
}
