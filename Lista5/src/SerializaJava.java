import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SerializaJava {
    
    public static void main(String[] args) {
        
        Animal a1 = new Animal("gato", 'M');
        Animal a2 = new Animal("cachorro", 'F');
        
        List<Animal> lista = new ArrayList<>();
        lista.add(a1);
        lista.add(a2);
        
        // Serializar os objetos para um arquivo em disco/SSD
        String arquivoSerializado = "animais.ser";
        
        try (FileOutputStream arquivoOutput = new FileOutputStream(arquivoSerializado);
             ObjectOutputStream objetoOutput = new ObjectOutputStream(arquivoOutput)) {
            
            // Escrever a lista serializada no arquivo
            objetoOutput.writeObject(lista);
            
            System.out.println("Objetos serializados com sucesso para " + arquivoSerializado);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
