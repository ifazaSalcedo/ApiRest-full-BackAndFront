package com.app.datapart;

import com.app.datapart.Exepciotions.CiudadNotFoundExeption;
import com.app.datapart.data.entity.Ciudad;
import com.app.datapart.data.repository.CiudadRepository;
import com.app.datapart.data.services.CiudadFactoryService;
import com.app.datapart.data.services.CiudadService;
import com.app.datapart.dto.CiudadDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CiudadServicesTest {

    @Mock
    private CiudadRepository repository;
    @Mock CiudadFactoryService ciudadFactoryService;
    @InjectMocks
    private CiudadService ciudadService;
    private Ciudad ciudad;

    @BeforeEach
    public void push(){
        ciudad = new Ciudad();
        ciudad.setCodigo(20L);
        ciudad.setDescripcion("Asunción");
    }

    @Test
    public void textFindById(){


        when(repository.findById(20L))
                .thenReturn(Optional.of(ciudad));

        CiudadDto ciudadDtoMk= new CiudadDto();
        ciudadDtoMk.setDescripcion("Asunción");
        when(ciudadFactoryService.crearCiudadDto(ciudad)).thenReturn(ciudadDtoMk);

        CiudadDto ciudadDto= ciudadService.findById(20L);
        //Preguntar
        assertThat(ciudadDto).isNotNull();
        assertThat(ciudadDto.getDescripcion()).isEqualTo("Asunción");
    }
    @Test
    public void textFindByIdNoFount(){
        when(repository.findById(20L))
                .thenReturn(Optional.empty());

        //Lanzar exeption
        assertThatThrownBy(() -> ciudadService.findById(20L))
                .isInstanceOf(CiudadNotFoundExeption.class)
                .hasMessageContaining("Ciudad con código 20 no encotrado");
    }
}
