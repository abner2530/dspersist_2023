import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "Abrigo")
public class Animais {
    
    private List<Animal> animais;

    public Animais() {}

    public Animais(List<Animal> animais) {
        this.animais = animais;
    }

    @JacksonXmlElementWrapper(localName = "animais")
    @JacksonXmlProperty(localName = "animal")

    public List<Animal> getAnimais() {
        return animais;
    }

    public void setAnimais(List<Animal> animais) {
        this.animais = animais;
    }

    @Override
    public String toString() {
        return "Animais [animais=" + animais + "]";
    }

}
