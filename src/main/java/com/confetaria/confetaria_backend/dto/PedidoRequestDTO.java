package com.confetaria.confetaria_backend.dto;

import java.util.List;

public record PedidoRequestDTO(
        Character statusPedido,
        String descricaoPedido,
        Integer cliente,
        List<PedidoItemRequestDTO> listaItens
) {
}
