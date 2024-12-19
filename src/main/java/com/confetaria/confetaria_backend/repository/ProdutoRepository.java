package com.confetaria.confetaria_backend.repository;

import com.confetaria.confetaria_backend.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
}
