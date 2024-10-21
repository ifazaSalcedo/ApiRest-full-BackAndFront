package com.app.datapart.data.entity;

import com.app.datapart.dto.ClienteDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "clientes")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cli_cod")
    private Long codigo;

    //@NotBlank(message = "El campo nombre es requerido!")
    @Column(name = "cli_nombres", length = 80)
    private String nombre;

    //@NotBlank(message = "El campo numero de documento es requerido!")
    @Column(name ="cli_documento" , length = 45)
    private String documentoNro;

    //@NotNull(message = "El campo ciudad es requerido!")
    @ManyToOne
    @JoinColumn(name = "ciu_cod", referencedColumnName = "ciu_cod")
    private Ciudad ciudad;

    //@NotBlank(message = "El campo dirección es requerido!")
    @Column(name ="cli_direccion", length = 200)
    private String direccion;

    //@NotBlank(message = "El campo teléfono es requerido!")
    @Column(name ="cli_telefono", length = 45)
    private String telefono;

    //@Email(message = "Formato del email es incorrecto!")
    //@NotBlank(message = "El campo email es requerido!")
    @Column(name ="cli_email", length = 45)
    private String email;

    public Cliente(ClienteDto dto) {
        setCodigo(dto.getCodigo());
        setNombre(dto.getNombre());
        setDocumentoNro(dto.getDocumentoNro());
        setCiudad(new Ciudad(dto.getCiudad()));
        setDireccion(dto.getDireccion());
        setTelefono(dto.getTelefono());
        setEmail(dto.getEmail());
    }
}
