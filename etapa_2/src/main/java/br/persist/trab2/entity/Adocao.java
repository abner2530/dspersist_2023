package br.persist.trab2.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "adocoes")
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class Adocao {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Cliente cliente;

    @NonNull 
    private LocalDateTime dataHora;

    @OneToMany(mappedBy = "adocao", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemAdocao> itens;

    @Override
    public String toString() {
        return "Adocao [id=" + id + ", cliente=" + cliente + ", dataHora=" + dataHora.format(formatter) + "]";
    }
}
