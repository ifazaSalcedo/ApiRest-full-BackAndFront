package com.app.datapart.data.services;

import com.app.datapart.dto.ProductoDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductoInterfaceService {
    ProductoDto save(ProductoDto productoDto);
    ProductoDto update(ProductoDto productoDto);
    void deleteById(String codigo);
    ProductoDto findId(String codigo);
    Page<ProductoDto> findAll(Pageable pag);
    Page<ProductoDto> findByArticuloNombreLike(String nom, Pageable pag);


}
