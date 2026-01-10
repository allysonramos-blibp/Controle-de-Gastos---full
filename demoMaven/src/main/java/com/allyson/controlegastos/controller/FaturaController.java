package com.allyson.controlegastos.controller;

import com.allyson.controlegastos.model.Fatura;
import com.allyson.controlegastos.service.FaturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/faturas")
@CrossOrigin("*")
public class FaturaController {

    @Autowired
    private FaturaService service;

    @GetMapping
    public List<Fatura> listar() {
        return service.listarTodas();
    }

    // Corrigido: Agora usa /api/faturas/buscar para evitar conflitos
    @GetMapping("/buscar")
    public Fatura buscarOuCriar(@RequestParam int mes, @RequestParam int ano) {
        return service.buscarOuCriar(mes, ano);
    }

    @PostMapping("/{id}/pagar")
    public void pagar(@PathVariable Long id) {
        service.pagar(id);
    }
}