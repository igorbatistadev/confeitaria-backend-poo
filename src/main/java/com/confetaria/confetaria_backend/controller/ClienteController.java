package com.confetaria.confetaria_backend.controller;

import com.confetaria.confetaria_backend.model.Cliente;
import com.confetaria.confetaria_backend.model.Material;
import com.confetaria.confetaria_backend.service.interfaces.ClienteService;
import com.confetaria.confetaria_backend.service.interfaces.MaterialService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cliente")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public List<Cliente> listarClientes() {
        return clienteService.listarClientes();
    }

    @PostMapping
    public ResponseEntity<Cliente> criarMaterial(@RequestBody Cliente cliente) {
        Cliente savedCliente = clienteService.salvarCliente(cliente);
        return new ResponseEntity<>(savedCliente, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getMaterialPorId(@PathVariable Integer id) {
        Optional<Cliente> cliente = clienteService.buscarPorId(id);
        return cliente.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}