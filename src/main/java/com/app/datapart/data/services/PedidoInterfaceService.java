package com.app.datapart.data.services;

import com.app.datapart.dto.PedidoDto;

public interface PedidoInterfaceService {
    PedidoDto save(PedidoDto pedidoDto);
    Integer controlNumberPedidoInter();
}
