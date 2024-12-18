package com.confetaria.confetaria_backend.repository;

import com.confetaria.confetaria_backend.model.Precificacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrecificacaoRepository extends JpaRepository<Precificacao, Integer> {
}