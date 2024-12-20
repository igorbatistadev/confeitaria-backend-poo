package com.confetaria.confetaria_backend.repository;

import com.confetaria.confetaria_backend.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
}
