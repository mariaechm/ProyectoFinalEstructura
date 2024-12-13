package com.example.controller.dao.services;

import java.lang.reflect.Array;

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

    public Split[] getAllSplits() throws Exception {
        try {
            return obj.getAllSplits().toArray(); 
        } catch (Exception e) {
            return (Split[])Array.newInstance(Split.class, 0);
        }
    }

    // CRUD
    public Split getSplitById(Integer id) throws Exception {
        return this.obj.getSplitById(id);
    }

    public void saveSplit() throws Exception {
        this.obj.saveSplit();
    }

    public void update() throws Exception {
        this.obj.updateSplit();
    }

    public void deleteSplit(Integer id) throws Exception {        
        this.obj.deleteSplit(id);
    }
}