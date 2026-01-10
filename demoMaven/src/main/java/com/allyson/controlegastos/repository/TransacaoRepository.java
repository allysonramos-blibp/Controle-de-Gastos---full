package com.allyson.controlegastos.repository;

import com.allyson.controlegastos.model.Transacao;
import com.allyson.controlegastos.model.TipoPagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

    // Busca transações baseadas no mês e ano extraídos da data
    @Query("SELECT t FROM Transacao t WHERE MONTH(t.data) = :mes AND YEAR(t.data) = :ano")
    List<Transacao> findByMesFaturaAndAnoFatura(@Param("mes") Integer mes, @Param("ano") Integer ano);

    @Modifying
    @Transactional
    @Query("DELETE FROM Transacao t WHERE t.tipoPagamento = :tipo " +
            "AND MONTH(t.data) = :mes AND YEAR(t.data) = :ano")
    void deleteByTipoPagamentoAndMesAno(
            @Param("tipo") TipoPagamento tipo,
            @Param("mes") int mes,
            @Param("ano") int ano
    );
}