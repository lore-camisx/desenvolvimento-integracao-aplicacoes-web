package com.example.aluguel_carros.persistencia.experimental;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * EXPERIMENTAL e só de teste (src/test): exercita o mecanismo do ORM com PostgreSQL
 * descartável. NÃO é o modelo persistente de Cliente nem do Contratante definitivo.
 * Sem unicidade de CPF, sem associação com Cliente, sem limite de empregadores.
 */
@Entity
@Table(name = "contratante_experimental")
public class ContratanteExperimentalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String rg;
    private String cpf;
    private String nome;
    private String endereco;
    private String profissao;

    /** Construtor sem parâmetros exigido pela JPA. */
    protected ContratanteExperimentalEntity() {
    }

    public ContratanteExperimentalEntity(
            String rg, String cpf, String nome, String endereco, String profissao) {
        this.rg = rg;
        this.cpf = cpf;
        this.nome = nome;
        this.endereco = endereco;
        this.profissao = profissao;
    }

    /** Só para o experimento de atualização (dirty checking). */
    public void alterarProfissao(String novaProfissao) {
        this.profissao = novaProfissao;
    }

    public Long getId() { return id; }
    public String getRg() { return rg; }
    public String getCpf() { return cpf; }
    public String getNome() { return nome; }
    public String getEndereco() { return endereco; }
    public String getProfissao() { return profissao; }
}
