package com.app.datapart.data.services;

import com.app.datapart.Exepciotions.ProductoNotFoundExeption;
import com.app.datapart.data.entity.Producto;
import com.app.datapart.data.repository.ProductoRepository;
import com.app.datapart.dto.ProductoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductoService implements ProductoInterfaceService{
    private final ProductoRepository productoRepository;
    private final ProductoFactoryService productoFactoryService;

    @Transactional
    @Override
    public ProductoDto save(ProductoDto productoDto) {

        if(productoRepository.findById(productoDto.getCodigo()).isPresent()){
            throw new ProductoNotFoundExeption("El código "+productoDto.getCodigo()+" de articulo ya existe!");
        }

        Producto producto = productoRepository.save(
                productoFactoryService.crearProductoEntity(productoDto));

        return productoFactoryService.crearProductoDto(producto);

    }

    @Transactional
    @Override
    public ProductoDto update(ProductoDto productoDto) {
        Producto productoEntity = productoFactoryService.crearProductoEntity(productoDto);
        if (productoRepository.findById(productoEntity.getCodigo()).isEmpty()) {
            throw new ProductoNotFoundExeption("El código "+productoEntity.getCodigo()+" de articulo no existe!");
        }
        return productoFactoryService.crearProductoDto(productoRepository.save(productoEntity));
    }

    @Transactional
    @Override
    public void deleteById(String codigo) {
        if (productoRepository.findById(codigo).isEmpty()) {
            throw new ProductoNotFoundExeption("El código "+codigo+" de articulo no existe!");
        }
        productoRepository.deleteById(codigo);
    }

    @Override
    public ProductoDto findId(String codigo) {
        return productoRepository.findById(codigo).map(productoFactoryService::crearProductoDto).orElseThrow(
                () -> new ProductoNotFoundExeption("El código "+codigo+" de articulo no existe!")
        );
    }

    @Override
    public Page<ProductoDto> findAll(Pageable pag) {
        Page<Producto> pageProductos= productoRepository.findAll(pag);
        return pageProductos.map(productoFactoryService::crearProductoDto);
    }

    @Override
    public Page<ProductoDto> findByArticuloNombreLike(String nom, Pageable pag) {
        Page<Producto> pageProductos= productoRepository.findByNombreContains(nom, pag);
        return pageProductos.map(productoFactoryService::crearProductoDto);
    }
}
