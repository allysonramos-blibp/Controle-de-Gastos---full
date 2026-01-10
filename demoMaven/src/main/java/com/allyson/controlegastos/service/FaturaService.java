package com.allyson.controlegastos.service;

import com.allyson.controlegastos.model.Fatura;
import com.allyson.controlegastos.model.StatusFatura;
import com.allyson.controlegastos.model.TipoPagamento;
import com.allyson.controlegastos.model.Transacao;
import com.allyson.controlegastos.repository.FaturaRepository;
import com.allyson.controlegastos.repository.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class FaturaService {

    @Autowired
    private FaturaRepository repository;

    @Autowired
    private TransacaoRepository transacaoRepository;

    public List<Fatura> listarTodas() {
        return repository.findAll();
    }

    public Fatura buscarOuCriar(int mes, int ano) {
        Fatura f = repository.findByMesAndAno(mes, ano)
                .orElseGet(() -> {
                    Fatura nova = new Fatura();
                    nova.setMes(mes);
                    nova.setAno(ano);
                    nova.setStatus(StatusFatura.ABERTA);
                    nova.setTotal(BigDecimal.ZERO);
                    return repository.save(nova);
                });

        // RECALCULO: Soma todas as transações de cartão deste mês/ano
        List<Transacao> transacoes = transacaoRepository.findByMesFaturaAndAnoFatura(mes, ano);
        BigDecimal novoTotal = transacoes.stream()
                .filter(t -> t.getTipoPagamento() == TipoPagamento.CARTAO_CREDITO)
                .map(Transacao::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (f.getTotal().compareTo(novoTotal) != 0) {
            f.setTotal(novoTotal);
            repository.save(f);
        }

        return f;
    }

    public void pagar(Long faturaId) {
        Fatura fatura = repository.findById(faturaId)
                .orElseThrow(() -> new RuntimeException("Fatura não encontrada"));

        if (fatura.getStatus() == StatusFatura.PAGA) return;

        // Deleta as transações de cartão para o mês/ano da fatura
        transacaoRepository.deleteByTipoPagamentoAndMesAno(
                TipoPagamento.CARTAO_CREDITO,
                fatura.getMes(),
                fatura.getAno()
        );

        fatura.setStatus(StatusFatura.PAGA);
        repository.save(fatura);
    }
}