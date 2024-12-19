package com.confetaria.confetaria_backend.service.interfaces;

import com.confetaria.confetaria_backend.dto.ProdutoRequestDTO;
import com.confetaria.confetaria_backend.model.Produto;

import java.util.List;
import java.util.Optional;

public interface ProdutoService {
    List<Produto> listarProdutos();

    Produto salvarProduto(ProdutoRequestDTO produtoRequest);

    Produto atualizarProduto(Integer id, ProdutoRequestDTO produtoRequest);

    Optional<Produto> buscarPorId(Integer id);

    void deletarProduto(Integer id);

}
