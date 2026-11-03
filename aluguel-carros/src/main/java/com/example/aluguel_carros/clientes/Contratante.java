package com.example.aluguel_carros.clientes;

/**
 * Representação provisória dos dados de identificação do contratante
 * citados no enunciado (RG, CPF, Nome, Endereço) e a profissão.
 *
 * Pendências: empregadores e rendimentos (máximo 3), relação com Cliente
 * (AMB-01), regras de unicidade e validação de CPF/RG.
 */
public class Contratante {

    private final String rg;
    private final String cpf;
    private final String nome;
    private final String endereco;
    private final String profissao;

    public Contratante(String rg, String cpf, String nome, String endereco, String profissao) {
        this.rg = rg;
        this.cpf = cpf;
        this.nome = nome;
        this.endereco = endereco;
        this.profissao = profissao;
    }

    public String getRg() { return rg; }
    public String getCpf() { return cpf; }
    public String getNome() { return nome; }
    public String getEndereco() { return endereco; }
    public String getProfissao() { return profissao; }
}
