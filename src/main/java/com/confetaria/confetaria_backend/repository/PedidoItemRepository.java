package com.confetaria.confetaria_backend.repository;

import com.confetaria.confetaria_backend.model.Pedido;
import com.confetaria.confetaria_backend.model.PedidoItem;
import com.confetaria.confetaria_backend.model.PedidoItemId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoItemRepository extends JpaRepository<PedidoItem, PedidoItemId> {
    List<PedidoItem> findAllByPedido(Pedido pedido);
}
