import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Q4 {
    public static void main(String[] args) throws IOException {

        String arquivo = "";

        try (Scanner s = new Scanner(System.in)) {
            String linha;
            StringBuilder texto = new StringBuilder();

            while (!(linha = s.nextLine()).equals("FIM")) {
                texto.append(linha).append("\n");
            }

            System.out.print("Digite o nome do arquivo: ");
            arquivo = s.nextLine();

            try (PrintWriter pw = new PrintWriter(new FileWriter(arquivo))) {
                pw.print(texto.toString());
            }
        }
    }
}
