package com.example.controller.dao.services;

import com.example.controller.dao.SuscripcionDao;
import com.example.controller.tda.list.LinkedList;
import com.example.models.Suscripcion;
import com.example.models.enumerator.TipoSuscripcion;

public class SuscripcionServices {
    private SuscripcionDao obj;

    public SuscripcionServices(){
        obj = new SuscripcionDao();
    }

    public Boolean save () throws Exception{
        return obj.save();
    }

    public LinkedList listAll() {
        return obj.getListAll();
    }

    public Suscripcion getSuscripcion() {
        return obj.getSuscripcion();
    }

    public void setSuscripcion(Suscripcion suscripcion) {
        obj.setSuscripcion(suscripcion);
    }

    public Suscripcion get(Integer id) throws Exception{
        return obj.get(id);
    }

    public void update() throws Exception{
        obj.update();
    }

    public void delete(Integer id) throws Exception{
        obj.delete(id);
    }

    public Suscripcion getById(Integer id) throws Exception{
        return obj.getById(id);
    }
    
} 