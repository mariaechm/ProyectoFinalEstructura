package com.example.controller.dao.services;

import com.example.controller.dao.RutinaDao;
import com.example.models.Rutina;
import com.example.models.enumerator.ObjetivoRutina;

public class RutinaServices {
    private RutinaDao obj;

    // CONSTRUCTOR
    public RutinaServices() {
        this.obj = new RutinaDao();
    }

    // GETTER
    public Rutina getRutina() {
        return this.obj.getRutina();
    }

    // CRUD
    public Rutina[] getAllRutinas() throws Exception {
        return this.obj.getAllRutinas().toArray();
    }

    public Object[] showListAll() throws Exception {
        return this.obj.listShowAll();
    }

    public Rutina getRutinaById(Integer id) throws Exception {
        return this.obj.getRutinaById(id);
    }

    public Rutina saveRutina(String json) throws Exception {
        this.obj.RutinaFromJson(json);
        return this.obj.saveRutina();
    }

    public Rutina updateRutina(String json) throws Exception {
        this.obj.RutinaFromJson(json);
        return this.obj.updateRutina();
    }

    public Rutina deleteRutina(Integer id) throws Exception {        
        return this.obj.deleteRutina(id);
    }

    // ENUMERACIONES
    public ObjetivoRutina getObjetivoRutina(String objetivoRutina) {
        return obj.getObjetivoRutina(objetivoRutina);
    }

    public ObjetivoRutina[] getObjetivos() {
        return obj.getObjetivos();
    }

    // ORDENAR
    public Rutina[] sort(String attribute, Integer orden, Integer method) throws Exception {
        return this.obj.sort(attribute, orden, method);
    }

    // BUSCAR
    public Rutina[] search(String attribute, String value) throws Exception {
        return this.obj.search(attribute, value);
    }
}