package com.app.datapart.data.services;

import com.app.datapart.data.entity.Cliente;
import com.app.datapart.dto.ClienteDto;
import org.springframework.stereotype.Service;

@Service
public class ClienteFactoryService {
    public ClienteDto crearClienteDto(Cliente entity){
        return new ClienteDto(entity);
    }
    public Cliente crearClienteEntity(ClienteDto dto){
        return new Cliente(dto);
    }
}
