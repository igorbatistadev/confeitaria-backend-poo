package com.confetaria.confetaria_backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "TB_PRECIFICACAO")
@Data
public class Precificacao extends EntidadeBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CO_PRECIFICACAO", nullable = false)
    private Integer codigoPreficacao;

    @Column(name = "DS_PRECIFICACAO", nullable = false, length = 1000)
    private String descricao;

    @Column(name = "NU_MARGEM_LUCRO", nullable = false)
    private BigDecimal margemLucro;

    @OneToMany(mappedBy = "precificacao")
    @JsonManagedReference
    private List<PrecificacaoMaterial> listaMateriais;

    public BigDecimal getValorCustoTotal() {
        return this.listaMateriais == null
                ? BigDecimal.ZERO
                : this.listaMateriais
                .stream()
                .map(PrecificacaoMaterial::getCusto)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getValorVendaSugerido() {
        return BigDecimal.ONE.add(this.margemLucro).multiply(this.getValorCustoTotal()).setScale(2, RoundingMode.HALF_UP);
    }
}