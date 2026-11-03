package com.example.aluguel_carros.acesso;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

// Executa a cadeia de segurança real (sem @WithMockUser e sem desligar filtros).
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class SegurancaTest {

    private static final String USUARIO = "usuario-teste";
    private static final String SENHA = "senha-teste";

    @Autowired
    private MockMvc mockMvc;

    @Test
    void semAutenticacaoRecebe401ComDesafioBasic() throws Exception {
        mockMvc.perform(get("/ping"))
                .andExpect(status().isUnauthorized())
                .andExpect(header().exists(HttpHeaders.WWW_AUTHENTICATE));
    }

    @Test
    void credenciaisInvalidasRecebem401() throws Exception {
        mockMvc.perform(get("/ping").with(httpBasic(USUARIO, "senha-errada")))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void credenciaisValidasAcessamPing() throws Exception {
        mockMvc.perform(get("/ping").with(httpBasic(USUARIO, SENHA)))
                .andExpect(status().isOk());
    }

    @Test
    void cadastroDeClientesFicaIndisponivelMesmoAutenticado() throws Exception {
        mockMvc.perform(get("/api/clientes/1").with(httpBasic(USUARIO, SENHA)))
                .andExpect(status().isForbidden());
    }

    @Test
    void caminhoInexistenteSemAutenticacaoNaoRevelaExistencia() throws Exception {
        mockMvc.perform(get("/naoexiste"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void postSemCsrfEBloqueadoAntesDoController() throws Exception {
        mockMvc.perform(post("/experimentos/eco")
                        .with(httpBasic(USUARIO, SENHA))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"texto\":\"ola\"}"))
                .andExpect(status().isForbidden());
    }

    @Test
    void postComCsrfChegaAoController() throws Exception {
        mockMvc.perform(post("/experimentos/eco")
                        .with(httpBasic(USUARIO, SENHA))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"texto\":\"ola\"}"))
                .andExpect(status().isOk());
    }
}
