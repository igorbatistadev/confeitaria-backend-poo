package com.confetaria.confetaria_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.confetaria.confetaria_backend.model.Pagamentos;

public interface PagamentosRepository extends JpaRepository<Pagamentos, Integer> {

    List<Pagamentos> findAllByCodigoPedido(Integer codigoPedido);

}