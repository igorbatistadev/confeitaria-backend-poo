
package com.confetaria.confetaria_backend.controller;

import com.confetaria.confetaria_backend.dto.CompraRequestDTO;
import com.confetaria.confetaria_backend.model.Compra;


import com.confetaria.confetaria_backend.service.interfarces.CompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/compra")
public class CompraController {

    private final CompraService compraService;

    public CompraController(CompraService compraService) {
        this.compraService = compraService;
    }

    @GetMapping
    public List<Compra> listarCompras() {
        return compraService.listarCompras();
    }

    @PostMapping
    public ResponseEntity<Compra> salvarCompra(@RequestBody CompraRequestDTO compra) {
        Compra savedCompra = compraService.salvarCompra(compra);
        return new ResponseEntity<>(savedCompra, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Compra> getMaterialPorId(@PathVariable Integer id) {
        Optional<Compra> compra = compraService.buscarPorId(id);
        return compra.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
