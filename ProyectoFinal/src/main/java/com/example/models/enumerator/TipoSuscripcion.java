package com.example.models.enumerator;

public enum TipoSuscripcion {
    DIA(3.00), SEMANA(10.00), MEDIO_MES(15.00), MES(25.00);

    private final Double precio;

    private TipoSuscripcion(Double precio) {
        this.precio = precio;
    }

    public Double getPrecio() {
        return precio;
    }
}