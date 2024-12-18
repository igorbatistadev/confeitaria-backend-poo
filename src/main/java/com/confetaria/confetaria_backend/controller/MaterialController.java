package com.confetaria.confetaria_backend.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.confetaria.confetaria_backend.model.Material;
import com.confetaria.confetaria_backend.service.interfaces.MaterialService;

@RestController
@RequestMapping("/api/material")
public class MaterialController {

    private final MaterialService materialService;

    public MaterialController(MaterialService materialService) {
        this.materialService = materialService;
    }

    @GetMapping
    public List<Material> listarMateriais() {
        return materialService.listarMateriais();
    }

    @PostMapping
    public ResponseEntity<Material> criarMaterial(@RequestBody Material material) {
        Material savedMaterial = materialService.salvarMaterial(material);
        return new ResponseEntity<>(savedMaterial, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Material> getMaterialPorId(@PathVariable Integer id) {
        Optional<Material> material = materialService.buscarPorId(id);
        return material.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Material> atualizarMaterial(
            @PathVariable Integer id, @RequestBody Material material) {
        Optional<Material> updatedMaterial = materialService.atualizarMaterial(id, material);
        return updatedMaterial.map(mat -> new ResponseEntity<>(mat, HttpStatus.OK))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarMaterial(@PathVariable Integer id) {
        boolean deletado = materialService.deletarMaterial(id);
        if (deletado) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}