
package com.confetaria.confetaria_backend.service.interfaces;

import com.confetaria.confetaria_backend.dto.CompraRequestDTO;
import com.confetaria.confetaria_backend.model.Compra;

import java.util.List;
import java.util.Optional;

public interface CompraService {

    List<Compra> listarCompras();

    Compra salvarCompra(CompraRequestDTO compraRequest);

    Optional<Compra> buscarPorId(Integer id);
}

