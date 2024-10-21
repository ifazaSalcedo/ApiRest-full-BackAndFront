package com.app.datapart.dto;

import com.app.datapart.data.entity.PedidoDetalle;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
@NoArgsConstructor
public class PedidoDetalleDto {
    private Long codigoPedido;
    private String codigoProducto;
    private int item;
    private BigDecimal cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal subTotalItem;

    public PedidoDetalleDto(PedidoDetalle pedidoDetalle) {
        setCodigoPedido(pedidoDetalle.getDetallePk().getCodigoPedido());
        setCodigoProducto(pedidoDetalle.getDetallePk().getCodigoProducto());
        setItem(pedidoDetalle.getDetallePk().getItem());
        setCantidad(pedidoDetalle.getCantidad());
        setPrecioUnitario(pedidoDetalle.getPrecioUnitario());
        setSubTotalItem(pedidoDetalle.getSubTotalItem());
    }
}
