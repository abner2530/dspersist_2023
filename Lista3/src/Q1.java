import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Q1 {

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Falta: Arquivo.csv ou Delimitador ou Coluna1");
            return;
        }

        String arquivo = args[0];
        String delimitador = args[1];
        String[] argColunas = new String[args.length - 2];
        for (int i = 0; i < argColunas.length; i++) {
            argColunas[i] = args[i + 2];
        }

        try (Scanner scanner = new Scanner(new File(arquivo))) {

            String proxLinha;
            String[] cabecalho = scanner.nextLine().split(delimitador);
            int[] colunaIndices = new int[argColunas.length];
            double[] colunaSoma = new double[argColunas.length];
            int[] colunaCount = new int[argColunas.length];

            // Atribuindo indices as relativas colunas
            for (int i = 0; i < argColunas.length; i++) {
                for (int j = 0; j < cabecalho.length; j++) {
                    if (cabecalho[j].equals(argColunas[i])) {
                        colunaIndices[i] = j;
                        break;
                    }
                }
            }

            while (scanner.hasNextLine()) {
                proxLinha = scanner.nextLine();
                String[] values = proxLinha.split(delimitador);

                for (int i = 0; i < colunaIndices.length; i++) {
                    int columnIndex = colunaIndices[i];

                    if (columnIndex >= 0 && columnIndex < values.length) {
                        try {
                            double value = Double.parseDouble(values[columnIndex]);
                            colunaSoma[i] += value;
                            colunaCount[i]++;
                        } catch (NumberFormatException e) {

                        }
                    }
                }
            }

            for (int i = 0; i < argColunas.length; i++) {
                if (colunaCount[i] > 0) {

                    double media = colunaSoma[i] / colunaCount[i];
                    
                    System.out.println("Coluna: " + argColunas[i]);
                    System.out.println("Soma: " + colunaSoma[i]);
                    System.out.println("Media: " + media);
                } else {
                    System.out.println("Coluna: " + argColunas[i]);
                    System.out.println("Não contém dados numéricos.");
                }
                System.out.println();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
