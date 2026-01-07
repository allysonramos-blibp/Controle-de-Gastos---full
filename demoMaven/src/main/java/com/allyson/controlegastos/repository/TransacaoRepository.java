package com.allyson.controlegastos.repository;

import com.allyson.controlegastos.model.TipoTransacao;
import com.allyson.controlegastos.model.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

    List<Transacao> findByTipo(TipoTransacao tipo);

    List<Transacao> findByDataBetween(LocalDate inicio, LocalDate fim);

    List<Transacao> findByTipoAndDataBetween(TipoTransacao tipo, LocalDate inicio, LocalDate fim);
}