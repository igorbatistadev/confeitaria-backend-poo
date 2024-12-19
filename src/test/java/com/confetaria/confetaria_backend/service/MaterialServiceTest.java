package com.confetaria.confetaria_backend.service;

import com.confetaria.confetaria_backend.model.Material;
import com.confetaria.confetaria_backend.repository.MaterialRepository;
import com.confetaria.confetaria_backend.service.impl.MaterialServiceImpl;
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
class MaterialServiceTest {

    @Mock
    private MaterialRepository materialRepository;

    @InjectMocks
    private MaterialServiceImpl materialService;

    @Test
    public void salvarMaterial_ComDadosValidos_RetornaMaterial() {
        Material material = new Material();
        material.setDescricao("Leite Condensado");
        when(materialRepository.save(material)).thenReturn(material);

        Material sut = materialService.salvarMaterial(material);

        assertEquals(material, sut);
    }

    @Test
    public void salvarMaterial_ComDadosInvalidos_LancarExcecao() {
        Material material = new Material();
        material.setDescricao(null);

        when(materialRepository.save(material)).thenThrow(DataIntegrityViolationException.class);

        assertThrows(DataIntegrityViolationException.class, () -> materialService.salvarMaterial(material));
    }

    @Test
    public void buscarPorId_ComIdValido_RetornarMaterial() {
        Material material = new Material();
        material.setCodigoMaterial(2);
        material.setDescricao("Leite Condensado");

        when(materialRepository.findById(material.getCodigoMaterial())).thenReturn(Optional.of(material));

        Optional<Material> materialOptional = materialService.buscarPorId(material.getCodigoMaterial());

        assertTrue(materialOptional.isPresent());
        assertEquals(material, materialOptional.get());
    }

    @Test
    public void buscarPorId_ComIdInvalido_RetornarMaterial() {
        when(materialRepository.findById(1)).thenReturn(Optional.empty());

        Optional<Material> materialOptional = materialService.buscarPorId(1);

        assertFalse(materialOptional.isPresent());
    }

    @Test
    public void listarMateriais_deveRetornarListaDeMateriais() {
        Material material1 = new Material();
        Material material2 = new Material();
        when(materialRepository.findAll()).thenReturn(Arrays.asList(material1, material2));

        List<Material> materiais = materialService.listarMateriais();

        assertNotNull(materiais);
        assertEquals(2, materiais.size());
        assertEquals(material1, materiais.get(0));
    }

    @Test
    public void listarMateriais_deveRetornarListaDeMateriaisVazia() {
        when(materialRepository.findAll()).thenReturn(Collections.emptyList());

        List<Material> materiais = materialService.listarMateriais();

        assertNotNull(materiais);
        assertTrue(materiais.isEmpty());
    }

    @Test
    public void atualizarMaterial_deveAtualizarEMaterialRetornado() {
        Material materialExistente = new Material();
        materialExistente.setCodigoMaterial(1);
        materialExistente.setDescricao("Material Antigo");

        Material materialAtualizado = new Material();
        materialAtualizado.setDescricao("Material Novo");
        materialAtualizado.setQtPorcao(10);

        when(materialRepository.findById(1)).thenReturn(Optional.of(materialExistente));
        when(materialRepository.save(materialExistente)).thenReturn(materialExistente);

        Optional<Material> resultado = materialService.atualizarMaterial(1, materialAtualizado);

        assertTrue(resultado.isPresent());
        assertEquals("Material Novo", resultado.get().getDescricao());
        assertEquals(10, resultado.get().getQtPorcao());
    }

    @Test
    void atualizarMaterial_deveRetornarOptionalVazioQuandoMaterialNaoExistir() {
        Material materialAtualizado = new Material();
        materialAtualizado.setDescricao("Material Novo");

        when(materialRepository.findById(1)).thenReturn(Optional.empty());

        Optional<Material> resultado = materialService.atualizarMaterial(1, materialAtualizado);

        assertFalse(resultado.isPresent());
    }

    @Test
    public void deletarMaterial_deveInativarMaterialQuandoExistir() {
        Material material = new Material();
        material.setCodigoMaterial(1);
        material.setAtivo(true);
        when(materialRepository.findById(1)).thenReturn(Optional.of(material));

        boolean resultado = materialService.deletarMaterial(1);

        assertTrue(resultado);
        assertFalse(material.getAtivo());
        assertNotNull(material.getDataExclusao());
    }

    @Test
    void deletarMaterial_deveRetornarFalsoQuandoMaterialNaoExistir() {
        when(materialRepository.findById(1)).thenReturn(Optional.empty());

        boolean resultado = materialService.deletarMaterial(1);

        assertFalse(resultado);
    }
}

