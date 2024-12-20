package com.confetaria.confetaria_backend.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.confetaria.confetaria_backend.model.Pagamentos;
import com.confetaria.confetaria_backend.repository.PagamentosRepository;
import com.confetaria.confetaria_backend.service.interfaces.PagamentoService;

@Service
public class PagamentoServiceImpl implements PagamentoService {

    private final PagamentosRepository pagamentosRepository;

    public PagamentoServiceImpl(PagamentosRepository pagamentosRepository) {
        this.pagamentosRepository = pagamentosRepository;
    }

    public List<Pagamentos> listarPagamentos() {
        return pagamentosRepository.findAll().stream()
                .toList();
    }

    public Pagamentos realizarPagamento(Pagamentos pagamentos) {
        if (!(pagamentos.getValorPagamento().compareTo(BigDecimal.ZERO) > 0)) {
            throw new IllegalArgumentException("Valor não pode ser menor ou igual a zero");
        }
        return pagamentosRepository.save(pagamentos);
    }

}