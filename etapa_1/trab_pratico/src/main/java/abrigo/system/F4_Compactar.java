/*
### Trabalho de Persistencia ###

Alunos: 
    Abner Enoque Monteiro Silva¹ - 511325
    Danilo dos Santos Gomes²     - 536894
____________________________________________

Compactar o arquivo CSV e gerar um novo arquivo de mesmo nome, mas com a extensão ZIP. Deve-se usar a API Java para realizar a compressão.

*/

package abrigo.system;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class F4_Compactar {

    public static void f4_Compactar() {
        String filecsv = "animais.csv";
        String filezip = "animais.zip";
        
        try {
            FileInputStream fis = new FileInputStream(filecsv);
            FileOutputStream fos = new FileOutputStream(filezip);
            ZipOutputStream zipOut = new ZipOutputStream(fos);

            ZipEntry zET = new ZipEntry(filecsv);
            zipOut.putNextEntry(zET);

            byte[] bytes = new byte[1024];
            int tam;
            while ((tam = fis.read(bytes)) >= 0) {
                zipOut.write(bytes, 0, tam);
            }

            zipOut.close();
            fos.close();
            fis.close();

            System.out.println("Arquivo compactado e salvo como " + filezip);
        } catch (IOException e) {
            System.out.println("Error: Erro ao compactar o arquivo CSV.");
        }
    }
}