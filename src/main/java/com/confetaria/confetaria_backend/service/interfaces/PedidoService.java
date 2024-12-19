package com.confetaria.confetaria_backend.service.interfaces;

import com.confetaria.confetaria_backend.dto.PedidoRequestDTO;
import com.confetaria.confetaria_backend.model.Pedido;

import java.util.List;
import java.util.Optional;

public interface PedidoService {
    List<Pedido> listarPedidos();

    Pedido salvarPedido(PedidoRequestDTO pedidoRequestDTO);

    Pedido atualizarPedido(Integer id, PedidoRequestDTO pedidoRequestDTO);

    Optional<Pedido> buscarPorId(Integer id);

    void deletarPedido(Integer id);

}
