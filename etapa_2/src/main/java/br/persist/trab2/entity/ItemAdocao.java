package br.persist.trab2.entity;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = "itens_adocoes")
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class ItemAdocao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @NonNull 
    private Adocao adocao;

    @ManyToOne
    @NonNull 
    private Animal animal;

    public String toString() {
        return "ItemAdocao [id=" + id + ", animal=" + animal +"]";
    }
}
