import java.io.Serializable;

public class Animal implements Serializable {

    private String tipo;
    private char genero;

    public Animal () {}

    public Animal(String tipo, char genero) {
        this.tipo = tipo;
        this.genero = genero;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public char getGenero() {
        return genero;
    }

    public void setGenero(char genero) {
        this.genero = genero;
    }

    @Override
    public String toString() {
        return "Animal [tipo=" + this.tipo + ", genero=" + this.genero + "]";
    }

}
