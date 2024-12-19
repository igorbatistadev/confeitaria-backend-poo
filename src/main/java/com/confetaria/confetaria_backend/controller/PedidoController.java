package com.confetaria.confetaria_backend.controller;

import com.confetaria.confetaria_backend.dto.PedidoRequestDTO;
import com.confetaria.confetaria_backend.model.Pedido;
import com.confetaria.confetaria_backend.service.interfaces.PedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pedido")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping
    public List<Pedido> listarPedidos() {
        return pedidoService.listarPedidos();
    }

    @PostMapping
    public ResponseEntity<Pedido> salvarPedido(@RequestBody PedidoRequestDTO pedido) {
        Pedido savedPedido = pedidoService.salvarPedido(pedido);
        return new ResponseEntity<>(savedPedido, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pedido> atualizarProduto(@PathVariable Integer id, @RequestBody PedidoRequestDTO pedido) {
        Pedido updatedPedido = pedidoService.atualizarPedido(id, pedido);
        return new ResponseEntity<>(updatedPedido, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> getPedidoPorId(@PathVariable Integer id) {
        Optional<Pedido> pedido = pedidoService.buscarPorId(id);
        return pedido.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPedido(@PathVariable Integer id) {
        pedidoService.deletarPedido(id);
        return ResponseEntity.noContent().build();
    }
}
