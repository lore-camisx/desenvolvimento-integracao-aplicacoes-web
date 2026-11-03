package com.example.aluguel_carros;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.sql.DataSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class IsolamentoDeTestesTest {

    @Autowired
    private ObjectProvider<DataSource> dataSources;

    @Autowired
    private Environment env;

    @Test
    void perfilDeTesteNaoCriaDataSource() {
        assertEquals(0, dataSources.stream().count());
    }

    @Test
    void esquemaNaoEAlteradoAutomaticamente() {
        assertAll(
                () -> assertEquals("none", env.getProperty("spring.jpa.hibernate.ddl-auto")),
                () -> assertEquals("never", env.getProperty("spring.sql.init.mode")));
    }
}
