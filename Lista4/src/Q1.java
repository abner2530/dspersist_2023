import java.io.*;
import java.util.zip.*;

/*
1. Crie uma aplicação em Java que recebe via linha de comando (1) o nome de um arquivo compactado a ser criado e (2) uma pasta. 
Compactar todos os arquivos e subpastas em um arquivo compactado com extensão zip. 
*/

public class Q1 {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Uso: Faltou arquivo.zip ou pasta_destino");
            System.exit(1);
        }

        String arquivo = args[0];
        String paste_destino = args[1];

        compactar(arquivo, paste_destino);
    }

    public static void compactar(String arquivo, String paste_destino) {
        try {
            FileOutputStream arq = new FileOutputStream(arquivo);
            ZipOutputStream zip = new ZipOutputStream(arq);
            File pasta = new File(paste_destino);
            compactarArquivos(pasta, pasta.getName(), zip);
            zip.close();
            arq.close();
            System.out.println("Compactação concluída!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void compactarArquivos(File arquivo, String nomeArquivo, ZipOutputStream zip) throws IOException {
        if (arquivo.isDirectory()) {
            File[] arquivos = arquivo.listFiles();
            if (arquivos != null) {
                for (File subArquivo : arquivos) {
                    compactarArquivos(subArquivo, nomeArquivo + "/" + subArquivo.getName(), zip);
                }
            }
        } else {
            FileInputStream fis = new FileInputStream(arquivo);
            ZipEntry zipEntry = new ZipEntry(nomeArquivo);
            zip.putNextEntry(zipEntry);

            byte[] bytes = new byte[1024];
            int length;
            while ((length = fis.read(bytes)) >= 0) {
                zip.write(bytes, 0, length);
            }

            fis.close();
        }
    }
}
