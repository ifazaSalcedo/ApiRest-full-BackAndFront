package com.app.datapart.apirest.controller;

import com.app.datapart.data.services.CiudadService;
import com.app.datapart.dto.CiudadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/app/web/rest/ciudades")
public class CiudadController {

    private final CiudadService ciudadService;


    @GetMapping
    public ResponseEntity<Page<CiudadDto>> findAll(Pageable pag){
        Page<CiudadDto> pageCiudad = ciudadService.findAll(pag);
        return ResponseEntity.ok(pageCiudad);
    }

}
