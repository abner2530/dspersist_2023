/*
### Trabalho de Persistencia ###

Alunos: 
    Abner Enoque Monteiro Silva¹ - 511325
    Danilo dos Santos Gomes²     - 536894
____________________________________________

Defina uma entidade com pelo menos 5 atributos e relacionada ao domínio escolhido pela dupla para o trabalho prático da disciplina. 
Crie uma classe Java para representar a entidade escolhida. 
Exemplo de entidade com 9 atributos: Cliente: id, nome, cpf, endereço, email, fone, cidade, uf, cep. 
A entidade escolhida por você não pode ser a entidade Cliente dada como exemplo. 
Escolha uma entidade bem diferente dela, inclusive quanto aos seus atributos.
*/

package abrigo.system;

import java.util.UUID;

public class Animal {
    UUID id;
    String tipo;
    String nome;
    String sexo;
    String raca;
    int idade;
    double peso;

    public Animal(UUID id, String tipo, String nome, String sexo2, String raça, int idade, double peso) {
        this.id = id;
        this.tipo = tipo;
        this.nome = nome;
        this.sexo = sexo2;
        this.raca = raça;
        this.idade = idade;
        this.peso = peso;
    }

    public UUID getId() {
        return id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public String toString() {
        return "{id: " + this.id + "\ntipo: " + this.tipo + "\nnome: " + this.nome + "\nsexo: " + this.sexo + "\nraça: "
                + this.raca + "\nidade: " + this.idade + "\npeso: " + this.peso + "}\n";
    }
}