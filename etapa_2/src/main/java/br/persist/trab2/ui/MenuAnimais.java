package br.persist.trab2.ui;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.persist.trab2.dao.AnimalDAO;
import br.persist.trab2.entity.Animal;

import javax.swing.*;
import java.util.List;

@Slf4j
@Component
public class MenuAnimais {
    @Autowired
    private AnimalDAO baseAnimais;

    public void obterAnimal(Animal animal) {
        String nome = JOptionPane.showInputDialog("Nome", animal.getNome());
        String tipo = JOptionPane.showInputDialog("Tipo", animal.getNome());
        String sexo = JOptionPane.showInputDialog("Sexo", animal.getNome());
        animal.setNome(nome);
        animal.setTipo(tipo);
        animal.setSexo(sexo);
    }

    public void listaAnimais(List<Animal> animais) {
        StringBuilder listagem = new StringBuilder();
        for (Animal animal : animais) {
            listagem.append(animal).append("\n");
        }
        JOptionPane.showMessageDialog(null, listagem.length() == 0 ? "Nenhum animal encontrado" : listagem);
    }

    public void listaAnimal(Animal animal) {
        JOptionPane.showMessageDialog(null, animal == null ? "Nenhum animal encontrado" : animal);
    }

    public void menu() {
        StringBuilder menu = new StringBuilder("Menu Animais\n")
                .append("1 - Inserir\n")
                .append("2 - Atualizar por id\n")
                .append("3 - Remover por id\n")
                .append("4 - Exibir por id\n")
                .append("5 - Exibir todos\n")
                .append("6 - Exibir todos que contém determinado nome\n")
                .append("7 - Menu anterior");
        char opcao = '0';
        do {
            try {
                Animal animal;
                Integer id;
                opcao = JOptionPane.showInputDialog(menu).charAt(0);
                switch (opcao) {
                    case '1':     // Inserir
                        animal = new Animal();
                        obterAnimal(animal);
                        baseAnimais.save(animal);
                        break;
                    case '2':     // Atualizar por id
                        id = Integer.valueOf(JOptionPane.showInputDialog("Digite o id do animal a ser alterado"));
                        animal = baseAnimais.findById(id).orElse(null);
                        if (animal != null) {
                            obterAnimal(animal);
                            baseAnimais.save(animal);
                        } else {
                            JOptionPane.showMessageDialog(null, "Não foi encontrado animal com o id " + id);
                        }
                        break;
                    case '3':     // Remover por id
                        id = Integer.valueOf(JOptionPane.showInputDialog("Digite o id do animal a ser removido"));
                        animal = baseAnimais.findById(id).orElse(null);
                        if (animal != null) {
                            baseAnimais.deleteById(animal.getId());
                        } else {
                            JOptionPane.showMessageDialog(null, "Não foi encontrado animal com o id " + id);
                        }
                        break;
                    case '4':     // Exibir por id
                        id = Integer.parseInt(JOptionPane.showInputDialog("Digite o id do animal a ser exibido"));
                        animal = baseAnimais.findById(id).orElse(null);
                        if (animal != null) {
                            listaAnimal(animal);
                        } else {
                            JOptionPane.showMessageDialog(null, "Não foi encontrado animal com o id " + id);
                        }
                        break;
                    case '5':     // Exibir todos
                        listaAnimais(baseAnimais.findAll());
                        break;
                    case '6':     // Exibir todos que contêm determinado nome
                        String nome = JOptionPane.showInputDialog("Nome");
                        listaAnimais(baseAnimais.findByNomeContainingIgnoreCase(nome));
                        break;
                    case '7':     // Sair
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
