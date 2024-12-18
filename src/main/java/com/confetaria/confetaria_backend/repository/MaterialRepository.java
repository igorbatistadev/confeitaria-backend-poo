package com.confetaria.confetaria_backend.repository;

import com.confetaria.confetaria_backend.model.Compra;
import com.confetaria.confetaria_backend.model.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MaterialRepository extends JpaRepository<Material, Integer> {
    @Query("SELECT m FROM Compra m WHERE m.material.codigoMaterial = :materialId AND m.quantidadeRestante > 0 ORDER BY m.dataCompra ASC")
    Compra findPrimeiraCompra(Integer materialId);
}