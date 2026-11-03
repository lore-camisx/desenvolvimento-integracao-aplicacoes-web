package com.example.aluguel_carros.diagnostico;

import org.springframework.test.context.ActiveProfiles;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class PingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void pingReturnsPongAsJson() throws Exception {
        mockMvc.perform(get("/ping"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"mensagem\":\"pong\"}"));
    }

    @Test
    void pingHasJsonContentType() throws Exception {
        mockMvc.perform(get("/ping"))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void unknownRouteReturnsNotFound() throws Exception {
        mockMvc.perform(get("/naoexiste"))
                .andExpect(status().isNotFound());
    }

}