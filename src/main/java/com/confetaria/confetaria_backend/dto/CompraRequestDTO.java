
package com.confetaria.confetaria_backend.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CompraRequestDTO(
        Integer materialId,
        LocalDate dataCompra,
        Integer quantidadeAdquirida,
        BigDecimal valorTotal,
        LocalDate dataValidade
) {}

