package com.example.aluguel_carros.persistencia.experimental;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.util.Arrays;
import javax.sql.DataSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.core.env.Environment;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

// Teste de INTEGRAÇÃO (sufixo IT, executado pelo Failsafe com "./mvnw verify").
// O create-drop só vale aqui, e a conexão vem do container (@ServiceConnection),
// nunca do Supabase. O perfil "test" (que exclui o DataSource) NÃO é usado.
@DataJpaTest(properties = "spring.jpa.hibernate.ddl-auto=create-drop")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
class ContratanteExperimentalPersistenciaIT {

    @Container
    @ServiceConnection
    static PostgreSQLContainer postgres =
            new PostgreSQLContainer(DockerImageName.parse("postgres:17-alpine"));

    @Autowired
    private ContratanteExperimentalRepository repositorio;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private Environment ambiente;

    private static ContratanteExperimentalEntity ana() {
        return new ContratanteExperimentalEntity(
                "RG-A", "00000000001", "Ana", "Rua A, 1", "Profissao A");
    }

    @Test // CT-23-01
    void persistirAtribuiChaveGeradaEExecutaFlush() {
        ContratanteExperimentalEntity novo = ana();
        assertNull(novo.getId());

        ContratanteExperimentalEntity salvo = repositorio.saveAndFlush(novo);

        assertNotNull(salvo.getId());
    }

    @Test // CT-23-02
    void recuperarAposClearTrazOsCincoCampos() {
        ContratanteExperimentalEntity original = repositorio.saveAndFlush(ana());
        Long id = original.getId();

        entityManager.clear();
        ContratanteExperimentalEntity lido = repositorio.findById(id).orElseThrow();

        assertNotSame(original, lido); // veio do banco, não do cache em memória
        assertAll(
                () -> assertEquals("RG-A", lido.getRg()),
                () -> assertEquals("00000000001", lido.getCpf()),
                () -> assertEquals("Ana", lido.getNome()),
                () -> assertEquals("Rua A, 1", lido.getEndereco()),
                () -> assertEquals("Profissao A", lido.getProfissao()));
    }

    @Test // CT-23-05
    void conexaoEfetivaPertenceAoContainerPostgres() throws Exception {
        try (Connection conexao = dataSource.getConnection()) {
            DatabaseMetaData metadados = conexao.getMetaData();
            String url = metadados.getURL();
            String hostEPorta = postgres.getHost() + ":" + postgres.getFirstMappedPort();

            assertAll(
                    () -> assertEquals("PostgreSQL", metadados.getDatabaseProductName()),
                    () -> assertTrue(url.contains(hostEPorta)),
                    () -> assertFalse(url.contains("supabase")),
                    () -> assertFalse(
                            Arrays.asList(ambiente.getActiveProfiles()).contains("supabase")));
        }
    }
}
