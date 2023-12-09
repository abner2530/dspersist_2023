package br.abner.JDBC;

import java.time.LocalDate;
import java.util.List;

import javax.swing.JOptionPane;

import br.abner.JDBC.dao.ProdutoDAO;
import br.abner.JDBC.dao.ProdutoJPADAO;
import br.abner.JDBC.entity.Produto;

public class Principal {

	public static void main(String[] args) {
		ProdutoDAO baseProdutos = new ProdutoJPADAO();
		StringBuilder menu = new StringBuilder("Escolha uma opção:\n")
				.append("1 - Inserir\n")
				.append("2 - Atualizar por Código\n")
				.append("3 - Remover por Código\n")
				.append("4 - Exibir por Código\n")
				.append("5 - Exibir por id\n")
				.append("6 - Exibir todos\n")
				.append("7 - Exibir todos que contêm determinada descrição\n")
				.append("8 - Exibir produtos com preço menor ou igual\n")
				.append("9 - Exibir produtos por data de entrada\n")
				.append("0 - Sair");
		char opcao = '0';
		do {
			try {
				Produto produto;
				String codigo;
				opcao = JOptionPane.showInputDialog(menu).charAt(0);
				switch (opcao) {
					case '1': // Inserir
						produto = new Produto();
						obterProduto(produto);
						baseProdutos.save(produto);
						break;
					case '2': // Atualizar por Código
						codigo = JOptionPane.showInputDialog("Digite o Código do produto a ser alterado");
						produto = baseProdutos.findByCodigo(codigo);
						obterProduto(produto);
						baseProdutos.save(produto);
						break;
					case '3': // Remover por Código
						codigo = JOptionPane.showInputDialog("Código");
						produto = baseProdutos.findByCodigo(codigo);
						if (produto != null) {
							baseProdutos.delete(produto.getId());
							JOptionPane.showMessageDialog(null, "Produto removido com sucesso.");
						} else {
							JOptionPane.showMessageDialog(null,
									"Não foi possível remover, pois o produto não foi encontrado.");
						}
						break;
					case '4': // Exibir por Código
						codigo = JOptionPane.showInputDialog("Código");
						produto = baseProdutos.findByCodigo(codigo);
						listaProduto(produto);
						break;
					case '5': // Exibir por id
						int id = Integer.parseInt(JOptionPane.showInputDialog("Id"));
						produto = baseProdutos.find(id);
						listaProduto(produto);
						break;
					case '6': // Exibir todos
						listaProdutos(baseProdutos.find());
						break;
					case '7': // Exibir todos que contêm determinada descrição
						String descricao = JOptionPane.showInputDialog("Descrição");
						listaProdutos(baseProdutos.findByDescricao(descricao));
						break;
					case '8': // Exibir produtos com preço menor ou igual
						double precoMaximo = Double.parseDouble(JOptionPane.showInputDialog("Preço Máximo"));
						List<Produto> produtosComPrecoMenorOuIgual = baseProdutos
								.obterProdutosComPrecoMenorOuIgual(precoMaximo);
						listaProdutos(produtosComPrecoMenorOuIgual);
						break;
					case '9': // Exibir produtos por data de entrada
						LocalDate dataInicial = LocalDate
								.parse(JOptionPane.showInputDialog("Data Inicial (yyyy-MM-dd)"));
						LocalDate dataFinal = LocalDate.parse(JOptionPane.showInputDialog("Data Final (yyyy-MM-dd)"));
						List<Produto> produtosPorDataEntrada = baseProdutos
								.obterProdutosPorDataUltimaEntrada(dataInicial, dataFinal);
						listaProdutos(produtosPorDataEntrada);
						break;
					case '0': // Sair
                        break;
					default:
						JOptionPane.showMessageDialog(null, "Opção Inválida");
						break;
				}
			} catch (Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
			}
		} while (opcao != '0');
	}

	public static void obterProduto(Produto produto) {
		String codigo = JOptionPane.showInputDialog("Código", produto.getCodigo());
		String descricao = JOptionPane.showInputDialog("Descrição", produto.getDescricao());
		double preco = Double.parseDouble(JOptionPane.showInputDialog("Preço", produto.getPreco()));
		int quantidadeEmEstoque = Integer.parseInt(JOptionPane.showInputDialog("Quantidade em Estoque", produto.getQuantidadeEmEstoque()));
		
		String dataEntradaStr = JOptionPane.showInputDialog("Data de Entrada (yyyy-MM-dd)", produto.getDataUltimaEntrada());
		LocalDate dataEntrada = LocalDate.parse(dataEntradaStr);
		
		produto.setCodigo(codigo);
		produto.setDescricao(descricao);
		produto.setPreco(preco);
		produto.setQuantidadeEmEstoque(quantidadeEmEstoque);
		produto.setDataUltimaEntrada(dataEntrada);
	}
	

	public static void listaProdutos(List<Produto> produtos) {
		StringBuilder listagem = new StringBuilder();
		for (Produto produto : produtos) {
			listagem.append(produto).append("\n");
		}
		JOptionPane.showMessageDialog(null, listagem.length() == 0 ? "Nenhum produto encontrado" : listagem);
	}

	public static void listaProduto(Produto produto) {
		JOptionPane.showMessageDialog(null, produto == null ? "Nenhum produto encontrado" : produto);
	}
}
