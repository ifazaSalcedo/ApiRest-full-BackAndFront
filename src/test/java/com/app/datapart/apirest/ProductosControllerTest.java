package com.app.datapart.apirest;

import com.app.datapart.apirest.controller.ProductoController;
import com.app.datapart.data.services.ProductoService;
import com.app.datapart.dto.ProductoDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductoController.class)

public class ProductosControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    ProductoService productoService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void saveRest() throws Exception{
        ProductoDto dto = new ProductoDto();
        dto.setCodigo("AA1");
        dto.setNombre("Producto prueba");
        dto.setDescripcion("Descripcion del producto de prueba");
        dto.setPrecioVenta(BigDecimal.valueOf(15000));
        dto.setStockActual(BigDecimal.valueOf(20));

        when(productoService.save(any(ProductoDto.class))).thenReturn(dto);

        String jsonContent = objectMapper.writeValueAsString(dto);

        mockMvc.perform(post("/app/web/rest/productos") // Asegúrate de que la ruta sea correcta
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isCreated());

    }
    /**
     * PROBAMOS PARA CUANDO EL END POINT RECIBA CIENTOS DE SOLICITUDES INSTANTANEAS
     * */
    @RepeatedTest(2000) /*Numero de solicitud a recibir*/
    public void saveRestRespit() throws Exception{
        ProductoDto dto = new ProductoDto();
        dto.setCodigo("AA1");
        dto.setNombre("Producto prueba");
        dto.setDescripcion("Descripcion del producto de prueba");
        dto.setPrecioVenta(BigDecimal.valueOf(15000));
        dto.setStockActual(BigDecimal.valueOf(20));

        when(productoService.save(any(ProductoDto.class))).thenReturn(dto);

        String jsonContent = objectMapper.writeValueAsString(dto);

        mockMvc.perform(post("/app/web/rest/productos") // Asegúrate de que la ruta sea correcta
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isCreated());

    }

}
