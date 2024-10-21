package com.app.datapart.data.services;

import com.app.datapart.data.entity.Producto;
import com.app.datapart.dto.ProductoDto;
import org.springframework.stereotype.Service;

@Service
public class ProductoFactoryService {
    public Producto crearProductoEntity(ProductoDto dto){
        return new Producto(dto);
    }

    public ProductoDto crearProductoDto(Producto entity) {
        return new ProductoDto(entity);
    }
}
