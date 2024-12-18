package com.example.models.enumerator;

public enum GrupoMuscularObjetivo {
    CUADRICEPS("CUÁDRICEPS"), FEMORALES("FEMORALES"), GLUTEOS("GLÚTEOS"),GEMELOS("GEMELOS"),
    ABDOMEN("ABDOMEN"), PECHO("PECHO"), DORSALES("DORSALES"), TRAPECIOS("TRAPECIOS"),
    DELTOIDES("DELTOIDES"), TRICEPS("TRÍCEPS"), BICEPS("BÍCEPS"), ANTEBRAZOS("ANTEBRAZOS"),
    FULL_BODY("FULL BODY");

    private final String name;

    GrupoMuscularObjetivo(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}