package br.abner.JDBC.entity;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Produto {
	private Integer id;
    private String codigo;
    private String descricao;
    private double preco;
    private int quantidadeEmEstoque;
    private Date dataUltimaEntrada;
    	
}
