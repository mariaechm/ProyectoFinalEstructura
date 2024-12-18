package com.example.controller.dao.services;

import com.example.controller.dao.SplitDao;
import com.example.models.Split;

public class SplitServices {
    private SplitDao obj;

    // CONSTRUCTOR
    public SplitServices() {
        this.obj = new SplitDao();
    }

    // GETTER
    public Split getSplit() {
        return this.obj.getSplit();
    }

    public void SplitFromJson(String SplitJson) {
        this.obj.SplitFromJson(SplitJson);
    }

    // CRUD
    public Split[] getAllSplits() throws Exception {
        return this.obj.getAllSplits().toArray();
    }

    public Object[] showListAll() throws Exception {
        return this.obj.listShowAll();
    }

    public Split getSplitById(Integer id) throws Exception {
        return this.obj.getSplitById(id);
    }

    public Split saveSplit(String json) throws Exception {
        this.obj.SplitFromJson(json);
        return this.obj.saveSplit();
    }

    public Split updateSplit(String json) throws Exception {
        this.obj.SplitFromJson(json);
        return this.obj.updateSplit();
    }

    public Split deleteSplit(Integer id) throws Exception {        
        return this.obj.deleteSplit(id);
    }


    // ORDENAR
    public Split[] sort(String attribute, Integer orden, Integer method) throws Exception {
        return this.obj.sort(attribute, orden, method);
    }

    // BUSCAR
    public Split[] search(String attribute, String value) throws Exception {
        return this.obj.search(attribute, value);
    }
}