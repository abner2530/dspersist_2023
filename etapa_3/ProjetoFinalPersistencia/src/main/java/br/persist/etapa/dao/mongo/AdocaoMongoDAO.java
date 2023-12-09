package br.persist.etapa.dao.mongo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import br.persist.etapa.dao.AdocaoDAO;
import br.persist.etapa.entity.Adocao;

@Repository
public interface AdocaoMongoDAO extends AdocaoDAO, MongoRepository<Adocao, String> {

    // Consulta baseada no nome do método
    Adocao findFirstByCliente_CpfAndAnimal_Nome(String cpf, String nome);

    // Consulta usando a anotação @Query com consulta BSON
    @Query(value = "{$and: [{'cliente.cpf': :#{#cpf}}, {'animal.nome': :#{#nome}}]}")
    Adocao buscaPrimeiraPorClienteCpfEAnimalNome(String cpf, String nome);

    @Query("{ 'data' : { $gte : ?0, $lte : ?1 } }")
    List<Adocao> findByDataAdocaoBetweenMongo(LocalDate dataInicio, LocalDate dataFim);

    // Consulta baseada no nome do método
    List<Adocao> findByDataAdocaoIsNull();

    // Consulta usando a anotação @Query com consulta BSON para contar o número de adoções
    @Query(value = "{}", count = true)
    int conta();

    @Query("{ 'cliente.nome' : ?0 }")
    List<Adocao> findByClienteNome(String nome);

    // Consulta baseada no nome do método
    List<Adocao> findByAnimal_Nome(String nome);

    // Consulta baseada no nome do método
    List<Adocao> findByCliente_Cpf(String cpf);
}
