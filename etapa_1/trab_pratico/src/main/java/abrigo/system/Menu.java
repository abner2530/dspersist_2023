/*
### Trabalho de Persistencia ###

Alunos: 
    Abner Enoque Monteiro Silva¹ - 511325
    Danilo dos Santos Gomes²     - 536894
____________________________________________

Crie uma classe Principal com um Menu contendo as funcionalidades (Fx) definidas a seguir:
*/

package abrigo.system;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {
    public static void main(String[] args) {
        try {
        Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.print("####  TRABALHO PERSISTENCIA  #### \n" +
                        "         MENU DO ABRIGO      \n" +
                        "                             \n" +
                        "  [1] - Inserir entidade     \n" +
                        "  [2] - Mostrar quantidade   \n" +
                        "  [3] - Converter JSON/XML   \n" +
                        "  [4] - Compacte .zip        \n" +
                        "  [5] - Calcular HASH        \n" +
                        "  [0] - SAIR                 \n" +
                        "---------------------------\n" +
                        "Digite a opção: ");

                int escolha = scanner.nextInt();

                if(escolha > 6){
                    throw new Error("Error: Insira um valor válido.");
                } else {
                    switch (escolha) {
                    case 1:
                        F1_Inserir.f1_Inserir();
                        break;
                    case 2:
                        F2_Quantidade.f2_Quantidade();
                        break;
                    case 3:
                        F3_Converter.f3_Converter();
                        break;
                    case 4:
                        F4_Compactar.f4_Compactar();
                        break;
                    case 5:
                        F5_Hash.f5_Hash();
                        break;
                    case 0:
                        System.out.println("Saindo do programa.");
                        scanner.close();
                        System.exit(0);
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                    }
                }
            }
        } catch (InputMismatchException e ) {
        System.out.println("Error: Insira um valor válido.");
        main(args);
        }
    }
}





