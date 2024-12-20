package com.confetaria.confetaria_backend.service.interfaces;

import com.confetaria.confetaria_backend.model.Pagamentos;
import java.util.List;

public interface PagamentoService {
    List<Pagamentos> listarPagamentos();

    Pagamentos realizarPagamento(Pagamentos pagamentos);

}