package br.persist.etapa.ui;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import br.persist.etapa.dao.ClienteDAO;
import br.persist.etapa.entity.Cliente;

import javax.swing.*;
import java.util.List;

@Slf4j
@Component
public class MenuClientes {
    @Autowired
    private ClienteDAO baseClientes;

    public void obterCliente(Cliente cl) {
        String nome = JOptionPane.showInputDialog("Nome", cl.getNome());
        String cpf = JOptionPane.showInputDialog("CPF", cl.getCpf());
        String fone = JOptionPane.showInputDialog("Fone", cl.getFone());

        if (nome.trim().isEmpty() || cpf.trim().isEmpty() || fone.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhum campo pode ser Nulo", "Adoção Cancelada",
                    JOptionPane.WARNING_MESSAGE);
                    baseClientes.delete(cl);
            return;
        }

        cl.setNome(nome);
        cl.setCpf(cpf);
        cl.setFone(fone);

        baseClientes.save(cl);
    }

    public void listaClientes(List<Cliente> clientes) {
        StringBuilder listagem = new StringBuilder();
        for(Cliente cl : clientes) {
            listagem.append(cl.toStringCompleto()).append("\n");
        }
        JOptionPane.showMessageDialog(null, listagem.length() == 0 ? "Nenhum cliente encontrado" : listagem);
    }

    public void listaCliente(Cliente cl) {
        JOptionPane.showMessageDialog(null, cl == null ? "Nenhum cliente encontrado" : cl.toString());
    }

    public void menu() {

        StringBuilder menu = new StringBuilder("Menu Clientes\n")
                .append("1 - Inserir\n")
                .append("2 - Atualizar por CPF\n")
                .append("3 - Remover por CPF\n")
                .append("4 - Exibir por CPF\n")
                .append("5 - Exibir por id\n")
                .append("6 - Exibir todos\n")
                .append("7 - Exibir cliente por nome\n")
                .append("8 - Menu anterior");

        char opcao = '0';
        do {
            try {
                Cliente cl;
                String cpf;
                opcao = JOptionPane.showInputDialog(menu).charAt(0);

                switch (opcao) {

                    case '1':     // Inserir
                        cl = new Cliente();
                        obterCliente(cl);
                        break;

                    case '2':     // Atualizar por CPF
                        cpf = JOptionPane.showInputDialog("Digite o CPF do cliente a ser alterado");
                        cl = baseClientes.findFirstByCpf(cpf);
                        if (cl != null) {
                            obterCliente(cl);
                            baseClientes.save(cl);
                        } else {
                            JOptionPane.showMessageDialog(null, "Não foi possível atualizar, pois o cliente não foi encontrado.");
                        }
                        break;

                    case '3':     // Remover por CPF
                        cpf = JOptionPane.showInputDialog("CPF");
                        cl = baseClientes.findFirstByCpf(cpf);
                        if (cl != null) {
                            baseClientes.delete(cl);
                            JOptionPane.showMessageDialog(null, "Cliente Deletado");
                        } else {
                            JOptionPane.showMessageDialog(null, "Não foi possível remover, pois o cliente não foi encontrado.");
                        }
                        break;

                    case '4':     // Exibir por CPF
                        cpf = JOptionPane.showInputDialog("CPF");
                        cl = baseClientes.findFirstByCpf(cpf);
                        listaCliente(cl);
                        break;

                    case '5':     // Exibir por id
                        String id = JOptionPane.showInputDialog("Id");
                        cl = baseClientes.findById(id).orElse(null);
                        listaCliente(cl);
                        break;

                    case '6':     // Exibir todos
                        listaClientes(baseClientes.findAll());
                        break;

                    case '7':     // Exibir cliente por nome
                        String nome = JOptionPane.showInputDialog("Nome");
                        listaClientes(baseClientes.findByNomeStartingWithIgnoreCase(nome));
                        break;

                    case '8':     // Sair
                        break;

                    default:
                        JOptionPane.showMessageDialog(null, "Opção Inválida");
                        break;
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
            }

        } while(opcao != '8');
    }
}
