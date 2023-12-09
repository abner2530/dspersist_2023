package br.persist.trab2.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.persist.trab2.entity.Animal;

import java.util.List;

@Repository
public interface AnimalDAO extends JpaRepository<Animal, Integer> {

    public List<Animal> findByNomeContainingIgnoreCase(String str);
    
}
