package com.example.models.enumerator;

public enum TipoEjercicio {
    COMPUESTO("COMPUESTO"), AISLADO("AISLADO");
    
    private final String name;

    TipoEjercicio(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}