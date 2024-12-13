package com.example.models;

public class Split {
    private Integer id;
    private String nombreSplit;
    private String descripcion;
    private int nroDias;
    private Integer idRutina;

    // CONSTRUCTOR VACIO
    public Split() {}

    // GETTERS Y SETTERS
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombreSplit() {
        return this.nombreSplit;
    }

    public void setNombreSplit(String nombreSplit) {
        this.nombreSplit = nombreSplit;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public int getNroDias() {
        return this.nroDias;
    }

    public void setNroDias(int nroDias) {
        this.nroDias = nroDias;
    }

    public Integer getIdRutina() {
        return this.idRutina;
    }

    public void setIdRutina(int idRutina) {
        this.idRutina = idRutina;
    }
}