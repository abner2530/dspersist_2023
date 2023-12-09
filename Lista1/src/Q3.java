import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Q3 {

    static void LerArquivo(String arq) throws IOException {
        InputStream is = new FileInputStream(arq);
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String s = br.readLine(); // primeira linha

        while (s != null) {
            System.out.println(s);
            s = br.readLine();
        }
        br.close();
    }
    public static void main(String[] args) throws IOException {

        String arquivo = args[0];
        String arquivo2 = args[1];

        LerArquivo(arquivo);
        LerArquivo(arquivo2);

    }
}
