package com.example.models.enumerator;

public enum TipoSuscripcion {
    DIA(3.00,1), 
    SEMANA(10.00, 7),
    MEDIO_MES(15.00,15), 
    MES(25.00, 30);

    private final Double precio;
    private final int duracionDias;

    private TipoSuscripcion(Double precio, int duracionDias) {
        this.precio = precio;
        this.duracionDias = duracionDias;
    }

    public Double getPrecio() {
        return precio;
    }

    public int getDuracionDias() {
        return duracionDias;
    }
}