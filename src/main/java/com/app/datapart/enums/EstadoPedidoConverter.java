package com.app.datapart.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class EstadoPedidoConverter implements AttributeConverter<EstadoPedido, String> {

    @Override
    public String convertToDatabaseColumn(EstadoPedido estado) {
        if(estado==null){
            return null;
        }
        return estado.getCodigo();
    }

    @Override
    public EstadoPedido convertToEntityAttribute(String codigo) {
        if(codigo==null){
            return null;
        }
        for (EstadoPedido estado : EstadoPedido.values()){
            if(estado.getCodigo().equals(codigo)){
                return estado;
            }
        }
        throw new IllegalArgumentException("Codigo de estado del pedido desconocido");
    }
}
