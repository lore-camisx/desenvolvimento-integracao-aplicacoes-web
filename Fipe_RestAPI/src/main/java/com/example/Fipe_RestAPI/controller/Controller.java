package com.example.Fipe_RestAPI.controller;

import com.example.Fipe_RestAPI.service.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    Service service = new Service();

    @GetMapping("/marcas")
    public String consultarMarcas(){
        return service.consultarMarcas();
    }

    @GetMapping("/modelos/{marca}")
    public String consultarModelos(@PathVariable int marca){
        return service.consultarModelos(marca);
    }

    @GetMapping ("/anos/{marca}/{modelo}")
    public String consultarAnos(@PathVariable int marca,@PathVariable int modelo){
        return service.consultarAnos(marca,modelo);
    }

    @GetMapping("/precos/{marca}/{modelo}/{ano}")
    public String consultarPrecos(@PathVariable int marca, @PathVariable int modelo,@PathVariable String ano){
        return service.consultarPrecos(marca, modelo, ano);
    }
}
