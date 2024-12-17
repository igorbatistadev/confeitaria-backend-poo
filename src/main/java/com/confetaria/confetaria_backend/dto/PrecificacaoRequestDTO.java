package com.confetaria.confetaria_backend.dto;

import java.math.BigDecimal;
import java.util.List;

public record PrecificacaoRequestDTO (
        String descricao,
        BigDecimal margemLucro,
        List<MaterialUtilizadosResquestDTO> materiaisUtilizados
) {}