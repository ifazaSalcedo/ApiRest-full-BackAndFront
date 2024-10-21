package com.app.datapart.apirest.controller;

import com.app.datapart.data.services.PedidoService;
import com.app.datapart.dto.PedidoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/app/web/rest/pedidos")
public class PedidoController {
    private final PedidoService pedidoService;
    @PostMapping
    public ResponseEntity<?> savePedido(@RequestBody PedidoDto pedidoDto){
        PedidoDto pedidoDtoSave = pedidoService.save(pedidoDto);
        return ResponseEntity.status(201).body(pedidoDtoSave);
    }
    @GetMapping("/controlNumPed")
    public ResponseEntity<?> controlNumPedido(){

        String nroPedido = String.format("%012d", pedidoService.controlNumberPedidoInter());

        Map<String, String> response= new HashMap<>();

        response.put("numeroPedido", nroPedido);

        return ResponseEntity.ok(response);

    }

    @GetMapping("/fechaServidor")
    public ResponseEntity<?> dateServer(){

        LocalDate fechaServidor= LocalDate.now();

        Map<String, String> response= new HashMap<>();

        response.put("fechaServer", DateTimeFormatter.ofPattern("dd-MM-yyyy").format(fechaServidor));

        return ResponseEntity.ok(response);

    }

}
