package com.example.aluguel_carros.clientes;

import java.util.Optional;

/**
 * Contrato provisório de armazenamento de contratantes.
 * Não define banco, chave primária definitiva nem regra de unicidade.
 */
public interface ArmazenamentoDeContratantes {

    /** Armazena o contratante e devolve o identificador técnico gerado. */
    long cadastrar(Contratante contratante);

    /** Busca por identificador técnico; vazio se não existir. */
    Optional<Contratante> buscarPorId(long id);
}
