package com.confetaria.confetaria_backend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.confetaria.confetaria_backend.model.Pagamentos;
import com.confetaria.confetaria_backend.repository.PagamentosRepository;
import com.confetaria.confetaria_backend.service.impl.PagamentoServiceImpl;

@ExtendWith(MockitoExtension.class)
class PagamentosServiceTest {

    @Mock
    private PagamentosRepository pagamentosRepository;

    @InjectMocks
    private PagamentoServiceImpl pagamentoService;

    @Test
    public void realizarPagamento_ComDadosValidos_RetornaPagamento() {
        Pagamentos pagamento = new Pagamentos();
        pagamento.setCodigoPagamento(1);
        pagamento.setValorPagamento(BigDecimal.valueOf(100.00));

        when(pagamentosRepository.save(pagamento)).thenReturn(pagamento);

        Pagamentos resultado = pagamentoService.realizarPagamento(pagamento);

        assertEquals(1, resultado.getCodigoPagamento(), "O código do pagamento deve ser 1");
        assertEquals(BigDecimal.valueOf(100.00), resultado.getValorPagamento(), "O valor do pagamento deve ser 100.00");

        verify(pagamentosRepository).save(pagamento);
    }

    @Test
    public void realizarPagamento_ComDadosInvalidosValidos_LancarExecao() {
        Pagamentos pagamentoNegativo = new Pagamentos();
        pagamentoNegativo.setCodigoPagamento(1);
        pagamentoNegativo.setValorPagamento(BigDecimal.valueOf(-100.00));

        Pagamentos pagamentoZero = new Pagamentos();
        pagamentoZero.setCodigoPagamento(2);
        pagamentoZero.setValorPagamento(BigDecimal.ZERO);

        assertThrows(IllegalArgumentException.class, () -> pagamentoService.realizarPagamento(pagamentoNegativo),
                "Pagamento com valor negativo deve lançar exceção");
        assertThrows(IllegalArgumentException.class, () -> pagamentoService.realizarPagamento(pagamentoZero),
                "Pagamento com valor zero deve lançar exceção");

        verify(pagamentosRepository, never()).save(any());
    }

    @Test
    public void listarPagamentos_deveRetornarListaDePagamentos() {
        Pagamentos pagamento1 = new Pagamentos();
        pagamento1.setCodigoPagamento(1);
        pagamento1.setValorPagamento(BigDecimal.valueOf(100.00));

        Pagamentos pagamento2 = new Pagamentos();
        pagamento2.setCodigoPagamento(2);
        pagamento2.setValorPagamento(BigDecimal.valueOf(200.00));

        List<Pagamentos> listaPagamentos = List.of(pagamento1, pagamento2);

        when(pagamentosRepository.findAll()).thenReturn(listaPagamentos);

        List<Pagamentos> resultado = pagamentoService.listarPagamentos();

        assertEquals(2, resultado.size(), "Deve retornar 2 pagamentos");
        assertEquals(BigDecimal.valueOf(100.00), resultado.get(0).getValorPagamento(),
                "O valor do primeiro pagamento deve ser 100.00");
        assertEquals(BigDecimal.valueOf(200.00), resultado.get(1).getValorPagamento(),
                "O valor do segundo pagamento deve ser 200.00");

        verify(pagamentosRepository).findAll();
    }

    @Test
    public void listarPagamentos_deveRetornarListaDePagamentosVazia() {
        when(pagamentosRepository.findAll()).thenReturn(Collections.emptyList());

        List<Pagamentos> resultado = pagamentoService.listarPagamentos();

        assertEquals(0, resultado.size(), "A lista de pagamentos deve estar vazia");

        verify(pagamentosRepository).findAll();
    }
}