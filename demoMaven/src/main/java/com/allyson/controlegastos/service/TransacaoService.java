package com.allyson.controlegastos.service;

import com.allyson.controlegastos.model.Categoria;
import com.allyson.controlegastos.model.TipoTransacao;
import com.allyson.controlegastos.model.Transacao;
import com.allyson.controlegastos.repository.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import com.allyson.controlegastos.dto.RelatorioCategoriaDTO;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class TransacaoService {

    @Autowired
    private TransacaoRepository repository;

    public Transacao salvar(Transacao transacao) {
        return repository.save(transacao);
    }

    public List<Transacao> listarTodas() {
        return repository.findAll();
    }

    public List<Transacao> listarPorTipo(TipoTransacao tipo) {
        return repository.findByTipo(tipo);
    }

    public List<Transacao> listarPorPeriodo(LocalDate inicio, LocalDate fim) {
        return repository.findByDataBetween(inicio, fim);
    }

    public BigDecimal calcularSaldo() {
        List<Transacao> todasTransacoes = repository.findAll();

        BigDecimal totalReceitas = todasTransacoes.stream()
                .filter(t -> t.getTipo() == TipoTransacao.RECEITA)
                .map(Transacao::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalDespesas = todasTransacoes.stream()
                .filter(t -> t.getTipo() == TipoTransacao.DESPESA)
                .map(Transacao::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return totalReceitas.subtract(totalDespesas);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }

    public Transacao atualizar(Long id, Transacao transacaoAtualizada) {
        // Busca a transação existente no banco
        Transacao transacaoExistente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transação não encontrada com ID: " + id));

        // Atualiza os campos
        transacaoExistente.setDescricao(transacaoAtualizada.getDescricao());
        transacaoExistente.setValor(transacaoAtualizada.getValor());
        transacaoExistente.setData(transacaoAtualizada.getData());
        transacaoExistente.setCategoria(transacaoAtualizada.getCategoria());
        transacaoExistente.setTipo(transacaoAtualizada.getTipo());

        // Salva de volta no banco
        return repository.save(transacaoExistente);
    }

    public Transacao buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transação não encontrada com ID: " + id));
    }

    public List<RelatorioCategoriaDTO> relatorioPorCategoria() {
        List<Transacao> todasTransacoes = repository.findAll();

        Map<Categoria, List<Transacao>> agrupadas = todasTransacoes.stream()
                .filter(t -> t.getCategoria() != null)
                .collect(Collectors.groupingBy(Transacao::getCategoria));

        List<RelatorioCategoriaDTO> relatorio = new ArrayList<>();

        for (Map.Entry<Categoria, List<Transacao>> entry : agrupadas.entrySet()) {
            Categoria categoria = entry.getKey();
            List<Transacao> transacoes = entry.getValue();

            BigDecimal total = transacoes.stream()
                    .map(Transacao::getValor)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            Long quantidade = (long) transacoes.size();

            relatorio.add(new RelatorioCategoriaDTO(categoria, total, quantidade));
        }

        return relatorio;
    }
}