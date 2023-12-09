package br.persist.trab2.entity;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = "animais")
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class Animal {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    private String nome;

    @NonNull
    private String tipo;  

    private String sexo;  

    public String toString() {
        return "Animal [id=" + id + ", nome=" + nome + ", tipo=" + tipo + ", sexo=" + sexo + "]";
    }

    public void setGenero(String genero) {
    }
}
