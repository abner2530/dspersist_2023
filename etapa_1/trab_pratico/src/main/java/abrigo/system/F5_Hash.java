/*
### Trabalho de Persistencia ###

Alunos: 
    Abner Enoque Monteiro Silva¹ - 511325
    Danilo dos Santos Gomes²     - 536894
____________________________________________

Mostrar na tela o hash SHA256 do arquivo CSV. Use a API Java para calcular o hash.
*/

package abrigo.system;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class F5_Hash {

    public static void f5_Hash() {
        String filecsv = "animais.csv";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256"); // Define método de segurança hash
            try (FileInputStream fis = new FileInputStream(filecsv)) {
                byte[] bytes = new byte[1024];
                int tam = 0;

                while ((tam = fis.read(bytes)) != -1) {
                    md.update(bytes, 0, tam);
                }
            }

            byte[] hashBytes = md.digest();

            StringBuilder Hash = new StringBuilder();
            for (byte hashByte : hashBytes) {
                String hex = Integer.toHexString(0xff & hashByte);
                if (hex.length() == 1) {
                    Hash.append('0');
                }
                Hash.append(hex);
            }

            System.out.println("Hash SHA-256 do arquivo " + filecsv + ":");
            System.out.println(Hash.toString());

        } catch (IOException e) {
            System.out.println("Error: Erro ao ler o arquivo CSV.");
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Error: Algoritmo SHA-256 não encontrado.");
        }
    }
}