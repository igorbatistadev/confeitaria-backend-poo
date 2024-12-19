package com.confetaria.confetaria_backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "TB_PRODUTO")
@Data
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CO_PRODUTO")
    private Integer codigoProduto;

    @Column(name = "DS_PRODUTO", nullable = false, length = 1000)
    private String descricao;

    @Column(name = "VALOR_UNIT", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorUnitario;

    @ManyToOne()
    @JoinColumn(name = "CO_PRECIFICACAO", nullable = false)
    private Precificacao precificacao;

    @Column(name = "DT_CADASTRO", nullable = false, updatable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime dataCadastro;

    @Column(name = "DT_EXCLUSAO")
    @JsonIgnore
    private LocalDateTime dataExclusao;

    @Column(name = "IE_ATIVO", nullable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Boolean ativo;

    @PrePersist
    private void prePersist() {
        if (this.dataCadastro == null) {
            this.dataCadastro = LocalDateTime.now();
        }
        if (this.ativo == null) {
            this.ativo = true;
        }
    }
}
