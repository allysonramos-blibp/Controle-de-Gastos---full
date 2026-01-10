package com.allyson.controlegastos.controller;

import com.allyson.controlegastos.model.Transacao;
import com.allyson.controlegastos.service.TransacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transacoes")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class TransacaoController {

    @Autowired
    private TransacaoService service;

    @GetMapping
    public List<Transacao> listar() {
        return service.listarTodas();
    }

    // 🔥 ESTE É O MÉTODO QUE RESOLVE O ERRO 405 NO EDITAR
    @GetMapping("/{id}")
    public Transacao buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    public Transacao salvar(@RequestBody Transacao t) {
        return service.salvar(t);
    }

    @PutMapping("/{id}")
    public Transacao atualizar(@PathVariable Long id, @RequestBody Transacao t) {
        return service.atualizar(id, t);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }
}