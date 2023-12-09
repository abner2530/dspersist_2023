package br.persist.etapa.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import br.persist.etapa.entity.Adocao;
import br.persist.etapa.entity.Animal;
import br.persist.etapa.entity.Cliente;

public interface AdocaoDAO {

    public Adocao findFirstByClienteCpfAndAnimalNome(String cpf, String nome);

    public List<Adocao> findAdocoesByClienteId(String clienteId);

    public List<Adocao> findByDataAdocaoBetween(LocalDate dataInicio, LocalDate dataFim);

    public int conta();

    public void save(Adocao adocao);

    public void deleteById(String id);

    public void delete(Adocao adocao);

    public Optional<Adocao> findById(String id);

    public List<Adocao> findAll();

    public List<Adocao> findByClienteNome(String nome);

    public List<Adocao> findByAnimal_Nome(String nome);

    public List<Adocao> findByCliente(Cliente clienteSelecionado);

    public List<Adocao> findByAnimal(Animal animalSelecionado);

}
