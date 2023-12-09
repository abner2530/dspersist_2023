package br.persist.etapa.entity;

import lombok.*;

import jakarta.persistence.*;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

@NamedQueries({
    @NamedQuery(name = "animalPorNome", query = "select a from Animal a where a.nome = :nome")
})

@Document
@Entity
@Table(name = "animais")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Animal {

    @Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

    @Column(nullable = false)
    private String nome;

    private String tipo;
    private String sexo;
    private int idade;

    @OneToMany(mappedBy = "animal")
    private List<Adocao> adocoes;
    
    @Column(name = "adotado")
    private boolean adotado = false;

    @Override
    public String toString() {
        return "Animal [nome= " + nome + ", tipo= " + tipo + "]";
    }

    public String toStringCompleto() {
        return "Animal [id= " + id + ", nome= " + nome + ", tipo= " + tipo + ", sexo= " + sexo + ", idade= " + idade + "]";
    }

}
