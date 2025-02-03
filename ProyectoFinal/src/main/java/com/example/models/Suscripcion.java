package com.example.models;

import com.example.models.enumerator.TipoSuscripcion;

public class Suscripcion {

    private Integer id;
    private String fechaInicio;
    private String fechaFinalizacion;
    private Double precio;
    private int duracionDias;

    private Integer personaId;

    private TipoSuscripcion tipo;

    // Constructor vacio
    public Suscripcion() {
        
    }

    // Getters y setters
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFechaInicio() {
        return this.fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

   public String getFechaFinalizacion() {
        return this.fechaFinalizacion;
    }

    public void setFechaFinalizacion(String fechaFinalizacion) {
        this.fechaFinalizacion = fechaFinalizacion;
    }

    public Double getPrecio() {
        return this.precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public int getDuracionDias() {
        return this.duracionDias;
    }

    public void setDuracionDias(int duracionDias) {
        this.duracionDias = duracionDias;
    }

    public TipoSuscripcion getTipo() {
        return this.tipo;
    }

    public void setTipo(TipoSuscripcion tipo) {
        this.tipo = tipo;
    }

    public Integer getPersonaId() {
        return this.personaId;
    }

    public void setPersonaId(Integer personaId) {
        this.personaId = personaId;
    }
}