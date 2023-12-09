package br.persist.trab2.ui;

import java.time.LocalDateTime;
import java.util.List;

import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.persist.trab2.dao.AdocaoDAO;
import br.persist.trab2.dao.ClienteDAO;
import br.persist.trab2.entity.Adocao;
import br.persist.trab2.entity.Cliente;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MenuAdocao {
    @Autowired
    private AdocaoDAO baseAdocoes;

    @Autowired
    private ClienteDAO baseClientes;

    @Autowired
    private MenuItensAdocao menuItensAdocao;

    public void obterESalvarAdocao(Adocao adocao) {
        List<Cliente> clientes = baseClientes.findAll();
        Cliente cl = (Cliente) JOptionPane.showInputDialog(null, "Selecione um cliente",
                "Clientes", JOptionPane.PLAIN_MESSAGE, null, clientes.toArray(), adocao.getCliente());
        adocao.setCliente(cl);
        if (adocao.getDataHora() == null)
            adocao.setDataHora(LocalDateTime.now());
        baseAdocoes.save(adocao);
        menuItensAdocao.menu(adocao);
    }

    public void listarTiposAnimaisAdotadosPorAdocao() {
        try {
            int adocaoId = Integer.parseInt(
                    JOptionPane.showInputDialog("Digite o ID da adoção para ver os tipos de animais adotados:"));
            List<String> tiposAnimais = baseAdocoes.findTiposAnimaisPorAdocaoId(adocaoId);

            if (tiposAnimais.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nenhum tipo de animal encontrado para esta adoção.");
            } else {
                StringBuilder tiposAnimaisMessage = new StringBuilder("Tipos de animais adotados nesta adoção:\n");
                for (String tipo : tiposAnimais) {
                    tiposAnimaisMessage.append("- ").append(tipo).append("\n");
                }
                JOptionPane.showMessageDialog(null, tiposAnimaisMessage.toString());
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
        }
    }

    public void listaAdocoes(List<Adocao> adocoes) {
        StringBuilder listagem = new StringBuilder();
        for (Adocao adocao : adocoes) {
            listagem.append(adocao);
            listagem.append("\n");
        }
        JOptionPane.showMessageDialog(null, listagem.length() == 0 ? "Nenhuma adoção encontrada" : listagem);
    }

    public static void listaAdocao(Adocao adocao) {
        JOptionPane.showMessageDialog(null, adocao == null ? "Nenhuma adoção encontrada" : adocao);
    }

    public void listarAdocoesPorCliente() {
        try {
            int clienteId = Integer
                    .parseInt(JOptionPane.showInputDialog("Digite o ID do cliente para ver suas adoções:"));
            List<Adocao> adocoes = baseAdocoes.findAdocoesPorCliente(clienteId);

            listaAdocoes(adocoes);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
        }
    }

    public void listarTiposAnimaisPorCliente() {
        try {
            int clienteId = Integer.parseInt(
                    JOptionPane.showInputDialog("Digite o ID do cliente para ver os tipos de animais adotados:"));
            List<String> tiposAnimais = baseAdocoes.findTiposAnimaisPorCliente(clienteId);

            if (tiposAnimais.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nenhum tipo de animal encontrado para este cliente.");
            } else {
                StringBuilder tiposAnimaisMessage = new StringBuilder("Tipos de animais adotados por este cliente:\n");
                for (String tipo : tiposAnimais) {
                    tiposAnimaisMessage.append("- ").append(tipo).append("\n");
                }
                JOptionPane.showMessageDialog(null, tiposAnimaisMessage.toString());
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
        }
    }

    public void listarClientesPorTipoAnimal() {
        try {
            String tipoAnimal = JOptionPane
                    .showInputDialog("Digite o tipo de animal para ver os clientes que adotaram:");
            List<String> clientes = baseAdocoes.findClientesPorTipoAnimal(tipoAnimal);

            StringBuilder clientesMessage = new StringBuilder(
                    "Clientes que adotaram o tipo de animal '" + tipoAnimal + "':\n");
            for (String cliente : clientes) {
                clientesMessage.append("- ").append(cliente).append("\n");
            }
            JOptionPane.showMessageDialog(null, clientesMessage.toString());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
        }
    }

    public void menu() {
        StringBuilder menu = new StringBuilder("Menu Adoção\n")
                .append("1 - Inserir\n")
                .append("2 - Atualizar por id\n")
                .append("3 - Remover por id\n")
                .append("4 - Exibir por id\n")
                .append("5 - Exibir todos\n")
                .append("6 - Exibir os tipos de animais de adocao por idAdocao\n")
                .append("7 - Listar adoções por cliente\n")
                .append("8 - Listar tipos de animais adotados por cliente\n")
                .append("9 - Listar clientes por tipo de animal adotado\n")
                .append("0 - Menu anterior");
        String opcao = "";
        do {
            try {
                Adocao adocao;
                Integer id;
                opcao = JOptionPane.showInputDialog(menu);
                switch (opcao) {
                    case "1": // Inserir
                        adocao = new Adocao();
                        obterESalvarAdocao(adocao);
                        break;
                    case "2": // Atualizar por id
                        id = Integer.valueOf(JOptionPane.showInputDialog("Digite o id da adoção a ser alterada"));
                        adocao = baseAdocoes.findById(id).orElse(null);
                        if (adocao != null) {
                            obterESalvarAdocao(adocao);
                        } else {
                            JOptionPane.showMessageDialog(null, "Não foi encontrada adoção com o id " + id);
                        }
                        break;
                    case "3": // Remover por id
                        id = Integer.valueOf(JOptionPane.showInputDialog("Digite o id da adoção a ser removida"));
                        adocao = baseAdocoes.findById(id).orElse(null);
                        if (adocao != null) {
                            baseAdocoes.deleteById(adocao.getId());
                        } else {
                            JOptionPane.showMessageDialog(null, "Não foi encontrada adoção com o id " + id);
                        }
                        break;
                    case "4": // Exibir por id
                        id = Integer.parseInt(JOptionPane.showInputDialog("Digite o id da adoção a ser exibida"));
                        adocao = baseAdocoes.findById(id).orElse(null);
                        if (adocao != null) {
                            listaAdocao(adocao);
                        } else {
                            JOptionPane.showMessageDialog(null, "Não foi encontrada adoção com o id " + id);
                        }
                        break;
                    case "5": // Exibir todos
                        listaAdocoes(baseAdocoes.findAll());
                        break;
                    case "6": // Exibir tipos de animais adotados por uma adoção
                        listarTiposAnimaisAdotadosPorAdocao();
                        break;
                    case "7": // Listar adoções por cliente
                        listarAdocoesPorCliente();
                        break;
                    case "8": // Listar tipos de animais adotados por cliente
                        listarTiposAnimaisPorCliente();
                        break;
                    case "9": // Listar clientes por tipo de animal adotado
                        listarClientesPorTipoAnimal();
                        break;
                    case "0": // Menu anterior
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Opção Inválida");
                        break;
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
            }

        } while (!opcao.equals("0"));
    }
}
