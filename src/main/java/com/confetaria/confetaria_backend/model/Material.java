package com.confetaria.confetaria_backend.model;

import java.time.LocalDateTime;

import com.confetaria.confetaria_backend.model.enums.UnidadeMaterialConverter;
import com.confetaria.confetaria_backend.model.enums.UnidadeMedida;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "TB_MATERIAL")
@Data
@EqualsAndHashCode(callSuper = false)
public class Material extends EntidadeBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CO_MATERIAL")
    private Integer codigoMaterial;

    @Column(name = "DS_MATERIAL", nullable = false, length = 1000)
    private String descricao;

    @Column(name = "QT_PORCAO", nullable = false)
    private Integer qtPorcao;

    @Convert(converter = UnidadeMaterialConverter.class)
    @Column(name = "TP_UNIDADE_MEDIDA", nullable = false, length = 1)
    private UnidadeMedida unidadeMedida;

    @Column(name = "IE_ATIVO", nullable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Boolean ativo;

    @Override
    protected void prePersist() {
        if (this.dataCadastro == null) {
            this.dataCadastro = LocalDateTime.now();
        }
        if (this.ativo == null) {
            this.ativo = true;
        }
    }

}