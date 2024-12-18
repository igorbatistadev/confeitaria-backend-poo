package com.confetaria.confetaria_backend.service.interfarces;

import com.confetaria.confetaria_backend.dto.PrecificacaoRequestDTO;
import com.confetaria.confetaria_backend.model.*;
import java.util.List;

public interface PrecificacaoService {
    List<Precificacao> listarPrecificacoes();

    Precificacao criarPrecificacao(PrecificacaoRequestDTO precificacaoRequestDTO);
}