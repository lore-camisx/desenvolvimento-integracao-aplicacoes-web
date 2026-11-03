package com.example.aluguel_carros.experimentos;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser
@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class ExperimentosControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void saudacaoUsaONomeRecebido() throws Exception {
        mockMvc.perform(get("/experimentos/saudacao").param("nome", "Ana"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"mensagem\":\"Ola, Ana\"}"));
        mockMvc.perform(get("/experimentos/saudacao").param("nome", "Bia"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"mensagem\":\"Ola, Bia\"}"));
    }

    @Test
    void saudacaoSemNomeRetorna400() throws Exception {
        mockMvc.perform(get("/experimentos/saudacao"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void dobroUsaONumeroDoCaminho() throws Exception {
        mockMvc.perform(get("/experimentos/dobro/{numero}", 21))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"entrada\":21,\"dobro\":42}"));
        mockMvc.perform(get("/experimentos/dobro/{numero}", 50))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"entrada\":50,\"dobro\":100}"));
    }

    @Test
    void dobroComTextoRetorna400() throws Exception {
        mockMvc.perform(get("/experimentos/dobro/abc"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void dobroSemSegmentoRetorna404() throws Exception {
        mockMvc.perform(get("/experimentos/dobro"))
                .andExpect(status().isNotFound());
    }

    @Test
    void ecoUsaOCorpoRecebido() throws Exception {
        mockMvc.perform(post("/experimentos/eco").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"texto\":\"teste\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"texto\":\"teste\",\"tamanho\":5}"));
        mockMvc.perform(post("/experimentos/eco").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"texto\":\"spring\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"texto\":\"spring\",\"tamanho\":6}"));
    }

    @Test
    void ecoComJsonMalformadoRetorna400() throws Exception {
        mockMvc.perform(post("/experimentos/eco").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"texto\":"))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertInstanceOf(
                        HttpMessageNotReadableException.class, result.getResolvedException()));
    }

    @Test
    void ecoSemCampoTextoViolaValidacao() throws Exception {
        postarEco("{}")
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertInstanceOf(
                        MethodArgumentNotValidException.class, result.getResolvedException()));
    }

    @Test
    void ecoComTextoNuloViolaValidacao() throws Exception {
        postarEco("{\"texto\":null}")
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertInstanceOf(
                        MethodArgumentNotValidException.class, result.getResolvedException()));
    }

    @Test
    void ecoComTextoVazioViolaValidacao() throws Exception {
        postarEco("{\"texto\":\"\"}")
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertInstanceOf(
                        MethodArgumentNotValidException.class, result.getResolvedException()));
    }

    @Test
    void ecoComApenasEspacosViolaValidacao() throws Exception {
        postarEco("{\"texto\":\"   \"}")
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertInstanceOf(
                        MethodArgumentNotValidException.class, result.getResolvedException()));
    }

    private ResultActions postarEco(String corpo) throws Exception {
        return mockMvc.perform(post("/experimentos/eco").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(corpo));
    }

}
