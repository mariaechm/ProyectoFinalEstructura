package com.example.models.enumerator;

public enum ObjetivoRutina {
    HIPERTROFIA("HIPERTROFIA"), FUERZA("FUERZA"), RESISTENCIA("RESISTENCIA"), POWERBUILDING("POWERBUILDING");

    private final String name;

    ObjetivoRutina(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}