package com.confetaria.confetaria_backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "TB_CLIENTE")
@Data
@EqualsAndHashCode(callSuper = false)
public class Cliente extends Usuario {

    @Column(name = "NU_CPF", nullable = true, length = 11)
    private String numeroCpf;

}
