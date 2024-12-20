package com.confetaria.confetaria_backend.service;

import com.confetaria.confetaria_backend.model.Cliente;
import com.confetaria.confetaria_backend.model.Material;
import com.confetaria.confetaria_backend.repository.ClienteRepository;
import com.confetaria.confetaria_backend.service.impl.ClienteServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteServiceImpl clienteService;

    @Test
    public void salvarCliente_ComDadosValidos_RetornaCliente() {
        Cliente cliente = new Cliente();
        cliente.setNomeUsuario("Igor");
        cliente.setEmail("igor@email.com");

        when(clienteRepository.save(cliente)).thenReturn(cliente);

        Cliente sut = clienteService.salvarCliente(cliente);

        assertEquals(cliente, sut);
    }

    @Test
    public void salvarCliente_ComDadosInvalidos_LancarExcecao() {
        Cliente cliente = new Cliente();
        cliente.setNomeUsuario(null);

        when(clienteRepository.save(cliente)).thenThrow(DataIntegrityViolationException.class);

        assertThrows(DataIntegrityViolationException.class, () -> clienteService.salvarCliente(cliente));
    }

    @Test
    public void buscarPorId_ComIdValido_RetornarCliente() {
        Cliente cliente = new Cliente();
        cliente.setCodigoUsuario(1);
        cliente.setNomeUsuario("Igor");

        when(clienteRepository.findById(cliente.getCodigoUsuario())).thenReturn(Optional.of(cliente));

        Optional<Cliente> clienteOptional = clienteService.buscarPorId(cliente.getCodigoUsuario());

        assertTrue(clienteOptional.isPresent());
        assertEquals(cliente, clienteOptional.get());
    }

    @Test
    public void buscarPorId_ComIdInvalido_RetornarFalse() {
        when(clienteRepository.findById(1)).thenReturn(Optional.empty());

        Optional<Cliente> clienteOptional = clienteService.buscarPorId(1);

        assertFalse(clienteOptional.isPresent());
    }

    @Test
    public void listarClientes_deveRetornarListaDeClientes() {
        Cliente cliente1 = new Cliente();
        Cliente cliente2 = new Cliente();
        when(clienteRepository.findAll()).thenReturn(Arrays.asList(cliente1, cliente2));

        List<Cliente> clientes = clienteService.listarClientes();

        assertNotNull(clientes);
        assertEquals(2, clientes.size());
        assertEquals(cliente1, clientes.get(0));
    }

    @Test
    public void listarClientes_deveRetornarListaDeClientesVazia() {
        when(clienteRepository.findAll()).thenReturn(Collections.emptyList());

        List<Cliente> clientes = clienteService.listarClientes();

        assertNotNull(clientes);
        assertTrue(clientes.isEmpty());
    }
}

