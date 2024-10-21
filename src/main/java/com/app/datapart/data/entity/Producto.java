package com.app.datapart.data.entity;

import com.app.datapart.dto.ProductoDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "productos")
public class Producto {
    @Id
    @Column(name = "prod_cod", length = 20)
    private String codigo;
    @Column(name = "prod_nombre", length = 80)
    @NotBlank(message = "Campo nombre del es requerido!")
    private String nombre;
    @NotBlank(message = "Campo descripcion del es requerido!")
    @Column(name = "prod_descrip", length = 200)
    private String descripcion;
    @NotNull(message = "Campo precio de venta es requerido!")
    @Min(value = 1,message = "Valor minimo para el campo es 1")
    @Column(name = "prod_precioven", precision = 12, scale = 2)
    private BigDecimal precioVenta;
    @Column(name = "prod_stock", precision = 12, scale = 3)
    private BigDecimal stockActual;

    public Producto(ProductoDto dto) {
        this.setCodigo(dto.getCodigo());
        this.setNombre(dto.getNombre());
        this.setDescripcion(dto.getDescripcion());
        this.setPrecioVenta(dto.getPrecioVenta());
        this.setStockActual(dto.getStockActual());
    }
}
