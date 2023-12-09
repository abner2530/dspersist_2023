package br.persist.etapa.entity;

import lombok.*;

import jakarta.persistence.*;
import java.time.LocalDate;

import org.springframework.data.mongodb.core.mapping.Document;

@NamedQueries({
        @NamedQuery(name = "adocaoPorClienteCpfEAnimalNomeViaConsultaNomeada", query = "select a from Adocao a where a.cliente.cpf = :cpf and a.animal.nome = :nome"),

        @NamedQuery(name = "Adocao.findAdocoesByCliente", query = "SELECT a FROM Adocao a WHERE a.cliente = :cliente")
})

@Document
@Entity
@Table(name = "adocoes")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Adocao {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "animal_id")
    private Animal animal;

    @Column(name = "data_adocao")
    private LocalDate dataAdocao;

    @Override
    public String toString() {
        return "Adocao [id=" + id + ", cliente=" + cliente + ", animal=" + animal + ", dataAdocao=" + dataAdocao + "]";
    }

}
