package com.app.datapart.data.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "pedidos_det")
public class PedidoDetalle {
    @EmbeddedId
    private PedidoDetallePk detallePk;

    @ManyToOne
    @JoinColumn(name = "ped_cod", referencedColumnName = "ped_cod")
    @MapsId("codigoPedido")
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "prod_cod", referencedColumnName = "prod_cod")
    @MapsId("codigoProducto")
    private Producto producto;

    @Column(name = "pdet_can", precision = 12, scale = 3)
    private BigDecimal cantidad;

    @Column(name = "pdet_preciouni", precision = 12, scale = 2)
    private BigDecimal precioUnitario;

    @Column(name = "pdet_subtotal", precision = 12, scale = 2)
    private BigDecimal subTotalItem;


}
