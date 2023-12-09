package br.persist.etapa.ui;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import br.persist.etapa.dao.AdocaoDAO;
import br.persist.etapa.dao.AnimalDAO;
import br.persist.etapa.dao.ClienteDAO;
import br.persist.etapa.entity.Adocao;
import br.persist.etapa.entity.Animal;
import br.persist.etapa.entity.Cliente;

import javax.swing.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class MenuAdocao {
    @Autowired
    private AdocaoDAO baseAdocoes;

    @Autowired
    private ClienteDAO baseClientes;

    @Autowired
    private AnimalDAO baseAnimais;

    public void obterAdocao(Adocao adocao) {
        List<Cliente> clientes = baseClientes.findAll();
        Cliente clienteSelecionado = selecionarCliente(clientes);

        if (clienteSelecionado == null) {
            JOptionPane.showMessageDialog(null, "Nenhum cliente selecionado.", "Adoção Cancelada",
                    JOptionPane.WARNING_MESSAGE);
            baseAdocoes.delete(adocao);
            return;
        }

        List<Animal> animais = baseAnimais.findAll();
        Animal animalSelecionado = selecionarAnimal(animais);

        if (animalSelecionado == null) {
            JOptionPane.showMessageDialog(null, "Nenhum animal selecionado.", "Adoção Cancelada",
                    JOptionPane.WARNING_MESSAGE);
            baseAdocoes.delete(adocao);
            return;
        }

        adocao.setCliente(clienteSelecionado);
        adocao.setAnimal(animalSelecionado);
        adocao.setDataAdocao(LocalDate.now());

        animalSelecionado.setAdotado(true);

        JOptionPane.showMessageDialog(null, "Adoção realizada com sucesso!\n\n" +
                "Cliente: " + clienteSelecionado.getNome() + "\n" +
                "Animal: " + animalSelecionado.getNome() + "\n" +
                "Data da Adoção: " + adocao.getDataAdocao(), "Adoção Concluída", JOptionPane.INFORMATION_MESSAGE);

        baseAnimais.save(animalSelecionado);
        baseAdocoes.save(adocao);
    }

    public void listaAdocoes(List<Adocao> adocoes) {
        StringBuilder listagem = new StringBuilder();
        for (Adocao adocao : adocoes) {
            listagem.append(adocao.toString()).append("\n");
        }
        JOptionPane.showMessageDialog(null, listagem.length() == 0 ? "Nenhuma adoção encontrada" : listagem);
    }

    private Cliente selecionarCliente(List<Cliente> clientes) {

        Object[] opcoes = clientes.toArray();
        Object clienteSelecionado = JOptionPane.showInputDialog(null,
                "Selecione o Cliente para Adoção:",
                "Seleção de Cliente",
                JOptionPane.PLAIN_MESSAGE,
                null,
                opcoes,
                null);

        return (Cliente) clienteSelecionado;
    }

    private Animal selecionarAnimalSemRestricao(List<Animal> animais) {
        if (animais.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhum animal disponível.", "Adoção Cancelada",
                    JOptionPane.WARNING_MESSAGE);
            return null;
        }

        Object[] opcoes = animais.toArray();
        Object animalSelecionado = JOptionPane.showInputDialog(null,
                "Selecione o Animal para Adoção:",
                "Seleção de Animal",
                JOptionPane.PLAIN_MESSAGE,
                null,
                opcoes,
                null);

        return (Animal) animalSelecionado;
    }

    private Animal selecionarAnimal(List<Animal> animais) {
        // Filtrar animais que ainda não foram adotados
        List<Animal> animaisDisponiveis = animais.stream()
                .filter(animal -> !animal.isAdotado())
                .collect(Collectors.toList());

        if (animaisDisponiveis.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Todos os animais foram adotados.", "Adoção Cancelada",
                    JOptionPane.WARNING_MESSAGE);
            return null;
        }

        Object[] opcoes = animaisDisponiveis.toArray();
        Object animalSelecionado = JOptionPane.showInputDialog(null,
                "Selecione o Animal para Adoção:",
                "Seleção de Animal",
                JOptionPane.PLAIN_MESSAGE,
                null,
                opcoes,
                null);

        return (Animal) animalSelecionado;
    }

    private void exibirAdocaoPorCliente() {
        Cliente clienteSelecionado = selecionarCliente(baseClientes.findAll());

        if (clienteSelecionado != null) {
            List<Adocao> adocoesPorCliente = baseAdocoes.findByCliente(clienteSelecionado);
            listaAdocoes(adocoesPorCliente);
        } else {
            JOptionPane.showMessageDialog(null, "Operação de exibição cancelada.");
        }
    }

    private void exibirAdocaoPorAnimal() {
        Animal animalSelecionado = selecionarAnimalSemRestricao(baseAnimais.findAll());

        if (animalSelecionado != null) {
            List<Adocao> adocoesPorAnimal = baseAdocoes.findByAnimal(animalSelecionado);
            listaAdocoes(adocoesPorAnimal);
        } else {
            JOptionPane.showMessageDialog(null, "Operação de exibição cancelada.");
        }
    }

    public void listaAdocao(Adocao adocao) {
        JOptionPane.showMessageDialog(null, adocao == null ? "Nenhuma adoção encontrada" : adocao.toString());
    }

    private void exibirAdocaoPorPeriodoJPA() {
        try {
            LocalDate dataInicio = LocalDate
                    .parse(JOptionPane.showInputDialog("Digite a data de início (formato: yyyy-MM-dd)"));
            LocalDate dataFim = LocalDate
                    .parse(JOptionPane.showInputDialog("Digite a data de fim (formato: yyyy-MM-dd)"));

            List<Adocao> adocoes = baseAdocoes.findByDataAdocaoBetween(dataInicio.plusDays(1), dataFim.plusDays(1));
            listaAdocoes(adocoes);
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(null, "Formato de data inválido. Use o formato: yyyy-MM-dd");
        }
    }

    private void exibirQuantidadeAdocoes() {
        JOptionPane.showMessageDialog(null, "Quantidade de adoções: " + baseAdocoes.conta());
    }

    public void menu() {

        StringBuilder menu = new StringBuilder("Menu Adoções\n")
                .append("1 - Realizar Adoção\n")
                .append("2 - Adoções por cliente\n")
                .append("3 - Exibir todas as Adoções\n")
                .append("4 - Exibir Adoções por Data (yy-mm-dd)\n")
                .append("5 - Exibir Quantidade de Adoções\n")
                .append("6 - Adoções por Animal\n")
                .append("7 - Menu anterior");

        char opcao = '0';
        do {
            try {
                Adocao adocao;
                opcao = JOptionPane.showInputDialog(menu).charAt(0);

                switch (opcao) {

                    case '1':
                        adocao = new Adocao();
                        obterAdocao(adocao);
                        break;

                    case '2':
                        exibirAdocaoPorCliente();
                        break;

                    case '3':
                        listaAdocoes(baseAdocoes.findAll());
                        break;

                    case '4':
                        exibirAdocaoPorPeriodoJPA();
                        break;

                    case '5':
                        exibirQuantidadeAdocoes();
                        break;

                    case '6':
                        exibirAdocaoPorAnimal();
                        break;

                    case '7':
                        break;

                    default:
                        JOptionPane.showMessageDialog(null, "Opção Inválida");
                        break;
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
            }

        } while (opcao != '7');
    }
}
