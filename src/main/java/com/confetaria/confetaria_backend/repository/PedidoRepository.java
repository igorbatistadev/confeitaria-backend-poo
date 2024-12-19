package com.confetaria.confetaria_backend.repository;

import com.confetaria.confetaria_backend.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
}
