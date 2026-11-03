package com.example.aluguel_carros.clientes;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Implementação provisória em memória: os dados somem quando a aplicação encerra.
 * O identificador é técnico e gerado aqui; não presume CPF como chave nem unicidade.
 */
public class ArmazenamentoDeContratantesEmMemoria implements ArmazenamentoDeContratantes {

    private final Map<Long, Contratante> registros = new ConcurrentHashMap<>();
    private final AtomicLong proximoId = new AtomicLong(1);

    @Override
    public long cadastrar(Contratante contratante) {
        long id = proximoId.getAndIncrement();
        registros.put(id, contratante);
        return id;
    }

    @Override
    public Optional<Contratante> buscarPorId(long id) {
        return Optional.ofNullable(registros.get(id));
    }

    @Override
    public boolean atualizar(long id, Contratante novosDados) {
        // replace (e não put): só substitui se o id já existir, nunca cria registro.
        return registros.replace(id, novosDados) != null;
    }

    @Override
    public boolean excluir(long id) {
        return registros.remove(id) != null;
    }
}
