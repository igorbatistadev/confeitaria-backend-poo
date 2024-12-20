package com.confetaria.confetaria_backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.confetaria.confetaria_backend.model.Pagamentos;
import com.confetaria.confetaria_backend.service.interfaces.PagamentoService;

@RestController
@RequestMapping("/api/pagamentos")
public class PagamentosController {

    private final PagamentoService pagamentoService;

    public PagamentosController(PagamentoService pagamentoService) {
        this.pagamentoService = pagamentoService;
    }

    @GetMapping
    public List<Pagamentos> listarPagamentos() {
        return pagamentoService.listarPagamentos();
    }

    @PostMapping
    public ResponseEntity<Pagamentos> realizarPagamento(@RequestBody Pagamentos pagamento) {
        Pagamentos savedPedido = pagamentoService.realizarPagamento(pagamento);
        return new ResponseEntity<>(savedPedido, HttpStatus.CREATED);
    }

}