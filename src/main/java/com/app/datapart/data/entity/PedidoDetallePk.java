package com.app.datapart.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@Embeddable
public class PedidoDetallePk {
    private Long codigoPedido;
    private String codigoProducto;
    @Column(name = "pdet_item")
    private int item;
}
