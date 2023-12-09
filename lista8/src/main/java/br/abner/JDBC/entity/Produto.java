package br.abner.JDBC.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Produto {

    public Produto(int id) {
		this.id = id;
	}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String codigo;

    private String descricao;
    private double preco;
    private int quantidadeEmEstoque;
    private LocalDate dataUltimaEntrada;
}
