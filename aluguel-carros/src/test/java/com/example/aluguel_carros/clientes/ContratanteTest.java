package com.example.aluguel_carros.clientes;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ContratanteTest {

    @Test
    void construirGuardaOsCincoDadosNosCamposCertos() {
        Contratante c = new Contratante(
                "MG-12.345.678",
                "00123456789",
                "Maria Ficticia da Silva",
                "Rua Exemplo, 100 - Belo Horizonte/MG",
                "Analista de testes");

        assertAll(
                () -> assertEquals("MG-12.345.678", c.getRg()),
                () -> assertEquals("00123456789", c.getCpf()),
                () -> assertEquals("Maria Ficticia da Silva", c.getNome()),
                () -> assertEquals("Rua Exemplo, 100 - Belo Horizonte/MG", c.getEndereco()),
                () -> assertEquals("Analista de testes", c.getProfissao()));
    }

    @Test
    void instanciasDiferentesNaoCompartilhamDados() {
        Contratante a = new Contratante("RG-A", "00000000001", "Ana", "Rua A", "Prof A");
        Contratante b = new Contratante("RG-B", "00000000002", "Beto", "Rua B", "Prof B");

        assertAll(
                () -> assertEquals("Ana", a.getNome()),
                () -> assertEquals("00000000001", a.getCpf()),
                () -> assertEquals("Beto", b.getNome()),
                () -> assertEquals("00000000002", b.getCpf()));
    }
}
