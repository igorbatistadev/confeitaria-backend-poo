package com.confetaria.confetaria_backend.service.impl;

import com.confetaria.confetaria_backend.dto.MaterialUtilizadosResquestDTO;
import com.confetaria.confetaria_backend.dto.PrecificacaoRequestDTO;
import com.confetaria.confetaria_backend.model.*;
import com.confetaria.confetaria_backend.repository.MaterialRepository;
import com.confetaria.confetaria_backend.repository.PrecificacaoMaterialRepository;
import com.confetaria.confetaria_backend.repository.PrecificacaoRepository;
import com.confetaria.confetaria_backend.service.interfaces.PrecificacaoService;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class PrecificacaoServiceImpl implements PrecificacaoService {

        private final PrecificacaoRepository precificacaoRepository;
        private final MaterialRepository materialRepository;
        private final PrecificacaoMaterialRepository precificacaoMaterialRepository;

        public PrecificacaoServiceImpl(PrecificacaoRepository precificacaoRepository,
                        MaterialRepository materialRepository,
                        PrecificacaoMaterialRepository precificacaoMaterialRepository) {
                this.precificacaoRepository = precificacaoRepository;
                this.materialRepository = materialRepository;
                this.precificacaoMaterialRepository = precificacaoMaterialRepository;
        }

        public List<Precificacao> listarPrecificacoes() {
                return precificacaoRepository.findAll().stream()
                                .toList();
        }

        public Precificacao criarPrecificacao(PrecificacaoRequestDTO precificacaoRequestDTO) {
                BigDecimal margemLucro = precificacaoRequestDTO.margemLucro() == null ? new BigDecimal("0.20")
                                : precificacaoRequestDTO.margemLucro();

                Precificacao precificacao = new Precificacao();
                precificacao.setDescricao(precificacaoRequestDTO.descricao());
                precificacao.setMargemLucro(margemLucro);

                Precificacao novaPrecificacao = precificacaoRepository.save(precificacao);

                this.processarMateriaisUtilizados(precificacaoRequestDTO, novaPrecificacao);

                return precificacaoRepository.findById(novaPrecificacao.getCodigoPreficacao())
                                .orElseThrow(() -> new RuntimeException("Precificação não encontrada"));
        }

        private void processarMateriaisUtilizados(PrecificacaoRequestDTO precificacaoRequestDTO,
                        Precificacao novaPrecificacao) {
                for (MaterialUtilizadosResquestDTO materialRequest : precificacaoRequestDTO.materiaisUtilizados()) {
                        Material material = materialRepository.findById(materialRequest.materialId())
                                        .orElseThrow(() -> new RuntimeException("Material não encontrado"));

                        Compra materialCompra = materialRepository.findPrimeiraCompra(material.getCodigoMaterial());

                        // Calcular o custo com base na compra mais antiga
                        BigDecimal custoMaterial = materialCompra.getValorTotal()
                                        .divide(new BigDecimal(materialCompra.getQuantidadeAdquirida()), 4,
                                                        RoundingMode.HALF_UP)
                                        .divide(BigDecimal.valueOf(material.getQtPorcao()), 4, RoundingMode.HALF_UP);

                        BigDecimal quantidadeUtilizada = new BigDecimal(materialRequest.quatidadeUtilizada());

                        BigDecimal custoMaterialTotal = custoMaterial.multiply(quantidadeUtilizada)
                                        .setScale(2, RoundingMode.HALF_UP);

                        PrecificacaoMaterial precificacaoMaterial = new PrecificacaoMaterial();
                        precificacaoMaterial.setPrecificacao(novaPrecificacao);
                        precificacaoMaterial.setCodigoMaterial(material.getCodigoMaterial());
                        precificacaoMaterial.setQuantidadeUtilizada(materialRequest.quatidadeUtilizada());
                        precificacaoMaterial.setCusto(custoMaterialTotal);
                        precificacaoMaterialRepository.save(precificacaoMaterial);
                }
        }

}