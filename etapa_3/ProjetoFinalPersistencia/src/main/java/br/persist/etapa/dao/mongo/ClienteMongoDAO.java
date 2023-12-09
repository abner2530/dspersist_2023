package br.persist.etapa.dao.mongo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import br.persist.etapa.dao.ClienteDAO;
import br.persist.etapa.entity.Cliente;

@Repository
public interface ClienteMongoDAO extends ClienteDAO, MongoRepository<Cliente, String> {

    // Consulta baseada no nome do método
    Cliente findFirstByCpf(String cpf);

    // Consulta usando a anotação @Query com consulta BSON
    @Query(value = "{cpf: :#{#cpf}}")
    Cliente buscaPrimeiroPorCpf(String cpf);

    // Consulta baseada no nome do método
    List<Cliente> findByNomeStartingWithIgnoreCase(String str);

    // Consulta usando a anotação @Query com consulta BSON usando regex
    @Query(value = "{nome: {$regex: :#{#nome}, $options: 'i'} }")
    List<Cliente> buscaPorNomeContendoString(String nome);

    // Consulta usando a anotação @Query com consulta BSON para contar todos os documentos
    @Query(value = "{}", count = true)
    int conta();
}
