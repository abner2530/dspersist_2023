/*
### Trabalho de Persistencia ###

Alunos: 
    Abner Enoque Monteiro Silva¹ - 511325
    Danilo dos Santos Gomes²     - 536894
____________________________________________

Converter os dados do arquivo CSV para gerar um arquivo JSON e 
um arquivo XML usando a biblioteca Jackson.
*/

package abrigo.system;

import java.io.*;
import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class F3_Converter {

    private static List<Animal> lerCSV() {

        List<Animal> animais = new ArrayList<>();
        String filecsv = "animais.csv";
        try {
            FileReader fr = new FileReader(filecsv);
            BufferedReader br = new BufferedReader(fr);

            String linha;
            boolean primeiraLinha =  true;

            while ((linha = br.readLine()) != null) {

                if(primeiraLinha){
                    primeiraLinha = false;
                    continue;
                }

                String[] partes = linha.split(";");
                if (partes.length == 7) {
                    String idT = partes[0];
                    String tipo = partes[1];
                    String nome = partes[2];
                    String sexo = partes[3];
                    String raca = partes[4];
                    int idade = Integer.parseInt(partes[5]);
                    double peso = Double.parseDouble(partes[6]);
                    
                    UUID id = UUID.fromString(idT);

                    Animal animal = new Animal(id, tipo, nome, sexo, raca, idade, peso);

                    animais.add(animal);
                }
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Error: Erro ao ler o arquivo CSV.");
        }

        return animais;
    }

    public static void f3_Converter() {
        String filejson = "animais.json";
        String filexml = "animais.xml";
        try {
            // Ler os dados do arquivo CSV e converter para objetos Animal
            List<Animal> animais = lerCSV();

            // Converter para JSON
            ObjectMapper om = new ObjectMapper();
            om.enable(SerializationFeature.INDENT_OUTPUT);//Identação do JSON
            String json = om.writeValueAsString(animais);

            // Salvar o JSON em um arquivo
            FileWriter jsonfw = new FileWriter(filejson);
            jsonfw.write(json);
            jsonfw.close();

            // Converter para XML
            XmlMapper xmlMapper = new XmlMapper();
            xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
            String xml = xmlMapper.writeValueAsString(animais);

            // Salvar o XML em um arquivo
            FileWriter xmlfw = new FileWriter(filexml);
            xmlfw.write(xml);
            xmlfw.close();
            
            System.out.println("Dados convertidos e salvos em JSON/XML.");
            System.out.println("Vamos continuar no programa!");

        } catch (IOException e) {
            System.out.println("Erro ao converter e salvar os dados.");
        }
    }

}
