import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Q3 {
    public static void main(String[] args) {

        Properties prop = new Properties();

        try {
            // Carregar as propriedades do arquivo config.properties
            prop.load(new FileReader("config.properties"));

            // Obter os valores das propriedades
            String nomeArquivo = prop.getProperty("arquivo");
            int linhaInicial = Integer.parseInt(prop.getProperty("linha_inicial"));
            int linhaFinal = Integer.parseInt(prop.getProperty("linha_final"));

            // Ler e exibir as linhas do arquivo
            try (BufferedReader br = new BufferedReader(new FileReader(nomeArquivo))) {
                String linha;
                int contLinha = 1;
                
                while ((linha = br.readLine()) != null) {
                    if (contLinha >= linhaInicial && contLinha <= linhaFinal) {
                        System.out.println("L" + contLinha + ":" + " " + linha);
                    }
                    contLinha++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
