package com.confetaria.confetaria_backend.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "TB_PAGAMENTOS")
@Data
public class Pagamentos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CO_PAGAMENTO", nullable = false)
    private Integer codigoPagamento;

    @Column(name = "CO_PEDIDO", nullable = false)
    private Integer codigoPedido;

    @Column(name = "VL_PAGAMENTO", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorPagamento;

    @Column(name = "DT_PAGAMENTO", nullable = false, updatable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime dataPagamento;

    @Column(name = "DT_EXCLUSAO")
    @JsonIgnore
    private LocalDateTime dataExclusao;

    @PrePersist
    private void prePersist() {
        if (this.dataPagamento == null) {
            this.dataPagamento = LocalDateTime.now();
        }
    }
}