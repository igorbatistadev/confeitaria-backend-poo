package com.confetaria.confetaria_backend.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.confetaria.confetaria_backend.dto.ProdutoRequestDTO;
import com.confetaria.confetaria_backend.model.Produto;
import com.confetaria.confetaria_backend.repository.ProdutoRepository;
import com.confetaria.confetaria_backend.service.impl.ProdutoServiceImpl;
import com.confetaria.confetaria_backend.model.Precificacao;
import com.confetaria.confetaria_backend.repository.PrecificacaoRepository;
import jakarta.persistence.EntityNotFoundException;

import java.math.BigDecimal;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProdutoServiceTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private PrecificacaoRepository precificacaoRepository;

    @InjectMocks
    private ProdutoServiceImpl produtoService;

    @Test
    public void salvarProduto_ComDadosValidos_RetornaProduto() {
        ProdutoRequestDTO produtoRequest = new ProdutoRequestDTO("Teste", BigDecimal.valueOf(2.22), 1);
        Precificacao precificacao = new Precificacao();
        precificacao.setCodigoPreficacao(1);

        when(precificacaoRepository.findById(1)).thenReturn(Optional.of(precificacao));
        when(produtoRepository.save(any(Produto.class))).thenReturn(new Produto());

        Produto result = produtoService.salvarProduto(produtoRequest);

        assertNotNull(result);
        verify(produtoRepository, times(1)).save(any(Produto.class));
    }

    @Test
    public void salvarProduto_ComPrecificacaoInvalida_LancaExcecao() {
        ProdutoRequestDTO produtoRequest = new ProdutoRequestDTO("Teste", BigDecimal.valueOf(2.22), 1);

        when(precificacaoRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> produtoService.salvarProduto(produtoRequest));
    }

    @Test
    public void atualizarProduto_ComDadosValidos_RetornaProdutoAtualizado() {
        ProdutoRequestDTO produtoRequest = new ProdutoRequestDTO("Teste Atualizado", BigDecimal.valueOf(3.33), 1);
        Precificacao precificacao = new Precificacao();
        precificacao.setCodigoPreficacao(1);
        Produto produto = new Produto();
        produto.setCodigoProduto(1);
        produto.setDescricao("Teste");
        produto.setValorUnitario(BigDecimal.valueOf(2.22));
        produto.setPrecificacao(precificacao);

        when(produtoRepository.findById(1)).thenReturn(Optional.of(produto));
        when(produtoRepository.save(any(Produto.class))).thenReturn(produto);

        Produto result = produtoService.atualizarProduto(1, produtoRequest);

        assertNotNull(result);
        assertEquals("Teste Atualizado", result.getDescricao());
        assertEquals(BigDecimal.valueOf(3.33), result.getValorUnitario());
        verify(produtoRepository, times(1)).save(any(Produto.class));
    }

    @Test
    public void buscarPorId_ComIdExistente_RetornaProduto() {
        Produto produto = new Produto();
        produto.setCodigoProduto(1);

        when(produtoRepository.findById(1)).thenReturn(Optional.of(produto));

        Optional<Produto> result = produtoService.buscarPorId(1);

        assertTrue(result.isPresent());
        assertEquals(1, result.get().getCodigoProduto());
    }

    @Test
    public void buscarPorId_ComIdInexistente_RetornaVazio() {
        when(produtoRepository.findById(1)).thenReturn(Optional.empty());

        Optional<Produto> result = produtoService.buscarPorId(1);

        assertFalse(result.isPresent());
    }

    @Test
    public void deletarProduto_ComIdExistente_DeletaProduto() {
        doNothing().when(produtoRepository).deleteById(1);

        produtoService.deletarProduto(1);

        verify(produtoRepository, times(1)).deleteById(1);
    }
}
