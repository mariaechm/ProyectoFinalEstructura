package com.example.models;

import com.example.models.enumerator.*;

public class Persona {
    private Integer id;
    private String nombre;
    private String apellido;
    private String identificacion;
    private String celular;
    private String fechaNacimiento;
    private String direccion;
    private TipoIdentificacion tipoIdentificacion;
    private Genero genero;
    private Rol rol;

    public Persona() {}

    public String getApellido() {
        return apellido;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCelular() {
        return celular;
    }
    
    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdentificacion() {
        return identificacion;
    }
    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public TipoIdentificacion getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(TipoIdentificacion tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }


    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", apellido='" + getApellido() + "'" +
            ", identificacion='" + getIdentificacion() + "'" +
            ", celular='" + getCelular() + "'" +
            ", fechaNacimiento='" + getFechaNacimiento() + "'" +
            ", direccion='" + getDireccion() + "'" +
            ", tipoIdentificacion='" + getTipoIdentificacion() + "'" +
            ", genero='" + getGenero() + "'" +
            ", rol='" + getRol() + "'" +
            "}";
    }

}