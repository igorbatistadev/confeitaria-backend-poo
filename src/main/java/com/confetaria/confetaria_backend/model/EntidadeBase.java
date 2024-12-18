package com.confetaria.confetaria_backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import lombok.Data;

import java.time.LocalDateTime;

@MappedSuperclass
@Data
public abstract class EntidadeBase {

    @Column(name = "DT_CADASTRO", nullable = false, updatable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    protected LocalDateTime dataCadastro;

    @Column(name = "DT_EXCLUSAO")
    @JsonIgnore
    protected LocalDateTime dataExclusao;

    @PrePersist
    protected void prePersist() {
        this.dataCadastro = LocalDateTime.now();
    }

}