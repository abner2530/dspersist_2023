package br.persist.etapa.entity;

import lombok.*;

import jakarta.persistence.*;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

@NamedQueries({
    @NamedQuery(name = "clientePorCpf", query = "select c from Cliente c where c.cpf = :cpf")
})

@Document
@Entity
@Table(name = "clientes")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Cliente {

    @Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

    @Column(unique = true, nullable = false)
    private String cpf;

    private String nome;
    private String fone;

    @OneToMany(mappedBy = "cliente")
    private List<Adocao> adocoes;

    @Override
    public String toString() {
        return "Cliente [nome= " + nome + ", cpf= " + cpf + "]";
    }

    public String toStringCompleto() {
        return "Cliente [id= " + id + ", nome= " + nome + ", cpf= " + cpf + "]";
    }
}
