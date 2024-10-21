package com.app.datapart;

import com.app.datapart.Exepciotions.ClienteNotFoundExeption;
import com.app.datapart.data.entity.Ciudad;
import com.app.datapart.data.entity.Cliente;
import com.app.datapart.data.repository.ClienteRepository;
import com.app.datapart.data.services.ClienteFactoryService;
import com.app.datapart.data.services.ClienteService;
import com.app.datapart.dto.CiudadDto;
import com.app.datapart.dto.ClienteDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClienteServicesTest {
    @Mock
    private ClienteRepository clienteRepository;
    @Mock
    private ClienteFactoryService clienteFactoryService;
    @InjectMocks
    private ClienteService clienteService;

    private Ciudad ciudad;
    private CiudadDto ciudadDto;
    private Cliente cliente;
    private ClienteDto clienteDto;


    @BeforeEach
    public void push(){
        ciudad= new Ciudad();
        ciudad.setCodigo(1L);
        ciudad.setDescripcion("ASUNCIÓN");

        ciudadDto= new CiudadDto(ciudad);

        clienteDto = new ClienteDto();
        clienteDto.setCodigo(1L);
        clienteDto.setNombre("Pedro Pica Piedras");
        clienteDto.setDocumentoNro("900");
        clienteDto.setCiudad(new CiudadDto(ciudad));
        clienteDto.setDireccion("25 de Agosto");
        clienteDto.setTelefono("0981-300");
        clienteDto.setEmail("pedro_pica@piedras.com");

        cliente= new Cliente();
        cliente.setCodigo(clienteDto.getCodigo());
        cliente.setNombre(clienteDto.getNombre());
        cliente.setDocumentoNro(clienteDto.getDocumentoNro());
        cliente.setCiudad(new Ciudad(clienteDto.getCiudad()));
        cliente.setDireccion(clienteDto.getDireccion());
        cliente.setTelefono(clienteDto.getTelefono());
        cliente.setEmail(clienteDto.getTelefono());

    }
    @Test
    public void testSave(){

        when(clienteRepository.findByEmail(clienteDto.getEmail())).thenReturn(Optional.empty());
        when(clienteRepository.findByDocumentoNro(clienteDto.getDocumentoNro())).thenReturn(Optional.empty());
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);
        when(clienteFactoryService.crearClienteDto(cliente)).thenReturn(clienteDto);
        when(clienteFactoryService.crearClienteEntity(clienteDto)).thenReturn(cliente);

        ClienteDto clienteDtoSave = clienteService.save(clienteDto);

        assertThat(clienteDtoSave).isNotNull();
        assertThat(clienteDtoSave.getCodigo()).isEqualTo(cliente.getCodigo());
        assertThat(clienteDtoSave.getCiudad().getCodigo()).isEqualTo(cliente.getCiudad().getCodigo());

        verify(clienteRepository, times(1)).save(any(Cliente.class));

    }

    @Test
    public void testSaveVerificarEmail(){
        when(clienteRepository.findByEmail(clienteDto.getEmail())).thenReturn(Optional.of(cliente));

        assertThatThrownBy(() -> clienteService.save(clienteDto))
                .isInstanceOf(ClienteNotFoundExeption.class)
                        .hasMessageContaining("El email "+clienteDto.getEmail() + " ya se encuentra registrado!");

    }

    @Test
    public void testUpdate(){
        when(clienteRepository.findById(clienteDto.getCodigo())).thenReturn(Optional.of(cliente));
        when(clienteRepository.save(cliente)).thenReturn(cliente);
        when(clienteFactoryService.crearClienteDto(cliente)).thenReturn(clienteDto);
        when(clienteFactoryService.crearClienteEntity(clienteDto)).thenReturn(cliente);

        ClienteDto clienteDtoSave = clienteService.update(clienteDto);

        assertThat(clienteDtoSave).isNotNull();
        assertThat(clienteDtoSave.getCodigo()).isEqualTo(cliente.getCodigo());

        verify(clienteRepository, times(1)).save(cliente);
    }
}
