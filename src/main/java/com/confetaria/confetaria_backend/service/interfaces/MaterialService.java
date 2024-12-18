package com.confetaria.confetaria_backend.service.interfaces;

import com.confetaria.confetaria_backend.model.Material;
import java.util.List;
import java.util.Optional;

public interface MaterialService {
    List<Material> listarMateriais();

    Material salvarMaterial(Material material);

    Optional<Material> buscarPorId(Integer id);

    Optional<Material> atualizarMaterial(Integer id, Material material);

    boolean deletarMaterial(Integer id);

}