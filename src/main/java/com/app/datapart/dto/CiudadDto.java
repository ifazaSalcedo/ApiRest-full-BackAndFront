package com.app.datapart.dto;

import com.app.datapart.data.entity.Ciudad;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class CiudadDto {
    private Long codigo;
    private String descripcion;

    public CiudadDto(Ciudad ciudad){
        this.setCodigo(ciudad.getCodigo());
        this.setDescripcion(ciudad.getDescripcion());
    }
}
