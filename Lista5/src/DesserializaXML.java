import java.io.File;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class DesserializaXML {
    public static void main(String[] args) throws Exception {
        File file = new File( "animais.xml");
        XmlMapper xm = new XmlMapper();
        Animais a = xm.readValue(file, Animais.class);
        System.out.println(a);
    }
}
