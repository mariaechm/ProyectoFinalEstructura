package com.example.controller.dao;

import java.util.HashMap;
import com.example.controller.dao.implement.AdapterDao;
import com.example.controller.dao.implement.JsonFileManager;
import com.example.controller.dao.services.RutinaServices;
import com.example.controller.tda.list.LinkedList;
import com.example.models.Split;
import com.example.models.Rutina;

public class SplitDao extends AdapterDao<Split> {
    private Split split;

    // CONSTRUCTOR
    public SplitDao() {
        super(Split.class);
    }

    // GETTER
    public Split getSplit() {
        if(this.split == null) {
            this.split = new Split();
        }
        return this.split;
    }

    // METODOS DE ACCESO
    public void SplitFromJson(String SplitJson) {
        this.split = g.fromJson(SplitJson, Split.class);
    }

    // CRUD
    public LinkedList<Split> getAllSplits() throws Exception {
        return this.listAll();
    }

    public Object[] listShowAll() throws Exception {
        if(!getAllSplits().isEmpty()) {
            Split[] lista = getAllSplits().toArray();
            Object[] respuesta = new Object[lista.length];
            for(int i = 0; i < lista.length; i++) {
                Rutina[] rutinas = new Rutina[lista[i].getIdRutina().length];
                for(int j = 0; j < lista[i].getIdRutina().length; j++) {
                    rutinas[j] = new RutinaServices().getRutinaById(lista[i].getIdRutina()[j]);
                }
                HashMap<String, Object> mapa = new HashMap<>();

                mapa.put("id", lista[i].getId());
                mapa.put("nombreSplit", lista[i].getNombreSplit());
                mapa.put("descripcion", lista[i].getDescripcion());
                mapa.put("nroDias", lista[i].getNroDias());
                mapa.put("rutinas", rutinas);
                respuesta[i] = mapa;
            }
            return respuesta;
        }
        return new Object[]{};
    }

    public Split getSplitById(Integer id) throws Exception {
        return get(id);
    }

    public Split saveSplit() throws Exception {
        if(!camposLlenos()) {
            throw new Exception("Los campos están vacíos, por favor completarlos.");
        }
        this.getSplit().setId(JsonFileManager.readAndUpdateCurrentIdOf(className));    
        persist(split);
        return this.split;
    }

    public Split updateSplit() throws Exception {
        Integer id = this.getSplit().getId();
        if(!camposLlenos()) {
            throw new Exception("Los campos están vacíos, por favor completarlos.");
        }
        merge(this.getSplit(), id);
        return this.split;
    }

    public Split deleteSplit(Integer id) throws Exception {
        Split split = get(id);
        remove(id);
        return split;
    }


    // VALIDACIONES
    public Boolean camposLlenos() {
        if(this.getSplit().getNombreSplit() == null) return false;
        if(this.getSplit().getDescripcion() == null) return false;
        if(this.getSplit().getNroDias() == 0) return false;
        if(this.getSplit().getIdRutina() == null) return false;
        return true;
    }


    // ORDENAR
    public Split[] sort(String attribute, Integer orden, Integer method) throws Exception {
        LinkedList<Split> list = listAll();
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
    public Split[] search(String attribute, String value) throws Exception {
        LinkedList<Split> list = listAll();
        try {
            if(attribute.equalsIgnoreCase("nombreSplit")) {
                return list.buscarPorAtributo(attribute, value).toArray();
            } else if (attribute.equalsIgnoreCase("nroDias")) {
                    return list.busquedaLinealBinaria(attribute, Integer.parseInt(value)).toArray();
            } else {
                return list.busquedaLinealBinaria(attribute, value).toArray();
            }
        } catch (Exception e) {
            return new Split[] {};
        }
    }
}