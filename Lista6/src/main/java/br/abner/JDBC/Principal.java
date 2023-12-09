package br.abner.JDBC;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.swing.JOptionPane;

import br.abner.JDBC.dao.ProdutoDAO;
import br.abner.JDBC.dao.ProdutoJDBCDAO;
import br.abner.JDBC.dao.ProdutoListDAO;
import br.abner.JDBC.entity.Produto;

public class Principal {

    public static void main(String[] args) {
        ProdutoDAO baseProduto = new ProdutoJDBCDAO();
        String menu = "Escolha uma opção:\n1 - Inserir\n2 - Atualizar por código\n3 - Remover por código\n4 - Exibir por código\n5 - Exibir por id\n6 - Exibir todos\n7 - Exibir todos que contêm determinada descrição\n8 - Exibir produtos com preço menor ou igual\n9 - Exibir produtos por data de entrada\n10 - Sair";
        String opcao = "0";
        do {
            try {
                Produto cl;
                String codigo;
                opcao = JOptionPane.showInputDialog(menu);
                switch (opcao) {
                    case "1": // Inserir
                        cl = new Produto();
                        obterProduto(cl);
                        baseProduto.save(cl);
                        break;
                    case "2": // Atualizar por código
                        codigo = JOptionPane.showInputDialog("Digite o código do Produto a ser alterado");
                        cl = baseProduto.findByCodigo(codigo);
                        if (cl != null) {
                            obterProduto(cl);
                            baseProduto.save(cl);
                        } else {
                            JOptionPane.showMessageDialog(null, "Produto não encontrado.");
                        }
                        break;
                    case "3": // Remover por código
                        codigo = JOptionPane.showInputDialog("Código");
                        cl = baseProduto.findByCodigo(codigo);
                        if (cl != null) {
                            baseProduto.delete(cl.getId());
                            JOptionPane.showMessageDialog(null, "Produto removido com sucesso.");
                        } else {
                            JOptionPane.showMessageDialog(null, "Produto não encontrado.");
                        }
                        break;
                    case "4": // Exibir por código
                        codigo = JOptionPane.showInputDialog("Código");
                        cl = baseProduto.findByCodigo(codigo);
                        listaProduto(cl);
                        break;
                    case "5": // Exibir por id
                        int id = Integer.parseInt(JOptionPane.showInputDialog("ID"));
                        cl = baseProduto.find(id);
                        listaProduto(cl);
                        break;
                    case "6": // Exibir todos
                        listaProduto(baseProduto.find());
                        break;
                    case "7": // Exibir todos que contêm determinada descrição
                        String descricao = JOptionPane.showInputDialog("Descrição");
                        listaProduto(baseProduto.findByDescricao(descricao));
                        break;
                    case "8": // Exibir produtos com preço menor ou igual
                        double precoMaximo = Double.parseDouble(JOptionPane.showInputDialog("Preço Máximo"));
                        listaProduto(baseProduto.obterProdutosComPrecoMenorOuIgual(precoMaximo));
                        break;
                    case "9": // Exibir produtos por data de entrada
                        Date dataInicial = obterData("Data Inicial (dd/MM/yyyy):");
                        Date dataFinal = obterData("Data Final (dd/MM/yyyy):");
                        listaProduto(baseProduto.obterProdutosPorDataUltimaEntrada(dataInicial, dataFinal));
                        break;
                    case "10": // Sair
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Opção Inválida");
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
            }
        } while (!Objects.equals(opcao, "10"));
    }

    public static void obterProduto(Produto cl) throws ParseException {
		String codigo = JOptionPane.showInputDialog("Código", cl.getCodigo());
		String descricao = JOptionPane.showInputDialog("Descrição", cl.getDescricao());
		double preco = Double.parseDouble(JOptionPane.showInputDialog("Preço", cl.getPreco()));
		int quantidade = Integer.parseInt(JOptionPane.showInputDialog("Quantidade em Estoque", cl.getQuantidadeEmEstoque()));
		Date dataUltimaEntrada = obterData("Data da Última Entrada (dd/MM/yyyy):");
	
		// Convertendo o java.util.Date em java.sql.Date
		java.sql.Date sqlDate = new java.sql.Date(dataUltimaEntrada.getTime());
	
        if (codigo != null ){
		cl.setCodigo(codigo);
		cl.setDescricao(descricao);
		cl.setPreco(preco);
		cl.setQuantidadeEmEstoque(quantidade);
		cl.setDataUltimaEntrada(sqlDate);
        }
	}

    public static void listaProduto(List<Produto> produtos) {
        StringBuilder listagem = new StringBuilder();
        for (Produto cl : produtos) {
            listagem.append(cl).append("\n");
        }
        JOptionPane.showMessageDialog(null, listagem.length() == 0 ? "Nenhum Produto encontrado" : listagem);
    }

    public static void listaProduto(Produto cl) {
        JOptionPane.showMessageDialog(null, cl == null ? "Nenhum Produto encontrado" : cl);
    }

    public static Date obterData(String mensagem) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dataStr = JOptionPane.showInputDialog(mensagem);
        return dateFormat.parse(dataStr);
    }
}
