package com.confetaria.confetaria_backend.controller;

import com.confetaria.confetaria_backend.dto.PrecificacaoRequestDTO;
import com.confetaria.confetaria_backend.model.Precificacao;
import com.confetaria.confetaria_backend.service.interfaces.PrecificacaoService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/precificacao")
public class PrecificacaoController {

    private final PrecificacaoService precificacaoService;

    public PrecificacaoController(PrecificacaoService precificacaoService) {
        this.precificacaoService = precificacaoService;
    }

    @PostMapping
    public ResponseEntity<Precificacao> criarPrecificacao(@RequestBody PrecificacaoRequestDTO precificacaoRequestDTO) {
        Precificacao savedPrecificacao = precificacaoService.criarPrecificacao(precificacaoRequestDTO);
        return new ResponseEntity<>(savedPrecificacao, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Precificacao> listarPrecificacoes() {
        return precificacaoService.listarPrecificacoes();
    }
}