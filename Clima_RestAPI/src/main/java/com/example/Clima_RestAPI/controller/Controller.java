package com.example.Clima_RestAPI.controller;

import com.example.Clima_RestAPI.service.ClimaService;
import jakarta.servlet.ServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    @Autowired
    private ClimaService climaService;
    
    @GetMapping("/climaBH")
    public String mostrarInformacoesBH(ServletRequest servletRequest){
        return climaService.mostrarInformacoes();
    }
    
    @GetMapping("/weather")
    public String mostrarInformacoes(@RequestParam double lat, @RequestParam double lon) {
        return climaService.mostrarInformacoes(lat, lon);
    }
}