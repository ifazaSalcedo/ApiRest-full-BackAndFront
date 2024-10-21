package com.app.datapart.dto;

import com.app.datapart.enums.EstadoPedido;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PedidoDto {
    private Long codigo;
    private Integer numeroControlPedido;
    private LocalDateTime fechaHora;
    @NotNull(message = "Cliente es requerido")
    private Long clienteId;
    @NotBlank(message = "Estado del pedido es requerido")
    private String estadoPedido;
    @Size(min = 1, message = "El pedido no tiene item de productos")
    private List<PedidoDetalleDto> detallePedidosDto = new ArrayList<>();
}
