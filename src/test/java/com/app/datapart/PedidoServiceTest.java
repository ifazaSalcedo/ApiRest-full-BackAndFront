package com.app.datapart;

import com.app.datapart.data.entity.Ciudad;
import com.app.datapart.data.entity.Cliente;
import com.app.datapart.data.entity.Pedido;
import com.app.datapart.data.entity.PedidoDetalle;
import com.app.datapart.data.repository.ClienteRepository;
import com.app.datapart.data.repository.PedidoRepository;
import com.app.datapart.data.services.PedidoFactoryService;
import com.app.datapart.data.services.PedidoService;
import com.app.datapart.dto.PedidoDetalleDto;
import com.app.datapart.dto.PedidoDto;
import com.app.datapart.enums.EstadoPedido;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PedidoServiceTest {

    private PedidoDto pedidoDto;
    private Cliente cliente;
    private Ciudad ciudad;
    private Pedido pedido;

    @InjectMocks
    //@MockBean
    private PedidoService pedidoService;
    @Mock
    private PedidoRepository pedidoRepository;
    @Mock
    private ClienteRepository clienteRepository;
    @Mock
    private PedidoFactoryService pedidoFactoryService;

    @BeforeEach
    public void push() {
        //MockitoAnnotations.openMocks(this);

        // Crear un cliente de prueba
        // Crear un objeto Ciudad de prueba
        ciudad = new Ciudad();
        ciudad.setCodigo(1L);
        ciudad.setDescripcion("Asunción");

        cliente = new Cliente();
        cliente.setCodigo(1L);
        cliente.setNombre("Cliente prueba");
        cliente.setCiudad(ciudad);

        // Crear un Pedido de prueba
        pedido = new Pedido();
        pedido.setCodigo(1L);
        pedido.setCliente(cliente);
        pedido.setEstadoPedido(EstadoPedido.PENDIENTE);


        pedidoDto = new PedidoDto();
        pedidoDto.setEstadoPedido("P");
        pedidoDto.setClienteId(1L);

        PedidoDetalleDto detalleDto = new PedidoDetalleDto();
        detalleDto.setCodigoProducto("A1");
        detalleDto.setCantidad(BigDecimal.valueOf(10));
        detalleDto.setPrecioUnitario(BigDecimal.valueOf(50.00));

        pedidoDto.setDetallePedidosDto(Collections.singletonList(detalleDto));



    }

    @Test
    public void testAddPedido() {

        //Rayos y cenellas este cliente
        assertEquals(1L, pedidoDto.getClienteId());

        when(clienteRepository.findById(pedidoDto.getClienteId())).thenReturn(Optional.of(cliente));
        //when(clienteRepository.findById(anyLong())).thenReturn(Optional.of(cliente));
        when(pedidoFactoryService.crearPedidoDetalleEntity(any(), any())).thenReturn(new PedidoDetalle());
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedido);
        when(pedidoFactoryService.crearPedidoDto(any(Pedido.class))).thenReturn(pedidoDto);


        PedidoDto result = pedidoService.save(pedidoDto);

        // Verificar resultados

        assertNotNull(result);
        assertEquals(pedidoDto.getEstadoPedido(), result.getEstadoPedido());
        assertEquals(pedidoDto.getClienteId(), result.getClienteId());

        verify(clienteRepository, times(1)).findById(pedidoDto.getClienteId());
        verify(pedidoRepository, times(1)).save(any(Pedido.class));
        verify(pedidoFactoryService, times(1)).crearPedidoDto(any(Pedido.class));
    }

    @Test
    public void testFindClienteById() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        Optional<Cliente> clienteResult = clienteRepository.findById(1L);

        assertNotNull(clienteResult.orElse(null));
        assertEquals(1L, clienteResult.get().getCodigo());
        assertEquals("Cliente prueba", clienteResult.get().getNombre());
    }

    @Test
    public void testOverflow() {
        // Simular miles de pedidos
        int cantidadPedidos = 10000;
        List<PedidoDto> pedidosDtos = new ArrayList<>();

        for (int i = 0; i < cantidadPedidos; i++) {
            PedidoDto pedidoDto = new PedidoDto();
            pedidoDto.setEstadoPedido("P");
            pedidoDto.setClienteId(1L);

            PedidoDetalleDto detalleDto = new PedidoDetalleDto();
            detalleDto.setCodigoProducto("A" + (i + 1));
            detalleDto.setCantidad(BigDecimal.valueOf(10 + i));
            detalleDto.setPrecioUnitario(BigDecimal.valueOf(50.00 + i));

            pedidoDto.setDetallePedidosDto(Collections.singletonList(detalleDto));

            pedidosDtos.add(pedidoDto);
        }

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedido);
        when(pedidoFactoryService.crearPedidoDetalleEntity(any(), any())).thenReturn(new PedidoDetalle());
        when(pedidoFactoryService.crearPedidoDto(any(Pedido.class))).thenReturn(pedidoDto);


        for (PedidoDto dto : pedidosDtos) {
            PedidoDto result = pedidoService.save(dto);
            assertNotNull(result);
        }

        verify(clienteRepository, times(cantidadPedidos)).findById(1L);
        verify(pedidoRepository, times(cantidadPedidos)).save(any(Pedido.class));
        verify(pedidoFactoryService, times(cantidadPedidos)).crearPedidoDto(any(Pedido.class));
    }


}
