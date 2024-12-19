package com.confetaria.confetaria_backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "TB_PEDIDO")
@Data
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CO_PEDIDO", nullable = false)
    private Integer codigoPedido;

    @Column(name = "VL_TOTAL", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorTotal;

    @Column(name = "ST_PEDIDO", nullable = false, length = 3)
    private Character statusPedido;

    @Column(name = "DS_PEDIDO", nullable = false, length = 1000)
    private String descricaoPedido;

    @ManyToOne()
    @JoinColumn(name = "CO_CLIENTE", nullable = false)
    private Cliente cliente;

    @OneToMany(mappedBy = "pedido")
    @JsonManagedReference
    private List<PedidoItem> listaItens;

    @Column(name = "DT_PEDIDO", nullable = false, updatable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime dataPedido;

    @Column(name = "DT_EXCLUSAO")
    @JsonIgnore
    private LocalDateTime dataExclusao;

    @PrePersist
    private void prePersist() {
        if (this.dataPedido == null) {
            this.dataPedido = LocalDateTime.now();
        }
    }
}
