package com.confetaria.confetaria_backend.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.confetaria.confetaria_backend.model.Material;
import com.confetaria.confetaria_backend.repository.MaterialRepository;
import com.confetaria.confetaria_backend.service.interfaces.MaterialService;

@Service
public class MaterialServiceImpl implements MaterialService {

    private final MaterialRepository materialRepository;

    public MaterialServiceImpl(MaterialRepository materialRepository) {
        this.materialRepository = materialRepository;
    }

    public List<Material> listarMateriais() {
        return materialRepository.findAll().stream()
                .toList();
    }

    public Material salvarMaterial(Material material) {
        return materialRepository.save(material);
    }

    public Optional<Material> buscarPorId(Integer id) {
        return materialRepository.findById(id);
    }

    public Optional<Material> atualizarMaterial(Integer id, Material material) {
        return materialRepository.findById(id).map(existingMaterial -> {
            existingMaterial.setDescricao(material.getDescricao());
            existingMaterial.setQtPorcao(material.getQtPorcao());

            return materialRepository.save(existingMaterial);
        });
    }

    public boolean deletarMaterial(Integer id) {
        Optional<Material> material = materialRepository.findById(id);
        if (material.isPresent()) {
            Material mat = material.get();
            mat.setAtivo(false);
            mat.setDataExclusao(LocalDateTime.now());
            materialRepository.save(mat);
            return true;
        }
        return false;
    }

}