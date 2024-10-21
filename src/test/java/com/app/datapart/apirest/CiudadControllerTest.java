package com.app.datapart.apirest;

import com.app.datapart.apirest.controller.CiudadController;
import com.app.datapart.data.services.CiudadService;
import com.app.datapart.dto.CiudadDto;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CiudadController.class)
public class CiudadControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CiudadService ciudadService;

    @Test
    public void saveRest() throws Exception{
        CiudadDto ciudadDto= new CiudadDto();
        ciudadDto.setCodigo(1L);
        ciudadDto.setDescripcion("Asunción");

        CiudadDto ciudadDto2= new CiudadDto();
        ciudadDto2.setCodigo(2L);
        ciudadDto2.setDescripcion("San Lorenzo");

        List<CiudadDto> list= Arrays.asList(ciudadDto,ciudadDto2);
        Page<CiudadDto> page= new PageImpl<>(list);


        Mockito.when(ciudadService.findAll(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/app/web/rest/ciudades?page=0&size=10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(2)) // Verifica que hay 2 ciudades
                .andExpect(jsonPath("$.content[0].descripcion").value("Asunción")) // Verifica el nombre de la primera ciudad
                .andExpect(jsonPath("$.content[1].descripcion").value("San Lorenzo")); // Verifica el nombre de la segunda ciudad


    }
}
