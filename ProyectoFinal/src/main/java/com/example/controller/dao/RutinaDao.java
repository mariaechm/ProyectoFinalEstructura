package com.example.controller.dao;

import java.util.HashMap;

import com.example.controller.dao.implement.AdapterDao;
import com.example.controller.dao.implement.JsonFileManager;
import com.example.controller.dao.services.EjercicioServices;
import com.example.controller.tda.list.LinkedList;
import com.example.models.Ejercicio;
import com.example.models.Rutina;
import com.example.models.enumerator.ObjetivoRutina;

public class RutinaDao extends AdapterDao<Rutina> {
    private Rutina rutina;

    // CONSTRUCTOR
    public RutinaDao() {
        super(Rutina.class);
    }

    // GETTER
    public Rutina getRutina() {
        if(this.rutina == null) {
            this.rutina = new Rutina();
        }
        return this.rutina;
    }

    // METODOS DE ACCESO
    public void RutinaFromJson(String RutinaJson) {
        this.rutina = g.fromJson(RutinaJson, Rutina.class);
    }

    // CRUD
    public LinkedList<Rutina> getAllRutinas() throws Exception {
        return this.listAll();
    }

    public Object[] listShowAll() throws Exception {
        if(!getAllRutinas().isEmpty()) {
            Rutina[] lista = getAllRutinas().toArray();
            Object[] respuesta = new Object[lista.length];
            for(int i = 0; i < lista.length; i++) {
                Ejercicio[] ejercicios = new Ejercicio[lista[i].getNroEjercicios()];
                System.out.println(lista[i].getNroEjercicios());
                if(ejercicios.length != 0) {
                    for(int j = 0; j < lista[i].getNroEjercicios(); j++) {
                    ejercicios[j] = new EjercicioServices().getEjercicioById(lista[i].getIdEjercicio()[j]);
                    }
                }
                HashMap<String, Object> mapa = new HashMap<>();

                mapa.put("id", lista[i].getId());
                mapa.put("nombreRutina", lista[i].getNombreRutina());
                mapa.put("descripcion", lista[i].getDescripcion());
                mapa.put("nroEjercicios", lista[i].getNroEjercicios());
                mapa.put("ejercicios", ejercicios);
                mapa.put("objetivoRutina", lista[i].getObjetivoRutina());
                respuesta[i] = mapa;
            }
            return respuesta;
        }
        return new Object[]{};
    }

    public Rutina getRutinaById(Integer id) throws Exception {
        return get(id);
    }

    public Rutina saveRutina() throws Exception {
        if(!camposLlenos()) {
            throw new Exception("Los campos están vacíos, por favor completarlos.");
        }
        this.getRutina().setNroEjercicios(this.getRutina().getIdEjercicio().length);
        this.getRutina().setId(JsonFileManager.readAndUpdateCurrentIdOf(className));    
        persist(rutina);
        return this.rutina;
    }

    public Rutina updateRutina() throws Exception {
        Integer id = this.getRutina().getId();
        if(!camposLlenos()) {
            throw new Exception("Los campos están vacíos, por favor completarlos.");
        }
        this.getRutina().setNroEjercicios(this.getRutina().getIdEjercicio().length);
        merge(this.getRutina(), id);
        return this.rutina;
    }

    public Rutina deleteRutina(Integer id) throws Exception {
        Rutina rutina = get(id);
        remove(id);
        return rutina;
    }

    // ENUMERACIONES
    public ObjetivoRutina getObjetivoRutina(String objetivoRutina) {
        return ObjetivoRutina.valueOf(objetivoRutina);
    }

    public ObjetivoRutina[] getObjetivos() {
        return ObjetivoRutina.values();
    }
   

    // VALIDACIONES
    public Boolean camposLlenos() {
        if(this.getRutina().getNombreRutina() == null) return false;
        if(this.getRutina().getDescripcion() == null) return false;
        if(this.getRutina().getIdEjercicio() == null) return false;
        if(this.getRutina().getObjetivoRutina() == null) return false;
        return true;
    }

    
    // ORDENAR
    public Rutina[] sort(String attribute, Integer orden, Integer method) throws Exception {
        LinkedList<Rutina> list = listAll();
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
    public Rutina[] search(String attribute, String value) throws Exception {
        LinkedList<Rutina> list = listAll();
        try {
            if(attribute.equalsIgnoreCase("nombreRutina")) {
                return list.buscarPorAtributo(attribute, value).toArray();
            } else if (attribute.equalsIgnoreCase("nroEjercicios")) {
                    return list.busquedaLinealBinaria(attribute, Integer.parseInt(value)).toArray();
            } else {
                return list.busquedaLinealBinaria(attribute, value).toArray();
            }
        } catch (Exception e) {
            return new Rutina[] {};
        }
    }
}