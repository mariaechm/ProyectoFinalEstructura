
package com.example.controller.dao.services;

import com.example.controller.dao.EstadisticaDao;
import com.example.models.Estadistica;
import com.example.controller.tda.list.LinkedList;

public class EstadisticaServices {
    private EstadisticaDao obj;

    public EstadisticaServices() {
        obj = new EstadisticaDao();
    }

    public Boolean save() throws Exception {
        return obj.save();
    }

    public LinkedList listAll() {
        return obj.getListAll();
    }

    public Estadistica getEstadistica() {
        return obj.getEstadistica();
    }

    public void setEstadistica(Estadistica estadistica) {
        obj.setEstadistica(estadistica);
    }

    public Estadistica get(Integer id) throws Exception {
        return obj.get(id);
    }

}