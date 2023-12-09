import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Q1 {
    public static void main(String[] args) throws IOException {

        String arquivo = args[0];

        InputStream is = new FileInputStream(arquivo);
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String s = br.readLine(); // primeira linha
        int cout = 0;

        while (s != null) {
            if (cout < 10) {
                System.out.println(s);
                s = br.readLine();
                cout++;
            } else
                break;
        }
        br.close();
    }
}