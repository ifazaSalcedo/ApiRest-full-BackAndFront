package com.app.datapart.dto;

import com.app.datapart.data.entity.Producto;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class ProductoDto {

    private String codigo;
    @NotBlank(message = "Nombre del producto es requerido!")
    private String nombre;
    @NotBlank(message = "Descripcion del producto es requerido!")
    private String descripcion;
    @NotNull(message = "Precio de venta es requerido")
    private BigDecimal precioVenta;
    @NotNull(message = "Stock actual es requerido")
    @Min(value = 1, message = "Stock actual debe ser mayor a cero")
    private BigDecimal stockActual;

    public ProductoDto(Producto entity) {
        this.setCodigo(entity.getCodigo());
        this.setNombre(entity.getNombre());
        this.setDescripcion(entity.getDescripcion());
        this.setPrecioVenta(entity.getPrecioVenta());
        this.setStockActual(entity.getStockActual());
    }
}
