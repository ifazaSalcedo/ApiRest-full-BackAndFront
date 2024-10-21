package com.app.datapart.data.entity;

import com.app.datapart.dto.PedidoDto;
import com.app.datapart.enums.EstadoPedido;
import com.app.datapart.enums.EstadoPedidoConverter;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "pedidos")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ped_cod", nullable = false)
    private Long codigo;

    @Column(name = "ped_nrocontrol")
    private Integer numeroControlPedido;

    @Column(name = "ped_fechahs", insertable = false, updatable = false)
    private LocalDateTime fechaHora;

    @ManyToOne
    @JoinColumn(name = "cli_cod", referencedColumnName = "cli_cod")
    private Cliente cliente;

    @Convert(converter = EstadoPedidoConverter.class)
    @Column(name = "ped_estado")
    //@Enumerated(EnumType.STRING)
    private EstadoPedido estadoPedido;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    @Valid
    private List<PedidoDetalle> detallePedido;
}
