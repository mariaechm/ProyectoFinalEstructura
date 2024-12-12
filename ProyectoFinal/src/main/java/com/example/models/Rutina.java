package com.example.models;

import com.example.models.enumerator.GrupoMuscularObjetivo;
import com.example.models.enumerator.ObjetivoRutina;

public class Rutina {
    private Integer id;
    private String nombreRutina;
    private String descripcion;
    private int nroEjercicios;
    private Integer idEjercicio;

    private GrupoMuscularObjetivo grupoMuscularObjetivo;
    private ObjetivoRutina objetivoRutina;
    

    // CONSTRUCTOR VACIO
    public Rutina() {}

    // GETTERS Y SETTERS
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombreRutina() {
        return this.nombreRutina;
    }

    public void setNombreRutina(String nombreRutina) {
        this.nombreRutina = nombreRutina;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public int getNroEjercicios() {
        return this.nroEjercicios;
    }

    public void setNroEjercicios(int nroEjercicios) {
        this.nroEjercicios = nroEjercicios;
    }

    public Integer getIdEjercicio() {
        return this.idEjercicio;
    }

    public void setIdEjercicio(int idEjercicio) {
        this.idEjercicio = idEjercicio;
    }

    public GrupoMuscularObjetivo getGrupoMuscularObjetivo() {
        return this.grupoMuscularObjetivo;
    }

    public void setGrupoMuscularObjetivo(GrupoMuscularObjetivo grupoMuscularObjetivo) {
        this.grupoMuscularObjetivo = grupoMuscularObjetivo;
    }

    public ObjetivoRutina getObjetivoRutina() {
        return this.objetivoRutina;
    }

    public void setObjetivoRutina(ObjetivoRutina objetivoRutina) {
        this.objetivoRutina = objetivoRutina;
    }
}