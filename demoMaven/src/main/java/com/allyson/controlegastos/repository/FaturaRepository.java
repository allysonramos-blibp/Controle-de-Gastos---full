package com.allyson.controlegastos.repository;

import com.allyson.controlegastos.model.Fatura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface FaturaRepository extends JpaRepository<Fatura, Long> {
    Optional<Fatura> findByMesAndAno(Integer mes, Integer ano);
}