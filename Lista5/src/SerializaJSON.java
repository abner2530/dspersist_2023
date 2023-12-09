import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class SerializaJSON {

    public static void main(String[] args) throws Exception {
        Animal a1 = new Animal("gato", 'M');
        Animal a2 = new Animal("cachorro", 'F');
        List<Animal> lista = new ArrayList<>();
        lista.add(a1);
        lista.add(a2);

        // Serializar os objetos para um arquivo JSON
        File f = new File("animais.json");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.writeValue(f, lista);

        System.out.println("Objetos serializados com sucesso para animais.json.");
    }
}
