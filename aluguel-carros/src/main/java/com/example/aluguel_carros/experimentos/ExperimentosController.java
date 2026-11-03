package com.example.aluguel_carros.experimentos;

import java.util.Map;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExperimentosController {

    @GetMapping("/experimentos/saudacao")
    public Map<String, String> saudacao(@RequestParam("nome") String nome) {
        return Map.of("mensagem", "Ola, " + nome);
    }

    @GetMapping("/experimentos/dobro/{numero}")
    public Map<String, Integer> dobro(@PathVariable("numero") int numero) {
        return Map.of("entrada", numero, "dobro", numero * 2);
    }

    @PostMapping("/experimentos/eco")
    public Map<String, Object> eco(@Valid @RequestBody EcoRequest entrada) {
        return Map.of("texto", entrada.texto(), "tamanho", entrada.texto().length());
    }

    public record EcoRequest(@NotBlank String texto) {
    }

}
