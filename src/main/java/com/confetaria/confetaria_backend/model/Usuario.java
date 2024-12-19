package com.confetaria.confetaria_backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "TB_USUARIO")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
public class Usuario extends EntidadeBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CO_USUARIO", nullable = false)
    private Integer codigoUsuario;

    @Column(name = "NO_USUARIO", nullable = false, length = 256)
    private String nomeUsuario;

    @Column(name = "DS_EMAIL", nullable = false, length = 1000)
    private String email;

}
