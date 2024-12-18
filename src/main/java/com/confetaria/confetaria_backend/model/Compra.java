package com.confetaria.confetaria_backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "TB_COMPRA")
@Data
@ToString
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CO_COMPRA")
    private Integer codigoCompra;

    @ManyToOne
    @JoinColumn(name = "CO_MATERIAL", nullable = false)
    private Material material;

    @Column(name = "DT_COMPRA", nullable = false)
    private LocalDate dataCompra;

    @Column(name = "QT_ADQUIRIDA", nullable = false)
    private Integer quantidadeAdquirida;

    @Column(name = "VL_TOTAL", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorTotal;

    @Column(name = "QT_RESTANTE", nullable = false)
    private Integer quantidadeRestante;

    @Column(name = "DT_VALIDADE", nullable = false)
    private LocalDate dataValidade;

    @Column(name = "DT_CADASTRO", nullable = false, updatable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime dataCadastro;

    @PrePersist
    private void prePersist() {
        if (this.dataCadastro == null) {
            this.dataCadastro = LocalDateTime.now();
        }
    }
}