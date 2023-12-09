package br.abner.JDBC;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import br.abner.JDBC.dao.ProdutoDAO;
import br.abner.JDBC.entity.Produto;
import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class Principal implements CommandLineRunner {

    @Autowired
    private ProdutoDAO baseProdutos;

    public static void main(String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(Principal.class);
        builder.headless(false).run(args);
    }

    @Override
    public void run(String... args) {
        String menu = "Escolha uma opção:\n1 - Inserir\n2 - Atualizar por Código\n3 - Remover por Código\n4 - Exibir por Código\n5 - Exibir por ID\n6 - Exibir todos\n7 - Exibir todos que contêm determinada descrição\n8 - Exibir produtos com preço menor ou igual\n9 - Exibir produtos por data de entrada\n0 - Sair";
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
                        codigo = JOptionPane.showInputDialog("Digite o Código do Produto a ser alterado");
                        produto = baseProdutos.findByCodigo(codigo);
                        if (produto != null) {
                            obterProduto(produto);
                            baseProdutos.save(produto);
                        } else {
                            JOptionPane.showMessageDialog(null, "Produto não encontrado.");
                        }
                        break;
                    case '3': // Remover por Código
                        codigo = JOptionPane.showInputDialog("Código");
                        produto = baseProdutos.findByCodigo(codigo);
                        if (produto != null) {
                            baseProdutos.delete(produto.getId());
                            JOptionPane.showMessageDialog(null, "Produto removido com sucesso.");
                        } else {
                            JOptionPane.showMessageDialog(null, "Produto não encontrado.");
                        }
                        break;
                    case '4': // Exibir por Código
                        codigo = JOptionPane.showInputDialog("Código");
                        produto = baseProdutos.findByCodigo(codigo);
                        listaProduto(produto);
                        break;
                    case '5': // Exibir por ID
                        int id = Integer.parseInt(JOptionPane.showInputDialog("ID"));
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
                        listaProdutos(baseProdutos.obterProdutosComPrecoMenorOuIgual(precoMaximo));
                        break;
                    case '9': // Exibir produtos por data de entrada
                        Date dataInicial = obterData("Data Inicial (dd/MM/yyyy):");
                        Date dataFinal = obterData("Data Final (dd/MM/yyyy):");
                        listaProdutos(baseProdutos.obterProdutosPorDataUltimaEntrada(dataInicial, dataFinal));
                        break;
                    case '0': // Sair
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Opção Inválida");
                        break;
                }
            } catch (NumberFormatException e) {
                log.error("Erro: {}", e.getMessage(), e);
                JOptionPane.showMessageDialog(null, "Entrada inválida: " + e.getMessage());

            } catch (Exception e) {
                log.error("Erro: {}", e.getMessage(), e);
                JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
            }
        } while (opcao != '0');
    }

    public static void obterProduto(Produto produto) {
        String codigo = JOptionPane.showInputDialog("Código", produto.getCodigo());
        String descricao = JOptionPane.showInputDialog("Descrição", produto.getDescricao());
        double preco = Double.parseDouble(JOptionPane.showInputDialog("Preço", produto.getPreco()));
        int quantidade = Integer.parseInt(JOptionPane.showInputDialog("Quantidade em Estoque", produto.getQuantidadeEmEstoque()));
        Date dataUltimaEntrada = obterData("Data da Última Entrada (dd/MM/yyyy):");

        // Convertendo o java.util.Date em java.sql.Date
        java.sql.Date sqlDate = new java.sql.Date(dataUltimaEntrada.getTime());

        produto.setCodigo(codigo);
        produto.setDescricao(descricao);
        produto.setPreco(preco);
        produto.setQuantidadeEmEstoque(quantidade);
        produto.setDataUltimaEntrada(sqlDate);
    }

    public static void listaProdutos(List<Produto> produtos) {
        StringBuilder listagem = new StringBuilder();
        for (Produto produto : produtos) {
            listagem.append(produto).append("\n");
        }
        JOptionPane.showMessageDialog(null, listagem.length() == 0 ? "Nenhum Produto encontrado" : listagem);
    }

    public static void listaProduto(Produto produto) {
        JOptionPane.showMessageDialog(null, produto == null ? "Nenhum Produto encontrado" : produto);
    }

    public static Date obterData(String mensagem) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dataStr = JOptionPane.showInputDialog(mensagem);
        try {
            return dateFormat.parse(dataStr);
        } catch (ParseException e) {
            log.error("Erro ao obter a data: {}", e.getMessage(), e);
            JOptionPane.showMessageDialog(null, "Data inválida: " + e.getMessage());
            return null;
        }
    }
}
