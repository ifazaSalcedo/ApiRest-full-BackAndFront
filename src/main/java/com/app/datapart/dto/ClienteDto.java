package com.app.datapart.dto;

import com.app.datapart.data.entity.Ciudad;
import com.app.datapart.data.entity.Cliente;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ClienteDto {
    private Long codigo;
    @NotBlank(message = "Nombre es requerido!")
    private String nombre;
    @NotBlank(message = "Documento de identidad es requerido!")
    private String documentoNro;
    @NotNull(message = "Ciudad es requerido!")
    private CiudadDto ciudad;
    @NotBlank(message = "Dirección es requerido!")
    private String direccion;
    @NotBlank(message = "Teléfono es requerido!")
    private String telefono;
    @NotBlank(message = "Email es requerido!")
    @Email(message = "Email invalido!")
    private String email;

    public ClienteDto(Cliente entity) {
        this.setCodigo(entity.getCodigo());
        this.setNombre(entity.getNombre());
        this.setDocumentoNro(entity.getDocumentoNro());
        this.setCiudad(new CiudadDto(entity.getCiudad()));
        this.setDireccion(entity.getDireccion());
        this.setTelefono(entity.getTelefono());
        this.setEmail(entity.getEmail());
    }
}
