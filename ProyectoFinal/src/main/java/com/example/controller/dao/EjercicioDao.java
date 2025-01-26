package com.example.controller.dao;

import com.example.controller.dao.implement.AdapterDao;
import com.example.controller.dao.implement.JsonFileManager;
import com.example.controller.tda.list.LinkedList;
import com.example.models.Ejercicio;
import com.example.models.enumerator.GrupoMuscularObjetivo;
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

    // METODOS DE ACCESO
    public void EjercicioFromJson(String EjercicioJson) {
        this.ejercicio = g.fromJson(EjercicioJson, Ejercicio.class);
    }

    // CRUD
    public Ejercicio[] getAllEjercicios() throws Exception {
        return this.listAll().toArray();
    }

    public Ejercicio getEjercicioById(Integer id) throws Exception {
        return get(id);
    }

    public Ejercicio saveEjercicio() throws Exception {
        if(!camposLlenos()) {
            throw new Exception("Los campos están vacíos, por favor completarlos.");
        }
        this.getEjercicio().setId(JsonFileManager.readAndUpdateCurrentIdOf(className));    
        persist(ejercicio);
        return this.ejercicio;
    }

    public Ejercicio updateEjercicio() throws Exception {
        Integer id = this.getEjercicio().getId();
        if(!camposLlenos()) {
            throw new Exception("Los campos están vacíos, por favor completarlos.");
        }
        merge(this.getEjercicio(), id);
        return this.ejercicio;
    }

    public Ejercicio deleteEjercicio(Integer id) throws Exception {
        Ejercicio ejercicio = get(id);
        remove(id);
        return ejercicio;
    }

    // ENUMERACIONES
    public GrupoMuscularObjetivo getGrupoMuscularObjetivo(String grupoMuscularObjetivo) {
        return GrupoMuscularObjetivo.valueOf(grupoMuscularObjetivo);
    }
    
    public GrupoMuscularObjetivo[] getGrupos() {
        return GrupoMuscularObjetivo.values();
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
        if(this.getEjercicio().getTipoEjercicio() == null) return false;
        if(this.getEjercicio().getGrupoMuscularObjetivo() == null) return false;
        return true;
    }

    
    // ORDENAR
    public Ejercicio[] sort(String attribute, Integer orden, Integer method) throws Exception {
        LinkedList<Ejercicio> list = listAll();
        switch (method) {
            case 0:
                list.quickSort(attribute, orden);
                break;
            case 1:
                list.mergeSort(attribute, orden);
                break;
            case 2:
                list.shellSort(attribute, orden);
                break;
            default:
                throw new Exception("Método de ordenamiento no válido");
        }
        return list.toArray();
    }


    // BUSCAR
    public Ejercicio[] search(String attribute, String value) throws Exception {
        LinkedList<Ejercicio> list = listAll();
        try {
            if(attribute.equalsIgnoreCase("nombreEjercicio")) {
                return list.buscarPorAtributo(attribute, value).toArray();
            } else if (attribute.equalsIgnoreCase("nroSeries") || attribute.equalsIgnoreCase("nroRepSerie") ||
                 attribute.equalsIgnoreCase("tiempoDescanso")) {
                    return list.busquedaLinealBinaria(attribute, Integer.parseInt(value)).toArray();
            } else {
                return list.busquedaLinealBinaria(attribute, value).toArray();
            }
        } catch (Exception e) {
            return new Ejercicio[] {};
        }
    }
}