package br.persist.trab2.ui;

import java.util.List;

import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.persist.trab2.dao.AnimalDAO;
import br.persist.trab2.dao.ItemAdocaoDAO;
import br.persist.trab2.entity.Adocao;
import br.persist.trab2.entity.Animal;
import br.persist.trab2.entity.ItemAdocao;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MenuItensAdocao {
    @Autowired
    private ItemAdocaoDAO baseItensAdocao;

    @Autowired
    private AnimalDAO baseAnimais;

    private Adocao adocao;

    public void obterItemAdocao(ItemAdocao item) {
        List<Animal> animais = baseAnimais.findAll();
        Animal animal = (Animal) JOptionPane.showInputDialog(null, "Selecione um animal",
                "Animais", JOptionPane.PLAIN_MESSAGE, null, animais.toArray(), item.getAnimal());
        item.setAnimal(animal);
    }

    public static void listaItemAdocao(ItemAdocao ia) {
        JOptionPane.showMessageDialog(null, ia == null ? "Nenhuma adoção encontrada" : ia);
    }

    public StringBuilder getStringItensAdocao() {
        List<ItemAdocao> itens = baseItensAdocao.findByAdocaoId(this.adocao.getId());
        StringBuilder str = new StringBuilder();
        for (ItemAdocao item : itens) {
            str.append(item).append("\n");
        }
        return str.length() == 0
                ? new StringBuilder("Nenhum item de adoção na adoção (id=").append(adocao.getId()).append(")\n")
                : str;
    }

    public void menu(Adocao adocao) {
        this.adocao = adocao;
        char opcao = '0';
        do {
            try {
                StringBuilder menu = new StringBuilder("Menu itens de adoção (id=").append(adocao.getId()).append(")\n")
                        .append(getStringItensAdocao())
                        .append("1 - Inserir\n")
                        .append("2 - Atualizar por id\n")
                        .append("3 - Remover por id\n")
                        .append("4 - Exibir por id\n")
                        .append("5 - Exibir todos\n")
                        .append("6 - Menu anterior");
                ItemAdocao ia;
                Integer id;
                opcao = JOptionPane.showInputDialog(menu).charAt(0);
                switch (opcao) {
                    case '1': // Inserir
                        ia = new ItemAdocao();
                        obterItemAdocao(ia);
                        ia.setAdocao(adocao);
                        baseItensAdocao.save(ia);
                        break;
                    case '2': // Atualizar por id
                        id = Integer
                                .valueOf(JOptionPane.showInputDialog("Digite o id do item de adoção a ser alterado"));
                        ia = baseItensAdocao.findById(id).orElse(null);
                        if (ia != null) {
                            if (ia.getAdocao().getId() != adocao.getId()) {
                                JOptionPane.showMessageDialog(null,
                                        "O item de adoção " + ia.getId() + " não pertence à adoção " + adocao.getId());
                            } else {
                                obterItemAdocao(ia);
                                baseItensAdocao.save(ia);
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Não foi encontrado item de adoção com o id " + id);
                        }
                        break;
                    case '3': // Remover por id
                        id = Integer.valueOf(JOptionPane.showInputDialog("Digite o id do adoção a ser removida"));
                        ia = baseItensAdocao.findById(id).orElse(null);
                        if (ia != null) {
                            baseItensAdocao.deleteById(ia.getId());
                        } else {
                            JOptionPane.showMessageDialog(null, "Não foi encontrado item de adoção com o id " + id);
                        }
                        break;
                    case '4': // Exibir por id
                        id = Integer.parseInt(JOptionPane.showInputDialog("Digite o id do adoção a ser exibida"));
                        ia = baseItensAdocao.findById(id).orElse(null);
                        if (ia != null) {
                            listaItemAdocao(ia);
                        } else {
                            JOptionPane.showMessageDialog(null, "Não foi encontrado item de adoção com o id " + id);
                        }
                        break;
                    case '5': // Exibir todos
                        JOptionPane.showMessageDialog(null, getStringItensAdocao());
                        break;
                    case '6': // Sair
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Opção Inválida");
                        break;
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
            }

        } while (opcao != '6');
    }
}
