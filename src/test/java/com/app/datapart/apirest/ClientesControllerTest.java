package com.app.datapart.apirest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.app.datapart.apirest.controller.ClienteController;
import com.app.datapart.data.services.ClienteService;
import com.app.datapart.dto.CiudadDto;
import com.app.datapart.dto.ClienteDto;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ClienteController.class)
public class ClientesControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    ClienteService clienteService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void saveRest() throws Exception{
        ClienteDto dto= new ClienteDto();
        dto.setNombre("Cliente Prueba");
        dto.setDireccion("Direccion cliente ");
        dto.setDocumentoNro("700");
        dto.setCiudad(new CiudadDto());
        dto.setDireccion("Direccion Prueba");
        dto.setTelefono("0981-300");
        dto.setEmail("email@preuba.com");

        when(clienteService.save(any(ClienteDto.class))).thenReturn(dto);

        String jsonContent = objectMapper.writeValueAsString(dto);

        mockMvc.perform(post("/app/web/rest/clientes") // Asegúrate de que la ruta sea correcta
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isCreated());

    }

    /**
     * PROBAMOS PARA CUANDO EL END POINT RECIBA CIENTOS DE SOLICITUDES INSTANTANEAS
     * */
    @RepeatedTest(2000) /*Numero de solicitud a recibir*/
    public void saveRestRepit() throws Exception{
        ClienteDto dto= new ClienteDto();
        dto.setNombre("Cliente Prueba");
        dto.setDireccion("Direccion cliente ");
        dto.setDocumentoNro("700");
        dto.setCiudad(new CiudadDto());
        dto.setDireccion("Direccion Prueba");
        dto.setTelefono("0981-300");
        dto.setEmail("email@preuba.com");

        when(clienteService.save(any(ClienteDto.class))).thenReturn(dto);

        String jsonContent = objectMapper.writeValueAsString(dto);

        mockMvc.perform(post("/app/web/rest/clientes") // Asegúrate de que la ruta sea correcta
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isCreated());

    }
}
