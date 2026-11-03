package com.example.aluguel_carros.clientes;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import org.junit.jupiter.api.Test;

// Sem @SpringBootTest: o armazenamento é instanciado com "new", sem Spring nem banco.
class ArmazenamentoDeContratantesEmMemoriaTest {

    private final ArmazenamentoDeContratantes armazenamento =
            new ArmazenamentoDeContratantesEmMemoria();

    private static Contratante ana() {
        return new Contratante("RG-A", "00000000001", "Ana", "Rua A, 1", "Profissao A");
    }

    private static Contratante beto() {
        return new Contratante("RG-B", "00000000002", "Beto", "Rua B, 2", "Profissao B");
    }

    @Test // CT-21-B-01
    void objetoArmazenadoPodeSerRecuperado() {
        long id = armazenamento.cadastrar(ana());

        Optional<Contratante> achado = armazenamento.buscarPorId(id);

        assertTrue(achado.isPresent());
        Contratante c = achado.get();
        assertAll(
                () -> assertEquals("RG-A", c.getRg()),
                () -> assertEquals("00000000001", c.getCpf()),
                () -> assertEquals("Ana", c.getNome()),
                () -> assertEquals("Rua A, 1", c.getEndereco()),
                () -> assertEquals("Profissao A", c.getProfissao()));
    }

    @Test // CT-21-B-02
    void doisRegistrosDiferentesNaoSaoConfundidos() {
        long idAna = armazenamento.cadastrar(ana());
        long idBeto = armazenamento.cadastrar(beto());

        assertNotEquals(idAna, idBeto);
        assertEquals("Ana", armazenamento.buscarPorId(idAna).orElseThrow().getNome());
        assertEquals("Beto", armazenamento.buscarPorId(idBeto).orElseThrow().getNome());
    }

    @Test // CT-21-B-03
    void buscaPorIdInexistenteDevolveVazio() {
        armazenamento.cadastrar(ana());

        assertTrue(armazenamento.buscarPorId(999L).isEmpty());
    }

    @Test // CT-21-B-04
    void funcionaSemSpringNemBanco() {
        assertDoesNotThrow(() -> {
            long id = armazenamento.cadastrar(ana());
            armazenamento.buscarPorId(id).orElseThrow();
        });
    }

    @Test // CT-21-C-01
    void atualizarCadastroExistenteMantemOMesmoId() {
        long id = armazenamento.cadastrar(ana());

        boolean atualizado = armazenamento.atualizar(id, beto());

        assertTrue(atualizado);
        Contratante c = armazenamento.buscarPorId(id).orElseThrow();
        assertAll(
                () -> assertEquals("RG-B", c.getRg()),
                () -> assertEquals("00000000002", c.getCpf()),
                () -> assertEquals("Beto", c.getNome()),
                () -> assertEquals("Rua B, 2", c.getEndereco()),
                () -> assertEquals("Profissao B", c.getProfissao()));
    }

    @Test // CT-21-C-02
    void atualizarIdInexistenteNaoCriaRegistro() {
        long idAna = armazenamento.cadastrar(ana());

        boolean atualizado = armazenamento.atualizar(999L, beto());

        assertFalse(atualizado);
        assertTrue(armazenamento.buscarPorId(999L).isEmpty());
        assertEquals("Ana", armazenamento.buscarPorId(idAna).orElseThrow().getNome());
    }

    @Test // CT-21-C-03
    void excluirCadastroExistenteRemoveSomenteEle() {
        long idAna = armazenamento.cadastrar(ana());
        long idBeto = armazenamento.cadastrar(beto());

        boolean excluido = armazenamento.excluir(idAna);

        assertTrue(excluido);
        assertTrue(armazenamento.buscarPorId(idAna).isEmpty());
        assertEquals("Beto", armazenamento.buscarPorId(idBeto).orElseThrow().getNome());
    }

    @Test // CT-21-C-04
    void excluirIdInexistenteNaoApagaOutroCadastro() {
        long idAna = armazenamento.cadastrar(ana());
        long idBeto = armazenamento.cadastrar(beto());

        boolean excluido = armazenamento.excluir(999L);

        assertFalse(excluido);
        assertEquals("Ana", armazenamento.buscarPorId(idAna).orElseThrow().getNome());
        assertEquals("Beto", armazenamento.buscarPorId(idBeto).orElseThrow().getNome());
    }
}
