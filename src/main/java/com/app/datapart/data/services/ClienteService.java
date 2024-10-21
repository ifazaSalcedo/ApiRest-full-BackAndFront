package com.app.datapart.data.services;

import com.app.datapart.Exepciotions.ClienteNotFoundExeption;
import com.app.datapart.data.entity.Cliente;
import com.app.datapart.data.repository.CiudadRepository;
import com.app.datapart.data.repository.ClienteRepository;
import com.app.datapart.dto.ClienteDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClienteService implements ClienteInterfaceService{
    private final ClienteRepository clienteRepository;
    private final ClienteFactoryService clienteFactory;
    @Transactional
    @Override
    public ClienteDto save(ClienteDto clienteDto) {
        if(clienteRepository.findByEmail(clienteDto.getEmail()).isPresent()){
            throw new ClienteNotFoundExeption("El email "+clienteDto.getEmail() + " ya se encuentra registrado!");
        }
        if(clienteRepository.findByDocumentoNro(clienteDto.getDocumentoNro()).isPresent()){
            throw new ClienteNotFoundExeption("El Documento de Identidad "+clienteDto.getEmail() + " ya se encuentra registrado!");
        }
        Cliente clienteSave = clienteRepository.save(clienteFactory.crearClienteEntity(clienteDto));
        return clienteFactory.crearClienteDto(clienteSave);
    }

    @Transactional
    @Override
    public ClienteDto update(ClienteDto clienteDto) {

        if(clienteRepository.findById(clienteDto.getCodigo()).isEmpty()){
            throw new ClienteNotFoundExeption("Cliente " + clienteDto.getCodigo() + " no existe");
        }

        Cliente clienteUpdate = clienteRepository.save(clienteFactory.crearClienteEntity(clienteDto));

        return clienteFactory.crearClienteDto(clienteUpdate);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        if(clienteRepository.findById(id).isEmpty()){
            throw new ClienteNotFoundExeption("Cliente " + id + " no existe");
        }
        clienteRepository.deleteById(id);
    }

    @Override
    public ClienteDto findById(Long id) {
        return clienteRepository.findById(id)
                .map(clienteFactory::crearClienteDto)
                .orElseThrow(() -> new ClienteNotFoundExeption("Cliente " + id + " no existe"));
    }

    @Override
    public ClienteDto findByDocumentoNro(String doc) {
        return clienteRepository.findByDocumentoNro(doc)
                .map(clienteFactory::crearClienteDto)
                .orElseThrow(() -> new ClienteNotFoundExeption("Cliente " + doc + " no existe"));
    }

    @Override
    public Page<ClienteDto> findAll(Pageable pag) {
        Page<Cliente> page= clienteRepository.findAll(pag);
        return page.map(clienteFactory::crearClienteDto);
    }

    @Override
    public Page<ClienteDto> findByNombreLike(String nom, Pageable pag) {
        Page<Cliente> page = clienteRepository.findByNombreContains(nom, pag);
        return page.map(clienteFactory::crearClienteDto);
    }
}
