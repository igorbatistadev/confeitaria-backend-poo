package com.confetaria.confetaria_backend.service;

import com.confetaria.confetaria_backend.dto.PedidoItemRequestDTO;
import com.confetaria.confetaria_backend.dto.PedidoRequestDTO;
import com.confetaria.confetaria_backend.model.Pedido;
import com.confetaria.confetaria_backend.model.PedidoItem;
import com.confetaria.confetaria_backend.model.Produto;
import com.confetaria.confetaria_backend.repository.PedidoItemRepository;
import com.confetaria.confetaria_backend.repository.PedidoRepository;
import com.confetaria.confetaria_backend.repository.ProdutoRepository;
import com.confetaria.confetaria_backend.service.impl.PedidoServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PedidoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private PedidoItemRepository pedidoItemRepository;

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private PedidoServiceImpl pedidoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testListarPedidos() {
        Pedido pedido = new Pedido();
        when(pedidoRepository.findAll()).thenReturn(Arrays.asList(pedido));

        List<Pedido> pedidos = pedidoService.listarPedidos();

        assertNotNull(pedidos);
        assertEquals(1, pedidos.size());
        verify(pedidoRepository, times(1)).findAll();
    }

    @Test
    void testSalvarPedido() {
        PedidoRequestDTO pedidoRequestDTO = mock(PedidoRequestDTO.class);
        PedidoItemRequestDTO pedidoItemRequestDTO = mock(PedidoItemRequestDTO.class);
        Produto produto = mock(Produto.class);
        Pedido pedido = new Pedido();

        when(pedidoRequestDTO.cliente()).thenReturn(1);
        when(pedidoRequestDTO.statusPedido()).thenReturn('N');
        when(pedidoRequestDTO.descricaoPedido()).thenReturn("Descrição");
        when(pedidoRequestDTO.listaItens()).thenReturn(Arrays.asList(pedidoItemRequestDTO));
        when(produtoRepository.findById(anyInt())).thenReturn(Optional.of(produto));
        when(produto.getValorUnitario()).thenReturn(BigDecimal.TEN);
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedido);
        when(pedidoItemRequestDTO.produtoId()).thenReturn(1);
        when(pedidoItemRequestDTO.qtProduto()).thenReturn(2);

        Pedido pedidoSalvo = pedidoService.salvarPedido(pedidoRequestDTO);

        assertNotNull(pedidoSalvo);
        verify(pedidoRepository, times(2)).save(any(Pedido.class));
        verify(pedidoItemRepository, times(1)).save(any(PedidoItem.class));
    }

    @Test
    void testAtualizarPedido() {
        PedidoRequestDTO pedidoRequestDTO = mock(PedidoRequestDTO.class);
        Pedido pedido = new Pedido();

        when(pedidoRequestDTO.cliente()).thenReturn(1);
        when(pedidoRequestDTO.statusPedido()).thenReturn('A');
        when(pedidoRequestDTO.descricaoPedido()).thenReturn("Descrição Atualizada");
        when(pedidoRepository.findById(anyInt())).thenReturn(Optional.of(pedido));
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedido);

        Pedido pedidoAtualizado = pedidoService.atualizarPedido(1, pedidoRequestDTO);

        assertNotNull(pedidoAtualizado);
        verify(pedidoRepository, times(1)).findById(anyInt());
        verify(pedidoRepository, times(1)).save(any(Pedido.class));
    }

    @Test
    void testBuscarPorId() {
        Pedido pedido = new Pedido();
        when(pedidoRepository.findById(anyInt())).thenReturn(Optional.of(pedido));

        Optional<Pedido> pedidoOptional = pedidoService.buscarPorId(1);

        assertTrue(pedidoOptional.isPresent());
        verify(pedidoRepository, times(1)).findById(anyInt());
    }

    @Test
    void testDeletarPedido() {
        doNothing().when(pedidoRepository).deleteById(anyInt());

        pedidoService.deletarPedido(1);

        verify(pedidoRepository, times(1)).deleteById(anyInt());
    }
}