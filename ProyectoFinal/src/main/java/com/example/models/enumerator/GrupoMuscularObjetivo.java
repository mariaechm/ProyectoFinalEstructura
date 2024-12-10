package com.example.models.enumerator;

public enum GrupoMuscularObjetivo {
    PIERNA("PIERNA"), PECHO("PECHO"), ESPALDA("ESPALDA"), BRAZO("BRAZO"), ABDOMEN("ABDOMEN"), FULL_BODY("FULL BODY");

    private final String name;

    GrupoMuscularObjetivo(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}