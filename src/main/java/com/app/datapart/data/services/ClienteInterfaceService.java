package com.app.datapart.data.services;

import com.app.datapart.data.entity.Cliente;
import com.app.datapart.dto.ClienteDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClienteInterfaceService {
    ClienteDto save(ClienteDto dto);
    ClienteDto update(ClienteDto dto);
    void deleteById(Long id);
    ClienteDto findById(Long id);
    ClienteDto findByDocumentoNro(String doc);
    Page<ClienteDto> findAll(Pageable pag);
    Page<ClienteDto> findByNombreLike(String nom, Pageable pag);
}
