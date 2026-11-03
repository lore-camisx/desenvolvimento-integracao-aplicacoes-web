package com.example.aluguel_carros.acesso;

import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.aluguel_carros.clientes.ArmazenamentoDeContratantes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

// Cadeia de segurança REAL (filtros ligados) com usuário de teste fictício.
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class SegurancaCadastroExperimentalTest {

    private static final String USUARIO = "usuario-teste";
    private static final String SENHA = "senha-teste";

    @Autowired
    private MockMvc mockMvc;

    // Simulado: se alguma requisição chegasse ao Controller, haveria interação com ele.
    @MockitoBean
    private ArmazenamentoDeContratantes armazenamento;

    @Test // CT-22-C-04
    void colecaoExperimentalFicaBloqueadaMesmoAutenticado() throws Exception {
        mockMvc.perform(get("/api/laboratorio/contratantes").with(httpBasic(USUARIO, SENHA)))
                .andExpect(status().isForbidden());
    }

    @Test // CT-22-C-04
    void itemExperimentalFicaBloqueadoMesmoAutenticado() throws Exception {
        mockMvc.perform(get("/api/laboratorio/contratantes/1").with(httpBasic(USUARIO, SENHA)))
                .andExpect(status().isForbidden());
    }

    @Test // CT-22-C-04
    void postExperimentalFicaBloqueadoENaoChegaAoArmazenamento() throws Exception {
        mockMvc.perform(post("/api/laboratorio/contratantes")
                        .with(httpBasic(USUARIO, SENHA))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\":\"Ana\"}"))
                .andExpect(status().isForbidden());

        verifyNoInteractions(armazenamento);
    }

    @Test // CT-22-C-05
    void colecaoDeClientesFicaBloqueadaMesmoAutenticada() throws Exception {
        mockMvc.perform(get("/api/clientes").with(httpBasic(USUARIO, SENHA)))
                .andExpect(status().isForbidden());
    }
}
