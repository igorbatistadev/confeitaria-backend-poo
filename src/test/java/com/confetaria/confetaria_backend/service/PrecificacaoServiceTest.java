package com.confetaria.confetaria_backend.service;

import com.confetaria.confetaria_backend.dto.MaterialUtilizadosResquestDTO;
import com.confetaria.confetaria_backend.dto.PrecificacaoRequestDTO;
import com.confetaria.confetaria_backend.model.Compra;
import com.confetaria.confetaria_backend.model.Material;
import com.confetaria.confetaria_backend.model.Precificacao;
import com.confetaria.confetaria_backend.model.PrecificacaoMaterial;
import com.confetaria.confetaria_backend.repository.MaterialRepository;
import com.confetaria.confetaria_backend.repository.PrecificacaoMaterialRepository;
import com.confetaria.confetaria_backend.repository.PrecificacaoRepository;
import com.confetaria.confetaria_backend.service.impl.PrecificacaoServiceImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PrecificacaoServiceTest {

    @Mock
    private PrecificacaoRepository precificacaoRepository;

    @Mock
    private MaterialRepository materialRepository;

    @Mock
    private PrecificacaoMaterialRepository precificacaoMaterialRepository;

    @InjectMocks
    private PrecificacaoServiceImpl precificacaoService;

    @Test
    public void listarPrecificacoes_ListarPrecificacoesComDados() {
        Precificacao precificacao1 = new Precificacao();
        precificacao1.setCodigoPreficacao(1);
        precificacao1.setDescricao("Precificação 1");

        Precificacao precificacao2 = new Precificacao();
        precificacao2.setCodigoPreficacao(2);
        precificacao2.setDescricao("Precificação 2");

        List<Precificacao> listaPrecificacoes = List.of(precificacao1, precificacao2);

        when(precificacaoRepository.findAll()).thenReturn(listaPrecificacoes);

        List<Precificacao> resultado = precificacaoService.listarPrecificacoes();

        assertEquals(2, resultado.size(), "Deve retornar 2 precificações");
        assertEquals("Precificação 1", resultado.get(0).getDescricao(),
                "A descrição da primeira precificação deve ser 'Precificação 1'");
        assertEquals("Precificação 2", resultado.get(1).getDescricao(),
                "A descrição da segunda precificação deve ser 'Precificação 2'");

        verify(precificacaoRepository).findAll();
    }

    @Test
    public void listarPrecificacoes_ListarPrecificacoesVazia() {
        when(precificacaoRepository.findAll()).thenReturn(Collections.emptyList());

        List<Precificacao> resultado = precificacaoService.listarPrecificacoes();

        assertEquals(0, resultado.size(), "A lista de precificações deve estar vazia");

        verify(precificacaoRepository).findAll();
    }

    @Test
    public void criarPrecificacao_DeveSalvarPrecificacaoEProcessarMateriais() {
        PrecificacaoRequestDTO requestDTO = new PrecificacaoRequestDTO(
                "Precificação Teste",
                new BigDecimal("0.30"),
                List.of(new MaterialUtilizadosResquestDTO(1, 50)));

        Material materialMock = new Material();
        materialMock.setCodigoMaterial(1);
        materialMock.setQtPorcao(100);

        Compra compraMock = new Compra();
        compraMock.setQuantidadeAdquirida(100);
        compraMock.setValorTotal(new BigDecimal("200"));

        Precificacao precificacaoMock = new Precificacao();
        precificacaoMock.setCodigoPreficacao(1);

        when(precificacaoRepository.save(any(Precificacao.class))).thenReturn(precificacaoMock);
        when(precificacaoRepository.findById(1)).thenReturn(Optional.of(precificacaoMock));
        when(materialRepository.findById(1)).thenReturn(Optional.of(materialMock));
        when(materialRepository.findPrimeiraCompra(1)).thenReturn(compraMock);

        Precificacao novaPrecificacao = precificacaoService.criarPrecificacao(requestDTO);

        assertNotNull(novaPrecificacao);
        assertEquals(1, novaPrecificacao.getCodigoPreficacao());

        ArgumentCaptor<Precificacao> precificacaoCaptor = ArgumentCaptor.forClass(Precificacao.class);
        verify(precificacaoRepository).save(precificacaoCaptor.capture());

        Precificacao precificacaoSalva = precificacaoCaptor.getValue();
        assertEquals("Precificação Teste", precificacaoSalva.getDescricao());
        assertEquals(new BigDecimal("0.30"), precificacaoSalva.getMargemLucro());

        ArgumentCaptor<PrecificacaoMaterial> precificacaoMaterialCaptor = ArgumentCaptor
                .forClass(PrecificacaoMaterial.class);
        verify(precificacaoMaterialRepository).save(precificacaoMaterialCaptor.capture());

        PrecificacaoMaterial precificacaoMaterialSalvo = precificacaoMaterialCaptor.getValue();
        assertNotNull(precificacaoMaterialSalvo);
        assertEquals(new BigDecimal("1.00"), precificacaoMaterialSalvo.getCusto());
        assertEquals(50, precificacaoMaterialSalvo.getQuantidadeUtilizada());
        assertEquals(1, precificacaoMaterialSalvo.getCodigoMaterial());

        verify(materialRepository, times(1)).findById(1);
        verify(materialRepository, times(1)).findPrimeiraCompra(1);
        verify(precificacaoMaterialRepository, times(1)).save(any(PrecificacaoMaterial.class));
    }

    @Test
    public void criarPrecificacao_DeveLancarExcecao_QuandoMaterialNaoEncontrado() {
        PrecificacaoRequestDTO requestDTO = new PrecificacaoRequestDTO(
                "Precificação Teste",
                new BigDecimal("0.30"),
                List.of(new MaterialUtilizadosResquestDTO(1, 10)));

        when(materialRepository.findById(1)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            precificacaoService.criarPrecificacao(requestDTO);
        });

        assertEquals("Material não encontrado", exception.getMessage());

        verify(materialRepository, times(1)).findById(1);
        verifyNoInteractions(precificacaoMaterialRepository);
    }
}