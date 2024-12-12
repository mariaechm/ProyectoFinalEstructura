package com.example.controller.dao;

import com.example.controller.dao.implement.AdapterDao;
import com.example.models.Estadistica;
import com.example.controller.tda.list.LinkedList;

public class EstadisticaDao extends AdapterDao<Estadistica> {
    private Estadistica estadistica;
    private LinkedList listAll;

    public EstadisticaDao(){
        super(Estadistica.class);
    }
    public Estadistica getEstadistica() {
        if (estadistica == null) {
            estadistica = new Estadistica();
        }
        return this.estadistica;
    }
    public void setEstadistica(Estadistica estadistica) {
        this.estadistica = estadistica;
    }
    public LinkedList getListAll() {
        if (listAll == null) {
            this.listAll = listAll();
        }
        return listAll;
    }
    public Boolean save() throws Exception{
        Integer id = getListAll().getSize() + 1;
        estadistica.setId(id);
        this.persist(this.estadistica);
        this.listAll = listAll();
        return true;
    }
    public Boolean update() throws Exception{
        this.merge(getEstadistica(),getEstadistica().getId());
        this.listAll = listAll();
        return true;
    }

    
}