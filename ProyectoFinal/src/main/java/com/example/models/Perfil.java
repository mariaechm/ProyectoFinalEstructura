package com.example.models;

public class Perfil {
    private Integer id;
    private String nickName;
    private String imagen;
    private String objetivoCliente;
    private String fechaCreacion;

    public Perfil() {
    }

    public Perfil(Integer id, String nickName, String imagen, String objetivoCliente, String fechaCreacion) {
        this.id = id;
        this.nickName = nickName;
        this.imagen = imagen;
        this.objetivoCliente = objetivoCliente;
        this.fechaCreacion = fechaCreacion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getObjetivoCliente() {
        return objetivoCliente;
    }

    public void setObjetivoCliente(String objetivoCliente) {
        this.objetivoCliente = objetivoCliente;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    
}
