package com.example.controller.dao.services;

import java.lang.reflect.Array;

import com.example.controller.dao.EjercicioDao;
import com.example.models.Ejercicio;
import com.example.models.enumerator.TipoEjercicio;

public class EjercicioServices {
    private EjercicioDao obj;

    // CONSTRUCTOR
    public EjercicioServices() {
        this.obj = new EjercicioDao();
    }

    // GETTER
    public Ejercicio getEjercicio() {
        return this.obj.getEjercicio();
    }
    
    public void EjercicioFromJson(String EjercicioJson) {
        this.obj.EjercicioFromJson(EjercicioJson);
    }

    public Ejercicio[] getAllEjercicios() throws Exception {
        try {
            return obj.getAllEjercicios().toArray(); 
        } catch (Exception e) {
            return (Ejercicio[])Array.newInstance(Ejercicio.class, 0);
        }
    }

    // CRUD
    public Ejercicio getEjercicioById(Integer id) throws Exception {
        return this.obj.getEjercicioById(id);
    }

    public void saveEjercicio() throws Exception {
        this.obj.saveEjercicio();
    }

    public void update() throws Exception {
        this.obj.updateEjercicio();
    }

    public void deleteEjercicio(Integer id) throws Exception {        
        this.obj.deleteEjercicio(id);
    }

    public TipoEjercicio getTipoEjercicio(String tipoEjercicio) {
        return obj.getTipoEjercicio(tipoEjercicio);
    }

    public TipoEjercicio[] getTipos() {
        return obj.getTipos();
    }
}