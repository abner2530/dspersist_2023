import java.io.FileInputStream;
import java.security.MessageDigest;

public class Q4 {

    private static String gerar_Hash(String arquivo, String tipo) throws Exception {
        MessageDigest md = MessageDigest.getInstance(tipo);
        FileInputStream fis = new FileInputStream(arquivo);
        byte[] Bytes = new byte[1024];

        int ler_Bytes;
        while ((ler_Bytes = fis.read(Bytes)) != -1) {
            md.update(Bytes, 0, ler_Bytes);
        }

        byte[] mdBytes = md.digest();
        StringBuilder hexString = new StringBuilder();
        for (byte mdByte : mdBytes) {
            hexString.append(String.format("%02x", mdByte));
        }

        fis.close();
        return hexString.toString();
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Faltou caminho_arquivo");
            System.exit(1);
        }

        String arquivo = args[0];
        try {
            long startTime, endTime;

            // Calcula o hash MD5
            startTime = System.currentTimeMillis();
            gerar_Hash(arquivo, "MD5");
            endTime = System.currentTimeMillis();
            System.out.println("MD5 Hash: Tempo de exec (MD5): " + (endTime - startTime) + " ms");

            // Calcula o hash SHA-1
            startTime = System.currentTimeMillis();
            gerar_Hash(arquivo, "SHA-1");
            endTime = System.currentTimeMillis();
            System.out.println("SHA-1 Hash: Tempo de exec (SHA-1): " + (endTime - startTime) + " ms");

            // Calcula o hash SHA-256
            startTime = System.currentTimeMillis();
            gerar_Hash(arquivo, "SHA-256");
            endTime = System.currentTimeMillis();
            System.out.println("SHA-256 Hash: Tempo de exec (SHA-256): " + (endTime - startTime) + " ms");

        } catch (Exception e) {
            System.err.println("Erro ao calcular o hash: " + e.getMessage());
        }
    }
}
