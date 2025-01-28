package com.example.controller.dao.services;

import com.example.controller.dao.SuscripcionDao;
import com.example.models.Suscripcion;
import com.example.models.enumerator.TipoSuscripcion;

public class SuscripcionServices {
    private SuscripcionDao obj;

    //Constructor
    public SuscripcionServices(){
        this.obj = new SuscripcionDao();
    }

    //getters 
    public Suscripcion getSuscripcion() {
        return this.obj.getSuscripcion();
    }

    //Crud
    public Suscripcion[] getAllSuscripciones() throws Exception{
        return this.obj.getAllSuscripciones().toArray();
    }

    
    public Suscripcion getSuscripcionById(Integer id) throws Exception{
        return this.obj.getSuscripcionById(id);
    }

    public Suscripcion save (String json) throws Exception{
        this.obj.SuscripcionFromJson(json);
        return this.obj.save();
    }

     public Suscripcion update (String json) throws Exception {
        this.obj.SuscripcionFromJson(json);
        return this.obj.update();
    }

    public Suscripcion delete (Integer id) throws Exception {        
        return this.obj.delete(id);
    }
    
    //Enumerators
    public TipoSuscripcion getTipoSuscripcion(String tipoSuscripcion) {
        return obj.getTipoSuscripcion(tipoSuscripcion);
    }

    public TipoSuscripcion[] getTipos() {
        return obj.getTipos();
    }  
} 