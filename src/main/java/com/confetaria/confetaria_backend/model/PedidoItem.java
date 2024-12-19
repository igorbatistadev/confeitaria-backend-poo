package com.confetaria.confetaria_backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@IdClass(PedidoItemId.class)
@Table(name = "TB_PEDIDO_ITEM")
@Data
public class PedidoItem extends EntidadeBase {

    @Id
    @ManyToOne
    @JoinColumn(name = "CO_PEDIDO", referencedColumnName = "CO_PEDIDO", nullable = false)
    @JsonBackReference
    private Pedido pedido;

    @Id
    @ManyToOne
    @JoinColumn(name = "CO_PRODUTO", referencedColumnName = "CO_PRODUTO", nullable = false)
    @JsonBackReference
    private Produto produto;

    @Column(name = "TP_PRODUTO_PEDIDO", nullable = false, length = 3)
    private Character tipoProduto;

    @Column(name = "VL_UNIT", nullable = false, precision = 10, scale = 2)
    private BigDecimal vlUnitario;

    @Column(name = "QT_PRODUTO", nullable = false)
    private Integer quantidadeProduto;
}
