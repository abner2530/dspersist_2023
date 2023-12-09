import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/*
2. Crie uma aplicação em Java que recebe via linha de comando 
(1) o nome de um arquivo a ser encriptado, 
(2) o nome do arquivo encriptado a ser criado e 
(3) a chave de encriptação.
*/

public class Q2 {
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Faltou arquivo a encriptar ou nome_arquivo_ecriptado ou senha");
            return;
        }

        String arquivo = args[0];
        String novo_arquivo = args[1];
        String key = args[2];

        try {
            FileInputStream is = new FileInputStream(arquivo);
            FileOutputStream os = new FileOutputStream(novo_arquivo);

            byte[] tam = key.getBytes();
            byte[] Bytes = new byte[1024]; // Tamanho do buffer de leitura

            int ler_Bytes;

            while ((ler_Bytes = is.read(Bytes)) != -1) {
                for (int i = 0; i < ler_Bytes; i++) {
                    Bytes[i] ^= tam[i % tam.length];
                }
                os.write(Bytes, 0, ler_Bytes);
            }

            is.close();
            os.close();

            System.out.println("Arquivo encriptado com sucesso!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
