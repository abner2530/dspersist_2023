package br.abner.JDBC;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.JOptionPane;

import br.abner.JDBC.dao.ProdutoDAO;
import br.abner.JDBC.dao.ProdutoJDBCDAO;
import br.abner.JDBC.entity.Produto;

public class Principal {

	public static void main(String[] args) {	
		ProdutoDAO baseProduto = new ProdutoJDBCDAO();
		String menu = "Escolha uma opção:\n1 - Inserir\n2 - Atualizar por codigo\n3 - Remover por codigo\n4 - Exibir por codigo\n5 - Exibir por id\n6 - Exibir todos\n7 - Exibir todos que contem determinado descricao\n8 - Sair";
		char opcao = '0';
		do {
			try {
				Produto cl;
				String codigo;
				opcao = JOptionPane.showInputDialog(menu).charAt(0);
				switch (opcao) {
				case '1':     // Inserir
					cl = new Produto();
					obterProduto(cl);
					baseProduto.save(cl);
					break;
				case '2':     // Atualizar por codigo
					codigo = JOptionPane.showInputDialog("Digite o codigo do Produto a ser alterado");
					cl = baseProduto.findByCodigo(codigo);
					obterProduto(cl);
					baseProduto.save(cl);
					break;
				case '3':     // Remover por codigo
					codigo = JOptionPane.showInputDialog("codigo");
					cl = baseProduto.findByCodigo(codigo);
					if (cl != null) {
						baseProduto.delete(cl.getId());
					} else {
						JOptionPane.showMessageDialog(null, "Não foi possível remover, pois o Produto não encontrado.");
					}
					break;
				case '4':     // Exibir por codigo
					codigo = JOptionPane.showInputDialog("Codigo");
					cl = baseProduto.findByCodigo(codigo);
					listaProduto(cl);
					break;
				case '5':     // Exibir por id
					int id = Integer.parseInt(JOptionPane.showInputDialog("Id"));
					cl = baseProduto.find(id);
					listaProduto(cl);
					break;
				case '6':     // Exibir todos
					listaProduto(baseProduto.find());
					break;
				case '7':     // Exibir todos que contem determinado descricao
					String descricao = JOptionPane.showInputDialog("Descricao");
					listaProduto(baseProduto.findByDescricao(descricao));
					break;
				case '8':     // Sair
					break;
				default:
					JOptionPane.showMessageDialog(null, "Opção Inválida");
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
			}
		} while(opcao != '8');
	}
	
	public static void obterProduto(Produto cl) throws ParseException {
		String codigo = JOptionPane.showInputDialog("Codigo", cl.getCodigo());
		String descricao = JOptionPane.showInputDialog("Descricao", cl.getDescricao());
		double preco = Double.parseDouble(JOptionPane.showInputDialog("Preco", cl.getPreco()));
		int quantidade = Integer.parseInt(JOptionPane.showInputDialog("Quantidade", cl.getQuantidadeEmEstoque()));
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String dataStr = JOptionPane.showInputDialog("Data", cl.getDataUltimaEntrada());
		java.util.Date data = dateFormat.parse(dataStr);
	
		// Convertendo o java.util.Date em java.sql.Date
		java.sql.Date sqlDate = new java.sql.Date(data.getTime());
	
		cl.setCodigo(codigo);
		cl.setDescricao(descricao);
		cl.setPreco(preco);
		cl.setQuantidadeEmEstoque(quantidade);
		cl.setDataUltimaEntrada(sqlDate); // Define o campo com java.sql.Date
	}

	public static void listaProduto(List<Produto> Produto) {
		StringBuilder listagem = new StringBuilder();
		for(Produto cl : Produto) {
			listagem.append(cl).append("\n");
		}
		JOptionPane.showMessageDialog(null, listagem.length() == 0 ? "Nenhum Produto encontrado" : listagem);
	}

	public static void listaProduto(Produto cl) {
		JOptionPane.showMessageDialog(null, cl == null ? "Nenhum Produto encontrado" : cl);
	}
	
}
