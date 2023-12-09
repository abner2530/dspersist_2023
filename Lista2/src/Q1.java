import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

public class Q1 {
    public static void main(String[] args) throws IOException {

        if (args.length < 2) {
            System.out.println("Ops! Esta esquecendo um argumento.");
            return;
        }

        String arquivo = args[0];
        String arquivo2 = args[1];

        try {
            InputStream is = new FileInputStream(arquivo);
            InputStreamReader isr = new InputStreamReader(is, "UTF-8");
            int i = isr.read();

            PrintStream ps = new PrintStream(arquivo2);

            while (i != -1) {
                ps.println((char) i);
                i = isr.read();
            }
            
            ps.close();
            isr.close();

            long x = System.currentTimeMillis();
            System.out.println(x/1000 + "ms"); // Transformando em segundos

        } catch (Exception e) {
            System.err.println(e);
        }
    }
}