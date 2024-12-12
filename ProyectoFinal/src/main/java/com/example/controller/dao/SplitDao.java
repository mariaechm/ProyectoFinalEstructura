package com.example.controller.dao;

import com.example.controller.dao.implement.AdapterDao;
import com.example.controller.tda.list.LinkedList;
import com.example.models.Split;

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
    public LinkedList<Split> getAllSplits() throws Exception {
        return this.listAll();
    }

    public void SplitFromJson(String SplitJson) {
        this.split = g.fromJson(SplitJson, Split.class);
    }

    // CRUD
    public Split getSplitById(Integer id) throws Exception {
        return get(id);
    }

    public void saveSplit() throws Exception {
        Integer id = listAll().getSize() + 1;
        this.getSplit().setId(id);
        if(!camposLlenos()) {
            throw new Exception("Los campos están vacíos, por favor completarlos");
        }
        persist(this.split);
    }

    public void updateSplit() throws Exception {
        Integer id = getSplit().getId();
        if(!camposLlenos()) {
            throw new Exception("Los campos están vacíos, por favor completarlos");
        }
        merge(getSplit(), id);
    }
    
    public void deleteSplit(Integer id) throws Exception {
        remove(id);
    }


    // VALIDACIONES
    public Boolean camposLlenos() {
        if(this.getSplit().getNombreSplit() == null) return false;
        if(this.getSplit().getDescripcion() == null) return false;
        if(this.getSplit().getNroDias() == 0) return false;
        if(this.getSplit().getIdRutina() == 0) return false;
        return true;
    }
}