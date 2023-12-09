/*
### Trabalho de Persistencia ###

Alunos: 
    Abner Enoque Monteiro Silva¹ - 511325
    Danilo dos Santos Gomes²     - 536894
____________________________________________

F2. Mostrar a quantidade de entidades existentes no arquivo CSV. 
Mesmo que você saia da aplicação e a reexecute futuramente, 
esta funcionalidade deve mostrar a real quantidade de entidades existentes no arquivo CSV, 
mesmo que dados sejam inseridos ou removidos no arquivo CSV através de um editor de texto externo à sua aplicação.
*/

package abrigo.system;

import java.io.*;

public class F2_Quantidade {
    public static void f2_Quantidade() {
        String filecsv = "animais.csv";

        try {
            // Abrir o arquivo CSV em modo de leitura
            FileReader fr = new FileReader(filecsv);
            BufferedReader br = new BufferedReader(fr);
             
            int qtd_Entidades = 0;

            boolean arquivoExiste = new File(filecsv).exists();

            if (arquivoExiste) {
                qtd_Entidades = -1;
            }
            
            while (br.readLine() != null) {
                qtd_Entidades++;
            }
            // Fechar o arquivo
            br.close();

            System.out.println("Quantidade de entidades: " + qtd_Entidades);
            System.out.println("Vamos continuar no programa!");

        } catch (IOException e) {
            System.out.println("Error: Erro ao acessar o arquivo CSV.");
        }
    }
}