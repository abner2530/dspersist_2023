package br.persist.etapa.dao;

import java.util.List;
import java.util.Optional;

import br.persist.etapa.entity.Animal;

public interface AnimalDAO {

    public Animal findFirstByNomeStartingWithIgnoreCase(String nome);

    public Animal buscaPrimeiroPorNome(String nome);

    public List<Animal> findByTipoStartingWithIgnoreCase(String str);

    public List<Animal> buscaPorTipoContendoString(String tipo);

    public int conta();

    public void save(Animal animal);

    public void deleteById(String id);

    public void delete(Animal animal);

    public Optional<Animal> findById(String id);

    public List<Animal> findAll();
}
