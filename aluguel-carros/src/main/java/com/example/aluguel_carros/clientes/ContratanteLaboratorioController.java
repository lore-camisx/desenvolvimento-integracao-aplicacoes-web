package com.example.aluguel_carros.clientes;

import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * EXPERIMENTAL: prova de integração HTTP do armazenamento provisório de contratantes.
 * Não é o CRUD de Cliente exigido no enunciado (relação Cliente x Contratante pendente).
 * Permanece bloqueado pela configuração de segurança (denyAll em /api/laboratorio/**).
 * Sem validação de campos: a obrigatoriedade dos dados do cadastro está pendente (AMB-01).
 */
@RestController
@RequestMapping("/api/laboratorio/contratantes")
public class ContratanteLaboratorioController {

    private final ArmazenamentoDeContratantes armazenamento;

    public ContratanteLaboratorioController(ArmazenamentoDeContratantes armazenamento) {
        this.armazenamento = armazenamento;
    }

    @PostMapping
    public ResponseEntity<ContratanteResposta> cadastrar(@RequestBody ContratanteEntrada entrada) {
        Contratante contratante = new Contratante(
                entrada.rg(), entrada.cpf(), entrada.nome(), entrada.endereco(), entrada.profissao());
        long id = armazenamento.cadastrar(contratante);
        return ResponseEntity
                .created(URI.create("/api/laboratorio/contratantes/" + id))
                .body(ContratanteResposta.de(id, contratante));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContratanteResposta> consultar(@PathVariable("id") long id) {
        return armazenamento.buscarPorId(id)
                .map(c -> ResponseEntity.ok(ContratanteResposta.de(id, c)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    public record ContratanteEntrada(
            String rg, String cpf, String nome, String endereco, String profissao) { }

    public record ContratanteResposta(
            long id, String rg, String cpf, String nome, String endereco, String profissao) {

        static ContratanteResposta de(long id, Contratante c) {
            return new ContratanteResposta(
                    id, c.getRg(), c.getCpf(), c.getNome(), c.getEndereco(), c.getProfissao());
        }
    }
}
