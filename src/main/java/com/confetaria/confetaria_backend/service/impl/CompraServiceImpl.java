
package com.confetaria.confetaria_backend.service.impl;

import com.confetaria.confetaria_backend.dto.CompraRequestDTO;
import com.confetaria.confetaria_backend.model.Compra;
import com.confetaria.confetaria_backend.model.Material;
import com.confetaria.confetaria_backend.repository.CompraRepository;
import com.confetaria.confetaria_backend.repository.MaterialRepository;
import com.confetaria.confetaria_backend.service.interfaces.CompraService;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompraServiceImpl implements CompraService {

    private final CompraRepository compraRepository;
    private final MaterialRepository materialRepository;

    public CompraServiceImpl(CompraRepository compraRepository, MaterialRepository materialRepository) {
        this.compraRepository = compraRepository;
        this.materialRepository = materialRepository;
    }

    public List<Compra> listarCompras() {
        return compraRepository.findAll().stream()
                .toList();
    }

    public Compra salvarCompra(CompraRequestDTO compraRequest) {
        Material material = materialRepository.findById(compraRequest.materialId())
                .orElseThrow(() -> new IllegalArgumentException("Material não encontrado"));

        Compra compra = new Compra();
        compra.setMaterial(material);
        compra.setDataCompra(compraRequest.dataCompra());
        compra.setQuantidadeAdquirida(compraRequest.quantidadeAdquirida());
        compra.setValorTotal(compraRequest.valorTotal());
        compra.setQuantidadeRestante(compraRequest.quantidadeAdquirida());
        compra.setDataValidade(compraRequest.dataValidade());

        return compraRepository.save(compra);
    }

    public Optional<Compra> buscarPorId(Integer id) {
        return compraRepository.findById(id);
    }

}

