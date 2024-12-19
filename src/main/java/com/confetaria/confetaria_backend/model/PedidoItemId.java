package com.confetaria.confetaria_backend.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Embeddable
@Data
public class PedidoItemId implements Serializable {
    private Integer pedido;
    private Integer produto;
}
