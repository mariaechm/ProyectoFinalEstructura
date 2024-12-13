package com.example.controller.dao;

import com.example.controller.dao.implement.AdapterDao;
import com.example.controller.tda.list.LinkedList;
import com.example.models.Ejercicio;
import com.example.models.enumerator.TipoEjercicio;


public class EjercicioDao extends AdapterDao<Ejercicio> {
    private Ejercicio ejercicio;

    // CONSTRUCTOR
    public EjercicioDao() {
        super(Ejercicio.class);
    }

    // GETTER
    public Ejercicio getEjercicio() {
        if(this.ejercicio == null) {
            this.ejercicio = new Ejercicio();
        }
        return this.ejercicio;
    }

    public void setEjercicio(Ejercicio ejercicio) {
        this.ejercicio = ejercicio;
    }

    // METODOS DE ACCESO
    public LinkedList<Ejercicio> getAllEjercicios() throws Exception {
        return this.listAll();
    }

    public void EjercicioFromJson(String EjercicioJson) {
        this.ejercicio = g.fromJson(EjercicioJson, Ejercicio.class);
    }

    // CRUD
    public Ejercicio getEjercicioById(Integer id) throws Exception {
        return get(id);
    }

    public void saveEjercicio() throws Exception {
        Integer id = listAll().getSize() + 1;
        this.getEjercicio().setId(id);
        if(!camposLlenos()) {
            throw new Exception("Los campos están vacíos, por favor completarlos");
        }
        persist(this.ejercicio);
    }

    public void updateEjercicio() throws Exception {
        Integer id = getEjercicio().getId();
        if(!camposLlenos()) {
            throw new Exception("Los campos están vacíos, por favor completarlos");
        }
        merge(getEjercicio(), id);
    }

    public void deleteEjercicio(Integer id) throws Exception {
        remove(id);
    }

    public TipoEjercicio getTipoEjercicio(String tipoEjercicio) {
        return TipoEjercicio.valueOf(tipoEjercicio);
    }

    public TipoEjercicio[] getTipos() {
        return TipoEjercicio.values();
    }
    
    
    // VALIDACIONES
    public Boolean camposLlenos() {
        if(this.getEjercicio().getNombreEjercicio() == null) return false;
        if(this.getEjercicio().getDescripcion() == null) return false;
        if(this.getEjercicio().getTiempoDescanso() == 0) return false;
        if(this.getEjercicio().getNroSeries() == 0) return false;
        if(this.getEjercicio().getNroRepSerie() == 0) return false;
        return true;
    }
}