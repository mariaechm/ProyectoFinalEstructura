package com.example.models;
import com.example.models.enumerator.TipoSuscripcion;

public class Suscripcion {

    private Integer id;
    private String fechaInicio;
    private String fechaFinalizacion;
    private TipoSuscripcion tipo;
    
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

    public TipoSuscripcion getTipo() {
        return this.tipo;
    }

    public void setTipo(TipoSuscripcion tipo) {
        this.tipo = tipo;
    }
    
}