package com.example.controller.dao.services;

import java.lang.reflect.Array;

import com.example.controller.dao.RutinaDao;
import com.example.models.Rutina;
import com.example.models.enumerator.GrupoMuscularObjetivo;
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

    public void RutinaFromJson(String RutinaJson) {
        this.obj.RutinaFromJson(RutinaJson);
    }

    public Rutina[] getAllRutinas() throws Exception {
        try {
            return obj.getAllRutinas().toArray(); 
        } catch (Exception e) {
            return (Rutina[])Array.newInstance(Rutina.class, 0);
        }
    }

    // CRUD
    public Rutina getRutinaById(Integer id) throws Exception {
        return this.obj.getRutinaById(id);
    }

    public void saveRutina() throws Exception {
        this.obj.saveRutina();
    }

    public void update() throws Exception {
        this.obj.updateRutina();
    }

    public void deleteRutina(Integer id) throws Exception {        
        this.obj.deleteRutina(id);
    }

    // ENUMERACIONES
    public GrupoMuscularObjetivo getGrupoMuscularObjetivo(String grupoMuscularObjetivo) {
        return obj.getGrupoMuscularObjetivo(grupoMuscularObjetivo);
    }

    public GrupoMuscularObjetivo[] getGrupos() {
        return obj.getGrupos();
    }

    public ObjetivoRutina getObjetivoRutina(String objetivoRutina) {
        return obj.getObjetivoRutina(objetivoRutina);
    }

    public ObjetivoRutina[] getObjetivos() {
        return obj.getObjetivos();
    }
}