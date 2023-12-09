import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Q2 {
    public static void main(String[] args) throws IOException {

        if (args.length < 2) {
            System.out.println("Ops! Esta esquecendo um argumento.");
            return;
        }

        String arquivo = args[0];
        String arquivo2 = args[1];

        try {
            FileInputStream fis = new FileInputStream(arquivo);
            FileOutputStream fos = new FileOutputStream(arquivo2);
            
            byte[] buffer = new byte[8192]; // Bloco de 8192 bytes

            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }

            fis.close();
            fos.close();

            long x = System.currentTimeMillis();
            System.out.println(x / 1000 + "ms"); // Divide por 1000 para obter o tempo em segundos

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
