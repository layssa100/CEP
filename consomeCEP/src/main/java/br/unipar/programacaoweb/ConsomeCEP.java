package br.unipar.programacaoweb;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;

public class ConsomeCEP {

    public static void main(String[] args) {
        String cep = JOptionPane.showInputDialog("Digite o CEP: ");

        if (cep == null || !cep.matches("\\d{8}")) {
            System.out.println("CEP inválido. Certifique-se de que contém 8 dígitos numéricos.");
            return;
        }

        EnderecoDAO dao = new EnderecoDAO();
        Endereco endereco = dao.buscarPorCep(cep);

        if (endereco != null) {
            System.out.println("CEP encontrado no banco de dados:");
        } else {
            endereco = consultarViaCEP(cep);
            if (endereco != null) {
                dao.salvar(endereco);
                System.out.println("CEP consultado via API e salvo no banco de dados.");
            } else {
                System.out.println("Não foi possível consultar o CEP.");
            }
        }

        if (endereco != null) {
            exibirEndereco(endereco);
        }
    }

    private static Endereco consultarViaCEP(String cep) {
        try {
            String url = "https://viacep.com.br/ws/" + cep + "/json/";
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(new URL(url));

            if (jsonNode.has("erro")) {
                System.out.println("CEP não encontrado na API ViaCEP.");
                return null;
            }

            Endereco endereco = new Endereco();
            endereco.setCep(jsonNode.get("cep").asText());
            endereco.setLogradouro(jsonNode.get("logradouro").asText());
            endereco.setBairro(jsonNode.get("bairro").asText());
            endereco.setLocalidade(jsonNode.get("localidade").asText());
            endereco.setUf(jsonNode.get("uf").asText());
            return endereco;

        } catch (IOException e) {
            System.err.println("Erro ao consultar ViaCEP: " + e.getMessage());
            return null;
        }
    }

    private static void exibirEndereco(Endereco endereco) {
        System.out.println("CEP: " + endereco.getCep());
        System.out.println("Logradouro: " + endereco.getLogradouro());
        System.out.println("Bairro: " + endereco.getBairro());
        System.out.println("Cidade: " + endereco.getLocalidade());
        System.out.println("Estado: " + endereco.getUf());
    }
}