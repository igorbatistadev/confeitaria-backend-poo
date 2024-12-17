package com.confetaria.confetaria_backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "TB_PRECIFICACAO_MATERIAL")
@Data
@EqualsAndHashCode(callSuper = false)
public class PrecificacaoMaterial extends EntidadeBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CO_PRECIFICACAO_MATERIAL", nullable = false)
    private Integer codigoPrecificacaoMaterial;

    @Column(name = "CO_MATERIAL")
    private Integer codigoMaterial;

    @ManyToOne
    @JoinColumn(name = "CO_PRECIFICACAO", referencedColumnName = "CO_PRECIFICACAO")
    @JsonBackReference
    private Precificacao precificacao;

    @Column(name = "VL_CUSTO", nullable = false, precision = 10, scale = 2)
    private BigDecimal custo;

    @Column(name = "QT_PORCAO_UTILIZADA", nullable = false)
    private Integer quantidadeUtilizada;

}