package com.app.datapart.apirest.controller;

import com.app.datapart.data.services.ProductoService;
import com.app.datapart.dto.ProductoDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/app/web/rest/productos")
public class ProductoController {
    private final ProductoService productoService;

    @PostMapping
    public ResponseEntity<?> saveProducto(@Valid @RequestBody ProductoDto productoDto, BindingResult errorValid){

        if(errorValid.hasFieldErrors()){
            return  formatearErrorValid(errorValid);
        }

        ProductoDto productoDtoSave = productoService.save(productoDto);

        return ResponseEntity.status(201).body(productoDtoSave);
    }

    @PutMapping
    public ResponseEntity<?> updateProducto(@Valid @RequestBody ProductoDto productoDto,  BindingResult errorValid){
        if(errorValid.hasFieldErrors()){
            return  formatearErrorValid(errorValid);
        }

        ProductoDto productoDtoSave = productoService.update(productoDto);

        return ResponseEntity.status(200).body(productoDtoSave);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteProducto(@RequestParam("codProd") String codProd){
        productoService.deleteById(codProd);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/product-list")
    public ResponseEntity<Page<ProductoDto>> findAllProductos(@RequestParam int page, @RequestParam int size){
        Page<ProductoDto> listAll= productoService.findAll(PageRequest.of(page, size));
        return ResponseEntity.ok(listAll);
    }
    @GetMapping("/produt-filt")
    public ResponseEntity<Page<ProductoDto>> findAllProductos(@RequestParam("nomProd") String nomProd, @RequestParam int page, @RequestParam int size){
        Page<ProductoDto> listAll= productoService.findByArticuloNombreLike(nomProd,PageRequest.of(page, size));
        return ResponseEntity.ok(listAll);
    }

    @GetMapping("/produt-id")
    public ResponseEntity<ProductoDto> findId(@RequestParam("idProd") String idProd){
        ProductoDto dto= productoService.findId(idProd);
        return ResponseEntity.ok(dto);
    }

    private ResponseEntity<Map<String, String>> formatearErrorValid(BindingResult errorValid) {
        Map<String, String> mapError= new HashMap<>();
        errorValid.getFieldErrors().forEach(fieldError -> {
            mapError.put(fieldError.getField(), fieldError.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(mapError);
    }

}
