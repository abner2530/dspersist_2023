package br.abner.JDBC.dao;

import java.time.LocalDate;
import java.util.List;

import br.abner.JDBC.entity.Produto;

public interface ProdutoDAO {
	
	public void save(Produto entity);
	
	public void delete(int id);
	
	public Produto find(int id);
	
	public List<Produto> find();
	
	public Produto findByCodigo(String codigo);
	
	public List<Produto> findByDescricao(String str);

    public List<Produto> obterProdutosComPrecoMenorOuIgual(double precoMaximo);
	
	public List<Produto> obterProdutosPorDataUltimaEntrada(LocalDate dataInicial, LocalDate dataFinal);
}
