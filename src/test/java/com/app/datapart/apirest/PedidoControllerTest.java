package com.app.datapart.apirest;

import com.app.datapart.apirest.controller.PedidoController;
import com.app.datapart.data.services.PedidoService;
import com.app.datapart.dto.PedidoDetalleDto;
import com.app.datapart.dto.PedidoDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PedidoController.class)
public class PedidoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    PedidoService pedidoService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void saveRest() throws Exception{

        PedidoDto pedidoDto = new PedidoDto();
        //pedidoDto.setCodigo(1L); // EL id es auto generado
        //pedidoDto.setFechaHora(LocalDateTime.now()); //Porque la base de datos inserta de forma automatica
        pedidoDto.setClienteId(101L);
        pedidoDto.setEstadoPedido("P");

        List<PedidoDetalleDto> detallePedidos = new ArrayList<>();
        PedidoDetalleDto detalleDto = new PedidoDetalleDto();
        detalleDto.setCodigoProducto("P001");
        detalleDto.setCantidad(BigDecimal.valueOf(5));
        detalleDto.setPrecioUnitario(BigDecimal.valueOf(15000));
        detallePedidos.add(detalleDto);

        pedidoDto.setDetallePedidosDto(detallePedidos);

        when(pedidoService.save(any(PedidoDto.class))).thenReturn(pedidoDto);

        mockMvc.perform(post("/app/web/rest/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pedidoDto)))
                .andExpect(status().isCreated());

    }


    /**
     * PROBAMOS PARA CUANDO EL END POINT RECIBA CIENTOS DE SOLICITUDES INSTANTANEAS
     * */
    @RepeatedTest(2000) /*Numero de solicitud a recibir*/
    public void saveRestRepit() throws Exception{

        PedidoDto pedidoDto = new PedidoDto();
        pedidoDto.setCodigo(1L); // EL id es auto generado
        //pedidoDto.setFechaHora(LocalDateTime.now()); //Porque la base de datos inserta de forma automatica
        pedidoDto.setClienteId(101L);
        pedidoDto.setEstadoPedido("P");

        List<PedidoDetalleDto> detallePedidos = new ArrayList<>();
        PedidoDetalleDto detalleDto = new PedidoDetalleDto();
        detalleDto.setCodigoProducto("P001");
        detalleDto.setCantidad(BigDecimal.valueOf(5));
        detalleDto.setPrecioUnitario(BigDecimal.valueOf(15000));
        detallePedidos.add(detalleDto);

        pedidoDto.setDetallePedidosDto(detallePedidos);

        when(pedidoService.save(any(PedidoDto.class))).thenReturn(pedidoDto);

        mockMvc.perform(post("/app/web/rest/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pedidoDto)))
                .andExpect(status().isCreated());

    }

}
