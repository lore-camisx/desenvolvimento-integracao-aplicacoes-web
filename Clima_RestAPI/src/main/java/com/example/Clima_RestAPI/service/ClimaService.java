package com.example.Clima_RestAPI.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ClimaService {

    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5";
    private static final String BASE_URL_BH = "https://api.openweathermap.org/data/2.5/weather?lat=-19.9245&lon=-43.9352&appid=";

    @Value("${openweather.api.key}")
    private String apikey;

    private String consultarURL(String apiURL) {
        String dados = "";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(apiURL, String.class);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            dados = responseEntity.getBody();
        } else {
            dados = "Erro ao consultar os dados meteorológicos. Código de Status: " + responseEntity.getStatusCode();
        }
        return dados;
    }

    public String mostrarInformacoes() {
        return consultarURL(BASE_URL_BH + apikey);
    }

    public String mostrarInformacoes(double lat, double lon){
        return consultarURL(BASE_URL + "/weather?lat="+lat+"&lon="+lon+"&appid="+apikey);
    }
}