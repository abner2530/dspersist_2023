import java.io.File;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class DesserializaJSON {

    public static void main(String[] args) throws Exception {
        File arquivoJSON = new File("animais.json");

        ObjectMapper objectMapper = new ObjectMapper();

        // Desserializar a lista de objetos a partir do arquivo JSON
        List<Animal> lista = objectMapper.readValue(arquivoJSON, objectMapper.getTypeFactory().constructCollectionType(List.class, Animal.class));

        // Exibir os objetos desserializados
        for (Animal animal : lista) {
            System.out.println("Nome: " + animal.getTipo());
            System.out.println("Sexo: " + animal.getGenero());
            System.out.println();
        }
    }
}
