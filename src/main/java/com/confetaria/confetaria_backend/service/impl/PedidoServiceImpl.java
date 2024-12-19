package com.confetaria.confetaria_backend.service.impl;

import com.confetaria.confetaria_backend.dto.PedidoItemRequestDTO;
import com.confetaria.confetaria_backend.dto.PedidoRequestDTO;
import com.confetaria.confetaria_backend.model.Cliente;
import com.confetaria.confetaria_backend.model.Pedido;
import com.confetaria.confetaria_backend.model.PedidoItem;
import com.confetaria.confetaria_backend.model.Produto;
import com.confetaria.confetaria_backend.repository.PedidoItemRepository;
import com.confetaria.confetaria_backend.repository.PedidoRepository;
import com.confetaria.confetaria_backend.repository.ProdutoRepository;
import com.confetaria.confetaria_backend.service.interfaces.PedidoService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository pedidoRepository;
    private final PedidoItemRepository pedidoItemRepository;
    private final ProdutoRepository produtoRepository;

    public PedidoServiceImpl(PedidoRepository pedidoRepository, PedidoItemRepository pedidoItemRepository,
                             ProdutoRepository produtoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.pedidoItemRepository = pedidoItemRepository;
        this.produtoRepository = produtoRepository;
    }

    public List<Pedido> listarPedidos() {
        return pedidoRepository.findAll().stream()
                .toList();
    }

    public Pedido salvarPedido(PedidoRequestDTO pedidoRequestDTO) {
        Cliente cliente = new Cliente();
        cliente.setCodigoUsuario(pedidoRequestDTO.cliente());

        Pedido pedido = new Pedido();
        pedido.setStatusPedido(pedidoRequestDTO.statusPedido());
        pedido.setDescricaoPedido(pedidoRequestDTO.descricaoPedido());
        pedido.setCliente(cliente);
        pedido.setValorTotal(BigDecimal.ZERO);

        Pedido pedidoSalvo = pedidoRepository.save(pedido);

        List<PedidoItemRequestDTO> pedidoItemRequestDTOS = pedidoRequestDTO.listaItens();
        BigDecimal valorTotal = BigDecimal.ZERO;
        for (PedidoItemRequestDTO pedidoItemRequestDTO : pedidoItemRequestDTOS) {
            Produto produto = produtoRepository.findById(pedidoItemRequestDTO.produtoId())
                    .orElseThrow(() -> new IllegalArgumentException("Material não encontrado"));

            PedidoItem pedidoItem = new PedidoItem();
            pedidoItem.setPedido(pedidoSalvo);
            pedidoItem.setProduto(produto);
            pedidoItem.setTipoProduto('P');
            pedidoItem.setQuantidadeProduto(pedidoItemRequestDTO.qtProduto());
            pedidoItem.setVlUnitario(produto.getValorUnitario());

            pedidoItemRepository.save(pedidoItem);

            valorTotal = valorTotal
                    .add(pedidoItem.getVlUnitario().multiply(new BigDecimal(pedidoItemRequestDTO.qtProduto())));
        }
        pedidoSalvo.setValorTotal(valorTotal);

        return pedidoRepository.save(pedidoSalvo);
    }

    public Pedido atualizarPedido(Integer id, PedidoRequestDTO pedidoRequestDTO) {
        Cliente cliente = new Cliente();
        cliente.setCodigoUsuario(pedidoRequestDTO.cliente());

        Pedido pedido = pedidoRepository.findById(id).orElseThrow();
        pedido.setStatusPedido(pedidoRequestDTO.statusPedido());
        pedido.setDescricaoPedido(pedidoRequestDTO.descricaoPedido());
        pedido.setCliente(cliente);

        return pedidoRepository.save(pedido);
    }

    public Optional<Pedido> buscarPorId(Integer id) {
        return pedidoRepository.findById(id);
    }

    public void deletarPedido(Integer id) {
        pedidoRepository.deleteById(id);
    }

}
