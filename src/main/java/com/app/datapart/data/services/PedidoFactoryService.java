package com.app.datapart.data.services;

import com.app.datapart.Exepciotions.ProductoNotFoundExeption;
import com.app.datapart.data.entity.Pedido;
import com.app.datapart.data.entity.PedidoDetalle;
import com.app.datapart.data.entity.PedidoDetallePk;
import com.app.datapart.data.entity.Producto;
import com.app.datapart.data.repository.ProductoRepository;
import com.app.datapart.dto.PedidoDetalleDto;
import com.app.datapart.dto.PedidoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoFactoryService {
    private final ProductoRepository productoRepository;
    public PedidoDetalle crearPedidoDetalleEntity(PedidoDetalleDto pedidoDetalleDto, Pedido pedidoEntity) {

        PedidoDetalle pedidoDetalleEntity= new PedidoDetalle();
        pedidoDetalleEntity.setPedido(pedidoEntity);

        PedidoDetallePk pk = new PedidoDetallePk();
        pk.setItem(pedidoDetalleDto.getItem());
        //Asignar pk detalle
        pedidoDetalleEntity.setDetallePk(pk);

        Producto productoEntity = productoRepository.findById(pedidoDetalleDto.getCodigoProducto())
                .orElseThrow(() -> new ProductoNotFoundExeption("Producto " + pedidoDetalleDto.getCodigoProducto() + " no existe"));

        pedidoDetalleEntity.setProducto(productoEntity);
        pedidoDetalleEntity.setCantidad(pedidoDetalleDto.getCantidad());
        pedidoDetalleEntity.setPrecioUnitario(pedidoDetalleDto.getPrecioUnitario());
        pedidoDetalleEntity.setSubTotalItem(pedidoDetalleDto.getSubTotalItem());

        return pedidoDetalleEntity;

    }

    public PedidoDto crearPedidoDto(Pedido entity) {
        PedidoDto pedidoDto= new PedidoDto();
        pedidoDto.setCodigo(entity.getCodigo());
        pedidoDto.setNumeroControlPedido(entity.getNumeroControlPedido());
        pedidoDto.setFechaHora(entity.getFechaHora());
        pedidoDto.setClienteId(entity.getCliente().getCodigo());
        pedidoDto.setEstadoPedido(entity.getEstadoPedido().getCodigo());
        pedidoDto.setDetallePedidosDto(entity.getDetallePedido()
                .stream().map(PedidoDetalleDto::new).collect(Collectors.toList()));
        return pedidoDto;
    }
}
