package com.app.datapart.data.services;

import com.app.datapart.Exepciotions.CiudadNotFoundExeption;
import com.app.datapart.data.entity.Ciudad;
import com.app.datapart.data.repository.CiudadRepository;
import com.app.datapart.dto.CiudadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CiudadService implements CiudadInterfaceService{
    private final CiudadRepository ciudadRepository;
    private final CiudadFactoryService ciudadFactory;
    @Override
    public CiudadDto findById(Long codigo) {
        return ciudadRepository.findById(codigo)
                .map(ciudad -> ciudadFactory.crearCiudadDto(ciudad))
                .orElseThrow(() -> new CiudadNotFoundExeption(codigo));
    }

    @Override
    public Page<CiudadDto> findAll(Pageable pag) {
        Page<Ciudad> page = ciudadRepository.findAll(pag);
        return page.map(ciudadFactory::crearCiudadDto);
    }
}
