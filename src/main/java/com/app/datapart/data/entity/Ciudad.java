package com.app.datapart.data.entity;

import com.app.datapart.dto.CiudadDto;
import com.app.datapart.dto.ClienteDto;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "ciudades")
public class Ciudad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ciu_cod")
    private Long codigo;
    @Column(name = "ciu_des", nullable = false, length = 50)
    private String descripcion;

    public Ciudad(CiudadDto dto) {
        this.setCodigo(dto.getCodigo());
        this.setDescripcion(dto.getDescripcion());
    }

}
