package com.example.aluguel_carros.acesso;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Política provisória de acesso. Não é regra de negócio aprovada.
 * - /api/clientes/**: negado a todos até as permissões serem definidas.
 * - demais caminhos: exigem autenticação.
 * httpBasic é só um mecanismo provisório; o login real ainda não existe.
 * A proteção CSRF permanece no padrão (ativa).
 */
@Configuration
public class SegurancaConfig {

    @Bean
    SecurityFilterChain filtroDeSeguranca(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(autorizacao -> autorizacao
                        .requestMatchers("/api/clientes/**").denyAll()
                        .anyRequest().authenticated())
                .httpBasic(withDefaults());
        return http.build();
    }
}
