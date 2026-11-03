package com.example.aluguel_carros.clientes;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

// Filtros de segurança DESLIGADOS de propósito: este teste verifica só a tradução HTTP.
// A autorização é verificada em SegurancaCadastroExperimentalTest (filtros ligados).
@WebMvcTest(ContratanteLaboratorioController.class)
@AutoConfigureMockMvc(addFilters = false)
class ContratanteLaboratorioControllerTest {

    private static final String JSON_ANA = "{\"id\":7,\"rg\":\"RG-A\",\"cpf\":\"00000000001\","
            + "\"nome\":\"Ana\",\"endereco\":\"Rua A, 1\",\"profissao\":\"Profissao A\"}";

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ArmazenamentoDeContratantes armazenamento;

    @Test // CT-22-C-01
    void cadastrarDevolve201ComIdentificadorELocation() throws Exception {
        when(armazenamento.cadastrar(any(Contratante.class))).thenReturn(7L);

        mockMvc.perform(post("/api/laboratorio/contratantes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"rg\":\"RG-A\",\"cpf\":\"00000000001\",\"nome\":\"Ana\","
                                + "\"endereco\":\"Rua A, 1\",\"profissao\":\"Profissao A\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/laboratorio/contratantes/7"))
                .andExpect(content().json(JSON_ANA));

        ArgumentCaptor<Contratante> captor = ArgumentCaptor.forClass(Contratante.class);
        verify(armazenamento).cadastrar(captor.capture());
        Contratante enviado = captor.getValue();
        assertAll(
                () -> assertEquals("RG-A", enviado.getRg()),
                () -> assertEquals("00000000001", enviado.getCpf()),
                () -> assertEquals("Ana", enviado.getNome()),
                () -> assertEquals("Rua A, 1", enviado.getEndereco()),
                () -> assertEquals("Profissao A", enviado.getProfissao()));
    }

    @Test // CT-22-C-02
    void consultarExistenteDevolve200() throws Exception {
        Contratante ana = new Contratante("RG-A", "00000000001", "Ana", "Rua A, 1", "Profissao A");
        when(armazenamento.buscarPorId(7L)).thenReturn(Optional.of(ana));

        mockMvc.perform(get("/api/laboratorio/contratantes/7"))
                .andExpect(status().isOk())
                .andExpect(content().json(JSON_ANA));
    }

    @Test // CT-22-C-03
    void consultarInexistenteDevolve404() throws Exception {
        when(armazenamento.buscarPorId(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/laboratorio/contratantes/999"))
                .andExpect(status().isNotFound());
    }
}
