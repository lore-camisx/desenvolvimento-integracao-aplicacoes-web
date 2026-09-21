package com.example.Fipe_RestAPI.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class Service {

    private static final String BASE_URL = "https://fipe.parallelum.com.br/api/v2";

    private String consultarURL(String apiUrl) {
        String dados = "";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(apiUrl, String.class);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            dados = responseEntity.getBody();
        } else {
            dados = ("Erro ao consultar dados. Código de Status: " + responseEntity.getStatusCode());
        }
        return dados;
    }

    public String consultarMarcas(){
        return consultarURL(BASE_URL + "/cars/brands");
    }

    public String consultarModelos(int id){
        return consultarURL(BASE_URL + "/cars/brands/"+id+"/models");
    }

    public String consultarAnos(int marca, int modelo){
        return consultarURL(BASE_URL + "/cars/brands/"+marca+"/models/"+modelo+"/years");
    }

    public String consultarPrecos(int marca, int modelo, String ano){
        return consultarURL(BASE_URL + "/cars/brands/"+marca+"/models/"+modelo+"/years/"+ano);
    }
}
