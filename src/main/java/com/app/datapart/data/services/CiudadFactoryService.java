package com.app.datapart.data.services;

import com.app.datapart.data.entity.Ciudad;
import com.app.datapart.dto.CiudadDto;
import org.springframework.stereotype.Service;

@Service
public class CiudadFactoryService {
    public Ciudad crearCiudad(CiudadDto dto){
        return new Ciudad(dto);
    }
    public CiudadDto crearCiudadDto(Ciudad enty){
        return new CiudadDto(enty);
    }
}
