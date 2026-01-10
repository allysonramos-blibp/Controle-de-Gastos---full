package com.allyson.controlegastos.service;

import com.allyson.controlegastos.model.*;
import com.allyson.controlegastos.repository.FaturaRepository;
import com.allyson.controlegastos.repository.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

@Service
public class TransacaoService {

    @Autowired
    private TransacaoRepository repository;

    @Autowired
    private FaturaRepository faturaRepository;

    public Transacao salvar(Transacao transacao) {

        if (transacao.getTotalParcelas() != null && transacao.getTotalParcelas() > 1) {
            salvarParcelada(transacao);
            return transacao;
        }

        Transacao salva = repository.save(transacao);
        atualizarFatura(salva);
        return salva;
    }

    private void salvarParcelada(Transacao base) {

        BigDecimal valorParcela = base.getValor()
                .divide(BigDecimal.valueOf(base.getTotalParcelas()), 2, RoundingMode.HALF_UP);

        for (int i = 1; i <= base.getTotalParcelas(); i++) {

            Transacao parcela = new Transacao();
            parcela.setDescricao(base.getDescricao() + " (" + i + "/" + base.getTotalParcelas() + ")");
            parcela.setValor(valorParcela);
            parcela.setCategoria(base.getCategoria());
            parcela.setTipo(TipoTransacao.DESPESA);
            parcela.setTipoPagamento(TipoPagamento.CARTAO_CREDITO);
            parcela.setParcelaAtual(i);
            parcela.setTotalParcelas(base.getTotalParcelas());
            parcela.setData(base.getData().plusMonths(i - 1));

            repository.save(parcela);
            atualizarFatura(parcela);
        }
    }

    private void atualizarFatura(Transacao transacao) {

        if (transacao.getTipoPagamento() != TipoPagamento.CARTAO_CREDITO) return;

        int mes = transacao.getData().getMonthValue();
        int ano = transacao.getData().getYear();

        Fatura fatura = faturaRepository
                .findByMesAndAno(mes, ano)
                .orElseGet(() -> {
                    Fatura f = new Fatura();
                    f.setMes(mes);
                    f.setAno(ano);
                    f.setStatus(StatusFatura.ABERTA);
                    f.setTotal(BigDecimal.ZERO);
                    return faturaRepository.save(f);
                });

        fatura.setTotal(fatura.getTotal().add(transacao.getValor()));
        faturaRepository.save(fatura);
    }

    /* CRUD */

    public List<Transacao> listarTodas() {
        return repository.findAll();
    }

    public Transacao buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transação não encontrada: " + id));
    }
    public Transacao atualizar(Long id, Transacao nova) {

        Transacao existente = buscarPorId(id);

        existente.setDescricao(nova.getDescricao());
        existente.setValor(nova.getValor());
        existente.setData(nova.getData());
        existente.setCategoria(nova.getCategoria());
        existente.setTipo(nova.getTipo());
        existente.setTipoPagamento(nova.getTipoPagamento());

        return repository.save(existente);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }

    public List<Transacao> listarPorFatura(int mes, int ano) {
        return repository.findByMesFaturaAndAnoFatura(mes, ano);
    }
}
