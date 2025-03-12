package br.unipar.programacaoweb;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity

public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;


    String cep;
    String logradouro;
    String bairro;
    String localidade;
    String uf;
    String ibge;
    String complemento;{
    }
}
