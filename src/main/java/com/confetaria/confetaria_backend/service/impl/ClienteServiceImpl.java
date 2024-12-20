package com.confetaria.confetaria_backend.service.impl;

import com.confetaria.confetaria_backend.model.Cliente;
import com.confetaria.confetaria_backend.repository.ClienteRepository;
import com.confetaria.confetaria_backend.service.interfaces.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public List<Cliente> listarClientes() {
        return clienteRepository.findAll()
                .stream().toList();
    }

    @Override
    public Cliente salvarCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @Override
    public Optional<Cliente> buscarPorId(Integer id) {
        return clienteRepository.findById(id);
    }
}
