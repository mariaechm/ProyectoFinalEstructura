package com.example.models;

import com.example.models.enumerator.GrupoMuscularObjetivo;
import com.example.models.enumerator.TipoEjercicio;

public class Ejercicio {
    private Integer id;
    private String nombreEjercicio;
    private String descripcion;
    private String imagen;
    private Float tiempoDescanso;
    private int nroSeries;
    private int nroRepSerie;

    private TipoEjercicio tipoEjercicio;
    private GrupoMuscularObjetivo grupoMuscularObjetivo;

    // CONSTRUCTOR VACIO
    public Ejercicio() {}

    // GETTERS Y SETTERS
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombreEjercicio() {
        return this.nombreEjercicio;
    }

    public void setNombreEjercicio(String nombreEjercicio) {
        this.nombreEjercicio = nombreEjercicio;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagen() {
        return this.imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Float getTiempoDescanso() {
        return this.tiempoDescanso;
    }

    public void setTiempoDescanso(Float tiempoDescanso) {
        this.tiempoDescanso = tiempoDescanso;
    }
    
    public int getNroSeries() {
        return this.nroSeries;
    }

    public void setNroSeries(int nroSeries) {
        this.nroSeries = nroSeries;
    }

    public int getNroRepSerie() {
        return this.nroRepSerie;
    }

    public void setNroRepSerie(int nroRepSerie) {
        this.nroRepSerie = nroRepSerie;
    }

    public TipoEjercicio getTipoEjercicio() {
        return this.tipoEjercicio;
    }

    public void setTipoEjercicio(TipoEjercicio tipoEjercicio) {
        this.tipoEjercicio = tipoEjercicio;
    }

    public GrupoMuscularObjetivo getGrupoMuscularObjetivo() {
        return this.grupoMuscularObjetivo;
    }

    public void setGrupoMuscularObjetivo(GrupoMuscularObjetivo grupoMuscularObjetivo) {
        this.grupoMuscularObjetivo = grupoMuscularObjetivo;
    }

}