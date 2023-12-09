import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class SerializaXML {
    
    public static void main(String[] args) throws Exception {
        Animal a1 = new Animal("gato", 'M');
        Animal a2 = new Animal("cachorro", 'F');
        List<Animal> lista = new ArrayList<Animal>();
        lista.add(a1);
        lista.add(a2);
        Animais animais = new Animais(lista);
        File f = new File("animais.xml");

        XmlMapper xm = new XmlMapper();
        xm.enable(SerializationFeature.INDENT_OUTPUT);
        xm.writeValue(f, animais);
    }
}
