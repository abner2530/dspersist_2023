import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class Q3 {
    public static void main(String[] args) throws IOException {

        if (args.length < 2) {
            System.out.println("Ops! Esta esquecendo um argumento.");
            return;
        }

        String arquivo = args[0];
        String arquivo2 = args[1];

        try {
            InputStream is = new FileInputStream(arquivo);
            InputStreamReader isr = new InputStreamReader(is, "ISO-8859-1");
            BufferedReader br = new BufferedReader(isr);
            String s = br.readLine();

            OutputStream os = new FileOutputStream(arquivo2);
            OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
            BufferedWriter bw = new BufferedWriter(osw);

            while (s != null) {
                bw.write(s);
                bw.newLine();
                s = br.readLine();
            }

            bw.close();
            isr.close();

            long x = System.currentTimeMillis();
            System.out.println(x / 1000 + "ms"); // Transformando em segundos

        } catch (Exception e) {
            System.err.println(e);
        }
    }
}