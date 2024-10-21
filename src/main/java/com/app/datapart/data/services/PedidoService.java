package com.app.datapart.data.services;

import com.app.datapart.Exepciotions.ClienteNotFoundExeption;
import com.app.datapart.data.entity.Cliente;
import com.app.datapart.data.entity.Pedido;
import com.app.datapart.data.entity.PedidoDetalle;
import com.app.datapart.data.repository.ClienteRepository;
import com.app.datapart.data.repository.PedidoRepository;
import com.app.datapart.dto.PedidoDto;
import com.app.datapart.enums.EstadoPedido;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoService implements PedidoInterfaceService{
    private final PedidoRepository pedidoRepository;
    private final PedidoFactoryService pedidoFactoryService;
    private final ClienteRepository clienteRepository;
    @Transactional
    @Override
    public PedidoDto save(PedidoDto pedidoDto) {
        Pedido pedidoEntity = new Pedido();

        pedidoEntity.setNumeroControlPedido(controlNumberPedidoInter());

        //Asignar estado pedido
        EstadoPedido estadoPedidoEnum= Arrays.stream(EstadoPedido.values())
                        .filter(e -> e.getCodigo().equals(pedidoDto.getEstadoPedido()))
                                .findFirst()
                                        .orElseThrow(() -> new IllegalArgumentException("Código "+pedidoDto.getEstadoPedido()+" desconocido para el estado de Pedido "));
        pedidoEntity.setEstadoPedido(estadoPedidoEnum);

        //Asignar cliente
        Cliente clienteEntity= clienteRepository.findById(pedidoDto.getClienteId())
                .orElseThrow(() -> new ClienteNotFoundExeption("Cliente código " + pedidoDto.getClienteId() +" no existe"));
        pedidoEntity.setCliente(clienteEntity);

        //Asignar detalle de pedido
        List<PedidoDetalle> detallePedidoEntity= pedidoDto.getDetallePedidosDto().stream()
                .map(pedidoDetalleDto -> pedidoFactoryService.crearPedidoDetalleEntity(pedidoDetalleDto, pedidoEntity))
                .toList();

        pedidoEntity.setDetallePedido(detallePedidoEntity);



        Pedido pedidoSave = pedidoRepository.save(pedidoEntity);

        return pedidoFactoryService.crearPedidoDto(pedidoSave);
    }

    @Override
    public Integer controlNumberPedidoInter() {
        return pedidoRepository.controlNumerPedido();
    }


}
