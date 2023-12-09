package br.persist.etapa.ui;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import br.persist.etapa.dao.AnimalDAO;
import br.persist.etapa.entity.Animal;

import javax.swing.*;
import java.util.List;

@Slf4j
@Component
public class MenuAnimal {

    @Autowired
    private AnimalDAO baseAnimais;

    public void obterAnimal(Animal animal) {
        String nome = JOptionPane.showInputDialog("Nome", animal.getNome());
        if (nome == null || nome.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nome não pode ser nulo", "Adoção Cancelada",
                    JOptionPane.WARNING_MESSAGE);
                    baseAnimais.delete(animal);
            return;
        }

        
        String[] tipos = { "Cachorro", "Gato", "Coelho", "Ramister", "Outro" };
        JComboBox<String> tipoComboBox = new JComboBox<>(tipos);
        JOptionPane.showMessageDialog(null, tipoComboBox, "Selecione o Tipo", JOptionPane.QUESTION_MESSAGE);
        String tipo = (String) tipoComboBox.getSelectedItem();

        
        String[] sexos = { "Macho", "Fêmea" };
        JComboBox<String> sexoComboBox = new JComboBox<>(sexos);
        JOptionPane.showMessageDialog(null, sexoComboBox, "Selecione o Sexo", JOptionPane.QUESTION_MESSAGE);
        String sexo = (String) sexoComboBox.getSelectedItem();

        int idade = Integer.parseInt(JOptionPane.showInputDialog("Idade", String.valueOf(animal.getIdade())));

        animal.setNome(nome);
        animal.setTipo(tipo);
        animal.setSexo(sexo);
        animal.setIdade(idade);

        baseAnimais.save(animal);
    }

    public void listaAnimais(List<Animal> animais) {
        StringBuilder listagem = new StringBuilder();
        for (Animal animal : animais) {
            listagem.append(animal.toStringCompleto()).append("\n");
        }
        JOptionPane.showMessageDialog(null, listagem.length() == 0 ? "Nenhum animal encontrado" : listagem);
    }

    public void listaAnimal(Animal animal) {
        JOptionPane.showMessageDialog(null, animal == null ? "Nenhum animal encontrado" : animal.toString());
    }

    private Animal escolherAnimalParaExclusao(List<Animal> animais) {
        if (animais.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhum animal encontrado.");
            return null;
        } else {
            // Mostra uma lista de todos os animais e permite que o usuário escolha qual
            // excluir
            Object[] arrayDeAnimais = animais.toArray();
            return (Animal) JOptionPane.showInputDialog(
                    null,
                    "Escolha o animal a ser removido:",
                    "Remover Animal",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    arrayDeAnimais,
                    arrayDeAnimais[0]);
        }
    }

    private Animal escolherAnimalParaAtualizacao(List<Animal> animais) {
        if (animais.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhum animal encontrado.");
            return null;
        } else {
            Object[] arrayDeAnimais = animais.toArray();
            return (Animal) JOptionPane.showInputDialog(
                    null,
                    "Escolha o animal que deseja atualizar:",
                    "Atualizar Animal",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    arrayDeAnimais,
                    arrayDeAnimais[0]);
        }
    }

    public void menu() {

        StringBuilder menu = new StringBuilder("Menu Animais\n")
                .append("1 - Inserir\n")
                .append("2 - Editar Animal\n")
                .append("3 - Remover Animal\n")
                .append("4 - Exibir por Nome\n")
                .append("5 - Exibir por Id\n")
                .append("6 - Exibir todos\n")
                .append("7 - Exibir animais por tipo\n")
                .append("8 - Menu anterior");

        char opcao = '0';
        do {
            try {

                Animal animal;
                String nome;
                opcao = JOptionPane.showInputDialog(menu).charAt(0);

                switch (opcao) {

                    case '1': 
                        animal = new Animal();
                        obterAnimal(animal);
                        break;

                    case '2': 
                        List<Animal> todosAnimaisParaAtualizacao = baseAnimais.findAll();
                        Animal animalParaAtualizacao = escolherAnimalParaAtualizacao(todosAnimaisParaAtualizacao);

                        if (animalParaAtualizacao != null) {
                            obterAnimal(animalParaAtualizacao);
                            baseAnimais.save(animalParaAtualizacao);
                            JOptionPane.showMessageDialog(null, "Animal atualizado.");
                        } else {
                            JOptionPane.showMessageDialog(null, "Operação de atualização cancelada.");
                        }
                        break;

                    case '3': 
                        List<Animal> todosAnimais = baseAnimais.findAll();
                        Animal animalSelecionado = escolherAnimalParaExclusao(todosAnimais);

                        if (animalSelecionado != null) {
                            baseAnimais.delete(animalSelecionado);
                            JOptionPane.showMessageDialog(null, "Animal deletado.");
                        } else {
                            JOptionPane.showMessageDialog(null, "Operação de remoção cancelada.");
                        }
                        break;

                    case '4': 
                        nome = JOptionPane.showInputDialog("Nome");
                        animal = baseAnimais.findFirstByNomeStartingWithIgnoreCase(nome);
                        listaAnimal(animal);
                        break;

                    case '5': 
                        String id = JOptionPane.showInputDialog("Id");
                        animal = baseAnimais.findById(id).orElse(null);
                        listaAnimal(animal);
                        break;

                    case '6': 
                        listaAnimais(baseAnimais.findAll());
                        break;

                    case '7': 
                        String[] tipos = { "Cachorro", "Gato", "Coelho", "Ramister", "Outro" };
                        JComboBox<String> tipoComboBox = new JComboBox<>(tipos);
                        JOptionPane.showMessageDialog(null, tipoComboBox, "Selecione o Tipo",
                                JOptionPane.QUESTION_MESSAGE);
                        String tipoSelecionado = (String) tipoComboBox.getSelectedItem();
                        listaAnimais(baseAnimais.findByTipoStartingWithIgnoreCase(tipoSelecionado));
                        break;

                    case '8': // Sair
                        break;

                    default:
                        JOptionPane.showMessageDialog(null, "Opção Inválida");
                        break;
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
            }

        } while (opcao != '8');
    }
}
