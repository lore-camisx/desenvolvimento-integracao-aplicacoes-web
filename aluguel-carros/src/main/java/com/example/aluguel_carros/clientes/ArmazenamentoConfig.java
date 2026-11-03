package com.example.aluguel_carros.clientes;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** Fornece o armazenamento provisório em memória. Será trocado pela persistência real. */
@Configuration
public class ArmazenamentoConfig {

    @Bean
    ArmazenamentoDeContratantes armazenamentoDeContratantes() {
        return new ArmazenamentoDeContratantesEmMemoria();
    }
}
