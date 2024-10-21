package com.app.datapart.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Convert;
import jakarta.persistence.Converter;
import lombok.Getter;

public enum EstadoPedido {
    PENDIENTE("P", "Pendiente"),
    AULADO("A", "Anulado"),
    COMPLETADO("C", "Completado"),
    VENCIDO("V", "Vencido");

    @Getter private final String codigo;

    @Getter private final String descripcion;

    EstadoPedido(String codigo, String descripcion){
        this.codigo= codigo;
        this.descripcion= descripcion;
    }


}
