package com.allyson.controlegastos.controller;

import com.allyson.controlegastos.model.TipoTransacao;
import com.allyson.controlegastos.model.Transacao;
import com.allyson.controlegastos.service.TransacaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.allyson.controlegastos.dto.RelatorioCategoriaDTO;
import java.util.List;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/transacoes")
public class TransacaoController {

    @Autowired
    private TransacaoService service;

    @PostMapping
    public ResponseEntity<Transacao> criar(@Valid @RequestBody Transacao transacao) {
        Transacao salva = service.salvar(transacao);
        return ResponseEntity.status(HttpStatus.CREATED).body(salva);
    }

    @GetMapping
    public ResponseEntity<List<Transacao>> listarTodas() {
        return ResponseEntity.ok(service.listarTodas());
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<Transacao>> listarPorTipo(@PathVariable TipoTransacao tipo) {
        return ResponseEntity.ok(service.listarPorTipo(tipo));
    }

    @GetMapping("/periodo")
    public ResponseEntity<List<Transacao>> listarPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {
        return ResponseEntity.ok(service.listarPorPeriodo(inicio, fim));
    }

    @GetMapping("/saldo")
    public ResponseEntity<BigDecimal> calcularSaldo() {
        return ResponseEntity.ok(service.calcularSaldo());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transacao> buscarPorId(@PathVariable Long id) {
        Transacao transacao = service.buscarPorId(id);
        return ResponseEntity.ok(transacao);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Transacao> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody Transacao transacao) {
        Transacao atualizada = service.atualizar(id, transacao);
        return ResponseEntity.ok(atualizada);
    }

    @GetMapping("/relatorio/categoria")
    public ResponseEntity<List<RelatorioCategoriaDTO>> relatorioPorCategoria() {
        return ResponseEntity.ok(service.relatorioPorCategoria());
    }
}
