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

    /**
     * Substitui por completo os dados do cadastro existente, mantendo o id.
     * Devolve false, sem criar nada, se o id não existir.
     */
    boolean atualizar(long id, Contratante novosDados);

    /** Remove o cadastro. Devolve true se existia e false se o id não existir. */
    boolean excluir(long id);
}
