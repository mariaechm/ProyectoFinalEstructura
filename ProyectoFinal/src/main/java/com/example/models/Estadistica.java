package com.example.models;

public class Estadistica {

    private Integer id;
    private float medidaEspalda;
    private float medidaPierna;
    private float medidaBrazo;
    private float medidaCintura;
    private float peso;
    private float altura;



    public Estadistica(Integer id,  float medidaEspalda, float medidaPierna, float medidaBrazo, float medidaCintura, float peso, float altura) {
        this.id = id;
        this.medidaEspalda = medidaEspalda;
        this.medidaPierna = medidaPierna;
        this.medidaBrazo = medidaBrazo;
        this.medidaCintura = medidaCintura;
        this.peso = peso;
        this.altura = altura;
    }

     // Getters and Setters
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public float getMedidaEspalda() {
        return medidaEspalda;
    }

    public void setMedidaEspalda(float medidaEspalda) {
        this.medidaEspalda = medidaEspalda;
    }

    public float getMedidaPierna() {
        return medidaPierna;
    }

    public void setMedidaPierna(float medidaPierna) {
        this.medidaPierna = medidaPierna;
    }

    public float getMedidaBrazo() {
        return medidaBrazo;
    }

    public void setMedidaBrazo(float medidaBrazo) {
        this.medidaBrazo = medidaBrazo;
    }

    public float getMedidaCintura() {
        return medidaCintura;
    }

    public void setMedidaCintura(float medidaCintura) {
        this.medidaCintura = medidaCintura;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public float getAltura() {
        return altura;
    }

    public void setAltura(float altura) {
        this.altura = altura;
    }

    public Estadistica() {
    }
}
