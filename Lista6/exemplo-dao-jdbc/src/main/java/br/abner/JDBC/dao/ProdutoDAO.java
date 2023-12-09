package br.abner.JDBC.dao;

import java.util.List;

import br.abner.JDBC.entity.Produto;

public interface ProdutoDAO {
	
	public void save(Produto entity);
	
	public void delete(int id);
	
	public Produto find(int id);
	
	public List<Produto> find();
	
	public Produto findByCodigo(String cpf);
	
	public List<Produto> findByDescricao(String str);
}
