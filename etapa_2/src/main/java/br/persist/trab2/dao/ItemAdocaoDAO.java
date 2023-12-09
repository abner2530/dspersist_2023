package br.persist.trab2.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.persist.trab2.entity.ItemAdocao;

import java.util.List;

@Repository
public interface ItemAdocaoDAO extends JpaRepository<ItemAdocao, Integer> {

    @Query("SELECT ia FROM ItemAdocao ia WHERE ia.adocao.id = :id")
    public List<ItemAdocao> findByAdocaoId(int id);

}
