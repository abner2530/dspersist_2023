package br.persist.etapa.dao.mongo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import br.persist.etapa.dao.AnimalDAO;
import br.persist.etapa.entity.Animal;

@Repository
public interface AnimalMongoDAO extends AnimalDAO, MongoRepository<Animal, String> {

    // Consulta baseada no nome do método
    Animal findFirstByNome(String nome);

    // Consulta usando a anotação @Query com consulta BSON
    @Query(value = "{nome: :#{#nome}}")
    Animal buscaPrimeiroPorNome(String nome);

    // Consulta nomeada não é suportada diretamente no MongoDB, então usamos o método padrão de nomenclatura
    Animal findByNome(String nome);

    // Consulta baseada no nome do método
    List<Animal> findByTipoStartingWithIgnoreCase(String tipo);

    // Consulta usando a anotação @Query com consulta BSON para consulta por tipo contendo uma string
    @Query(value = "{tipo: {$regex: :#{#tipo}, $options: 'i'}}")
    List<Animal> buscaPorTipoContendoString(String tipo);

    // Consulta usando a anotação @Query com consulta BSON para contar o número de animais
    @Query(value = "{}", count = true)
    int conta();

    // Consulta usando a anotação @Query com consulta BSON para verificar se o animal foi adotado por outro cliente
    @Query(value = "{_id: :#{#animalId}, adotado: true, clienteAdotante.id: {$ne: :#{#clienteId}}}")
    boolean isAdotadoPorOutroCliente(Integer animalId, Integer clienteId);
}
