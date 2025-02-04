package com.example.controller.dao.services;

import com.example.controller.dao.EjercicioDao;
import com.example.models.Ejercicio;
import com.example.models.enumerator.GrupoMuscularObjetivo;
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

    // CRUD
    public Ejercicio[] getAllEjercicios() throws Exception {
        return this.obj.getAllEjercicios();
    }

    public Ejercicio getEjercicioById(Integer id) throws Exception {
        return this.obj.getEjercicioById(id);
    }

    public Ejercicio saveEjercicio(String json) throws Exception {
        this.obj.EjercicioFromJson(json);
        return this.obj.saveEjercicio();
    }

    public Ejercicio updateEjercicio(String json) throws Exception {
        this.obj.EjercicioFromJson(json);
        return this.obj.updateEjercicio();
    }

    public Ejercicio deleteEjercicio(Integer id) throws Exception {        
        return this.obj.deleteEjercicio(id);
    }

    // ENUMERACIONES
    public TipoEjercicio getTipoEjercicio(String tipoEjercicio) {
        return obj.getTipoEjercicio(tipoEjercicio);
    }

    public TipoEjercicio[] getTipos() {
        return obj.getTipos();
    }

    public GrupoMuscularObjetivo getGrupoMuscularObjetivo(String grupoMuscularObjetivo) {
        return obj.getGrupoMuscularObjetivo(grupoMuscularObjetivo);
    }

    public GrupoMuscularObjetivo[] getGrupos() {
        return obj.getGrupos();
    }

    // ORDENAR
    public Ejercicio[] sort(String attribute, Integer orden, Integer method) throws Exception {
        return this.obj.sort(attribute, orden, method);
    }

    // BUSCAR 
    public Ejercicio[] search(String attribute, String value) throws Exception {
        return this.obj.search(attribute, value);
    }
}