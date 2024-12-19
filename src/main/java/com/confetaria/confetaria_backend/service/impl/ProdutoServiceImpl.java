package com.confetaria.confetaria_backend.service.impl;

import com.confetaria.confetaria_backend.dto.ProdutoRequestDTO;
import com.confetaria.confetaria_backend.model.Precificacao;
import com.confetaria.confetaria_backend.model.Produto;
import com.confetaria.confetaria_backend.repository.PrecificacaoRepository;
import com.confetaria.confetaria_backend.repository.ProdutoRepository;
import com.confetaria.confetaria_backend.service.interfaces.ProdutoService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoServiceImpl implements ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final PrecificacaoRepository precificacaoRepository;

    public ProdutoServiceImpl(ProdutoRepository produtoRepository, PrecificacaoRepository precificacaoRepository) {
        this.produtoRepository = produtoRepository;
        this.precificacaoRepository = precificacaoRepository;
    }

    public List<Produto> listarProdutos() {
        return produtoRepository.findAll().stream()
                .toList();
    }

    public Produto salvarProduto(ProdutoRequestDTO produtoRequest) {
        Precificacao precificacao = precificacaoRepository.findById(produtoRequest.precificacao())
                .orElseThrow(() -> new EntityNotFoundException("Precificação não encontrada"));

        Produto produto = new Produto();
        produto.setDescricao(produtoRequest.descricao());
        produto.setValorUnitario(produtoRequest.valorUnitario());
        produto.setPrecificacao(precificacao);

        return produtoRepository.save(produto);
    }

    public Produto atualizarProduto(Integer id, ProdutoRequestDTO produtoRequest) {
        Precificacao precificacao = new Precificacao();
        precificacao.setCodigoPreficacao(produtoRequest.precificacao());

        Produto produto = produtoRepository.findById(id).orElseThrow();
        produto.setDescricao(produtoRequest.descricao());
        produto.setValorUnitario(produtoRequest.valorUnitario());
        produto.setPrecificacao(precificacao);

        return produtoRepository.save(produto);
    }

    public Optional<Produto> buscarPorId(Integer id) {
        return produtoRepository.findById(id);
    }

    public void deletarProduto(Integer id) {
        produtoRepository.deleteById(id);
    }

}
