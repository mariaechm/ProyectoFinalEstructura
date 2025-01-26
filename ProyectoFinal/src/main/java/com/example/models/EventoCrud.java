package com.example.models;

public class EventoCrud {
    private Integer id;
    private String fecha;
    private String hora;
    private String descripcion;

    public EventoCrud() {}

    public EventoCrud(String fecha, String hora, String descripcion) {
        this.fecha = fecha;
        this.hora = hora;
        this.descripcion = descripcion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }
    
    public void setHora(String hora) {
        this.hora = hora;
    }

  
    @Override
    public String toString() {
        return "Fecha: " + this.fecha + "\n" +
                "Hora: " + this.hora + "\n" +
                "Descripcion" + this.descripcion;
    }

}
