package com.example.models;

public class Perfil {
    private Integer id;
    private Float peso;
    private String imagen;
    private Float altura;
    private String objetivoCliente;

    public Perfil() {
    }

    public Perfil(Integer id, Float peso, String imagen, Float altura, String objetivoCliente) {
        this.id = id;
        this.peso = peso;
        this.imagen = imagen;
        this.altura = altura;
        this.objetivoCliente = objetivoCliente;
    }

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float getPeso() {
        return peso;
    }

    public void setPeso(Float peso) {
        this.peso = peso;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Float getAltura() {
        return altura;
    }

    public void setAltura(Float altura) {
        this.altura = altura;
    }

    public String getObjetivoCliente() {
        return objetivoCliente;
    }

    public void setObjetivoCliente(String objetivoCliente) {
        this.objetivoCliente = objetivoCliente;
    }
}
