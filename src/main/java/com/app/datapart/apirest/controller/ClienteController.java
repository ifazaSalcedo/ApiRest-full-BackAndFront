package com.app.datapart.apirest.controller;

import com.app.datapart.data.services.ClienteService;
import com.app.datapart.dto.ClienteDto;
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
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/app/web/rest/clientes")
public class ClienteController {
    private final ClienteService clienteService;
    @PostMapping
    public ResponseEntity<?> saveCliente(@Valid @RequestBody ClienteDto clienteDto, BindingResult errorValid){
        if(errorValid.hasFieldErrors()){
            return formatearErrorValid(errorValid);
        }
        ClienteDto clienteDtoSave= clienteService.save(clienteDto);
        return ResponseEntity.status(201).body(clienteDtoSave);
    }

    @PutMapping
    public ResponseEntity<?> updateCliente(@Valid @RequestBody ClienteDto clienteDto, BindingResult errorValid){
        if(errorValid.hasFieldErrors()){
            return formatearErrorValid(errorValid);
        }
        ClienteDto clienteDtoSave= clienteService.update(clienteDto);
        return ResponseEntity.status(200).body(clienteDtoSave);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCliente(@RequestParam("codCli") Long codigo){
        clienteService.deleteById(codigo);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{codCli}")
    public ResponseEntity<ClienteDto> clienteById(@PathVariable Long codCli){
        ClienteDto clienteDto = clienteService.findById(codCli);
        return ResponseEntity.ok(clienteDto);
    }
        @GetMapping("/client-list")
    public ResponseEntity<Page<ClienteDto>> clienteById(@RequestParam int page, @RequestParam int size){
        Page<ClienteDto> clientes= clienteService.findAll(PageRequest.of(page, size));
        return ResponseEntity.ok(clientes);
    }
    @GetMapping("/client-fil")
    public ResponseEntity<Page<ClienteDto>> clienteByNombreLike(@RequestParam("cliNom") String nom, @RequestParam int page, @RequestParam int size){
        Page<ClienteDto> clientes= clienteService.findByNombreLike(nom,PageRequest.of(page, size));
        return ResponseEntity.ok(clientes);
    }
    @GetMapping("/client-doc")
    public ResponseEntity<ClienteDto> clienteByNroDocumento(@RequestParam("nroDoc") String doc){
        ClienteDto clienteDto= clienteService.findByDocumentoNro(doc);
        return ResponseEntity.ok(clienteDto);
    }
    private ResponseEntity<Map<String, String>> formatearErrorValid(BindingResult errorValid) {
        Map<String, String> mapError= new HashMap<>();
        errorValid.getFieldErrors().forEach(fieldError -> {
            mapError.put(fieldError.getField(), fieldError.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(mapError);
    }

}
