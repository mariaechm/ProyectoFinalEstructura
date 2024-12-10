package com.example.models.enumerator;

public enum Rol {
    ADMINISTRADOR("ADMINISTRADOR"), CLIENTE("CLIENTE");

    private final String name;

    Rol(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}