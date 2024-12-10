package com.example.models.enumerator;

public enum Genero {
    HOMBRE("HOMBRE"), MUJER("MUJER"), OTRO("OTRO");

    private final String name;

    Genero(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}