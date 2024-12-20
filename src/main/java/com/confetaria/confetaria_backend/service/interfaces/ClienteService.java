package com.confetaria.confetaria_backend.service.interfaces;

import com.confetaria.confetaria_backend.model.Cliente;

import java.util.List;
import java.util.Optional;

public interface ClienteService {
    List<Cliente> listarClientes();

    Cliente salvarCliente(Cliente cliente);

    Optional<Cliente> buscarPorId(Integer id);
}
