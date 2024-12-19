package com.confetaria.confetaria_backend.dto;

import java.math.BigDecimal;

public record ProdutoRequestDTO(
        String descricao,
        BigDecimal valorUnitario,
        Integer precificacao) {
}
