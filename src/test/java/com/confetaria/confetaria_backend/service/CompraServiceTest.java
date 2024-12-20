
package com.confetaria.confetaria_backend.service;

import com.confetaria.confetaria_backend.dto.CompraRequestDTO;
import com.confetaria.confetaria_backend.model.Compra;
import com.confetaria.confetaria_backend.model.Material;
import com.confetaria.confetaria_backend.repository.CompraRepository;
import com.confetaria.confetaria_backend.repository.MaterialRepository;
import com.confetaria.confetaria_backend.service.impl.CompraServiceImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CompraServiceTest {

    @Mock
    private CompraRepository compraRepository;

    @Mock
    private MaterialRepository materialRepository;

    @InjectMocks
    private CompraServiceImpl compraService;

    @Test
    void listarCompras_DeveRetornarListaDeCompras() {
        Compra compra1 = new Compra();
        Compra compra2 = new Compra();
        when(compraRepository.findAll()).thenReturn(List.of(compra1, compra2));

        List<Compra> compras = compraService.listarCompras();

        assertEquals(2, compras.size());
    }

    @Test
    public void salvarCompra_DeveSalvarCompraQuandoMaterialExiste() {
        Material material = new Material();
        material.setCodigoMaterial(1);

        CompraRequestDTO compraRequest = new CompraRequestDTO(
                1,
                LocalDate.now(),
                27,
                BigDecimal.valueOf(283.99),
                LocalDate.now().plusDays(30));

        when(materialRepository.findById(1)).thenReturn(Optional.of(material));
        ArgumentCaptor<Compra> compraCaptor = ArgumentCaptor.forClass(Compra.class);

        compraService.salvarCompra(compraRequest);

        verify(compraRepository).save(compraCaptor.capture());

        Compra compraSalva = compraCaptor.getValue();
        assertNotNull(compraSalva);
        assertEquals(material, compraSalva.getMaterial());
        assertEquals(compraRequest.quantidadeAdquirida(), compraSalva.getQuantidadeAdquirida());
        assertEquals(compraRequest.valorTotal(), compraSalva.getValorTotal());
        assertEquals(compraRequest.dataValidade(), compraSalva.getDataValidade());
        assertEquals(compraSalva.getQuantidadeRestante(), compraRequest.quantidadeAdquirida());
    }

    @Test
    void salvarCompra_DeveLancarExcecaoQuandoMaterialNaoExiste() {
        CompraRequestDTO compraRequest = new CompraRequestDTO(
                1,
                LocalDate.now(),
                27,
                BigDecimal.valueOf(283.99),
                LocalDate.now().plusDays(30));

        when(materialRepository.findById(1)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            compraService.salvarCompra(compraRequest);
        });

        assertEquals("Material não encontrado", exception.getMessage());
    }

    @Test
    public void buscarPorId_DeveRetornarCompraQuandoExistir() {
        Compra compra = new Compra();
        compra.setCodigoCompra(1);
        when(compraRepository.findById(1)).thenReturn(Optional.of(compra));

        Optional<Compra> resultado = compraService.buscarPorId(1);

        assertTrue(resultado.isPresent());
        assertEquals(1, resultado.get().getCodigoCompra());
    }

    @Test
    void buscarPorId_DeveRetornarVazioQuandoNaoExistir() {
        when(compraRepository.findById(1)).thenReturn(Optional.empty());

        Optional<Compra> resultado = compraService.buscarPorId(1);

        assertFalse(resultado.isPresent());
    }
}
