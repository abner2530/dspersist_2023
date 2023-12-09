import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/*
3. Crie uma aplicação em Java que recebe via linha de comando 
(1) o nome de um arquivo a ser decriptado e 
(2) o nome do arquivo resultante da decriptação e 
(3) a chave de decriptação. 
*/

public class Q3 {
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Faltou nome_arquivo_ecriptado ou novo_arquivo_descriptado ou senha");
            return;
        }

        String arquivo_encriptado = args[0];
        String arquivo_decriptado = args[1];
        String key = args[2];

        try {
            File file = new File(arquivo_encriptado);
            FileInputStream is = new FileInputStream(file);
            FileOutputStream os = new FileOutputStream(arquivo_decriptado);

            byte[] tam = key.getBytes();
            byte[] Bytes = new byte[(int) file.length()];
            is.read(Bytes);

            for (int i = 0; i < Bytes.length; i++) {
                Bytes[i] ^= tam[i % tam.length];
            }

            os.write(Bytes);

            is.close();
            os.close();

            System.out.println("Arquivo desencriptado com sucesso!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
