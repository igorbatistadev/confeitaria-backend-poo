package com.confetaria.confetaria_backend.repository;

import com.confetaria.confetaria_backend.model.PrecificacaoMaterial;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrecificacaoMaterialRepository extends JpaRepository<PrecificacaoMaterial, Integer> {
}