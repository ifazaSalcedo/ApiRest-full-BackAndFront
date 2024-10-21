package com.app.datapart.data.services;

import com.app.datapart.data.entity.Ciudad;
import com.app.datapart.dto.CiudadDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface CiudadInterfaceService {
    CiudadDto findById(Long codigo);
    Page<CiudadDto> findAll(Pageable pag);
}
