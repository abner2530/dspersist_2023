/*
### Trabalho de Persistencia ###

Alunos: 
    Abner Enoque Monteiro Silva¹ - 511325
    Danilo dos Santos Gomes²     - 536894
____________________________________________

F1. Inserir entidade no arquivo CSV - 
Cadastrar dados relacionados à entidade definida na questão 1. 
Receber dados via teclado e os adicionar através de append a um arquivo CSV.
*/

package abrigo.system;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class F1_Inserir {

    //Método para adicionar animais ao arquivo CSV
    public static void f1_Inserir() {
        String filecsv = "animais.csv"; //Nome do arquivo
         
        try {
        Console console = System.console();
        System.out.println("Digite os dados do animal para inserir no CSV:"); //Leitura dos dados do animal
        System.out.print("Tipo: ");
        String tipo = console.readLine();
        System.out.print("Nome: ");
        String nome = console.readLine();
        System.out.print("Sexo: ");
        String sexo = console.readLine();
        System.out.print("Raça: ");
        String raca = console.readLine();
        System.out.print("Idade: ");
        String idadee = console.readLine();
        System.out.print("Peso: ");
        String pesoo = console.readLine();
        
        int idade = Integer.parseInt(idadee);
        double peso = Double.parseDouble(pesoo);
        UUID id = UUID.randomUUID();
       
        Animal animal = new Animal(id, tipo, nome, sexo, raca, idade, peso); //Aciona o construtor da classe Animal
        
        

        boolean arquivoExiste = new File(filecsv).exists(); //Verificar se o arquivo já existe
        
        FileWriter fw = new FileWriter(filecsv,StandardCharsets.UTF_8,true); //Abre o escritor para adicionar dados ao arquivo

        if(!arquivoExiste) {
            fw.write("id;Tipo;Nome;Sexo;Raça;Idade;Peso\n");
        }

        //Escrever os dados do animal no formato apropriado
        fw.write(animal.getId() + ";" 
        + animal.getTipo() + ";" 
        + animal.getNome() + ";" 
        + animal.getSexo() + ";" 
        + animal.getRaca() + ";"  
        + animal.getIdade() + ";" 
        + animal.getPeso() + "\n");

        fw.flush(); // Escrever os dados do buffer no arquivo imediatamente
        fw.close(); // Fechar o recurso de escrita
        
        System.out.println("Dados inseridos no arquivo CSV.");
        System.out.println("Vamos continuar no programa!");
        
        //scanner.close();
        } catch (IOException e) {
        System.out.println("Error: ao acessar o arquivo CSV.");

        } catch (InputMismatchException e) {
        System.out.println("Error: Dados inseridos incorretamente.");
        }

    }
}