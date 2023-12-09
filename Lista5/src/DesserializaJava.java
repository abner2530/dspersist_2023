import java.io.*;
import java.util.List;

public class DesserializaJava {
    
    public static void main(String[] args) {
        String arquivoSerializado = "animais.ser";
        
        try (FileInputStream arquivoInput = new FileInputStream(arquivoSerializado);
             ObjectInputStream objetoInput = new ObjectInputStream(arquivoInput)) {
            
            // Desserializar a lista de objetos
            List<Animal> lista = (List<Animal>) objetoInput.readObject();
            
            // Exibir os objetos desserializados
            for (Animal animal : lista) {
                System.out.println("Nome: " + animal.getTipo());
                System.out.println("Sexo: " + animal.getGenero());
                System.out.println();
            }
            
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
