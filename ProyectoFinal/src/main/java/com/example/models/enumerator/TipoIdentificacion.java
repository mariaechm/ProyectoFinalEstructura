package com.example.models.enumerator;

public enum TipoIdentificacion {
    CEDULA("CEDULA"), RUC("RUC");

    private final String name;

    TipoIdentificacion(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}