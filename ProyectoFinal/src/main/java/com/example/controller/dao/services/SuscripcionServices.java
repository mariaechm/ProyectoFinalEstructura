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

    
   /*public LinkedList<Suscripcion> listAll() {
        return obj.getListAll();
    }*/

    //getters 
    public Suscripcion getSuscripcion() {
        return this.obj.getSuscripcion();
    }

   /*public void setSuscripcion(Suscripcion suscripcion) {
        obj.setSuscripcion(suscripcion);
    }*/

    //Crud
    public Suscripcion[] getAllSuscripciones() throws Exception{
        return this.obj.getAllSuscripciones().toArray();
    }

   /* public Object[] showListAll() throws Exception {
        return this.obj.listShowAll();
    }*/

    
    public Suscripcion getSuscripcionById(Integer id) throws Exception{
        return this.obj.getSuscripcionById(id);
    }

    public Suscripcion save (String json) throws Exception{
        this.obj.SuscripcionFromJson(json);
        return this.obj.save();
    }
    /*public Suscripcion update() throws Exception{
        return obj.update();
    }

    public Suscripcion delete(Integer id) throws Exception{
        return obj.delete(id);
    }*/

     public Suscripcion update (String json) throws Exception {
        this.obj.SuscripcionFromJson(json);
        return this.obj.update();
    }

    public Suscripcion delete (Integer id) throws Exception {        
        return this.obj.delete(id);
    }
    
    //Enumerators
    /*public TipoSuscripcion[] tiposSuscripcion() {
        return TipoSuscripcion.values();
    }*/

    public TipoSuscripcion getTipoSuscripcion(String tipoSuscripcion) {
        return obj.getTipoSuscripcion(tipoSuscripcion);
    }

    public TipoSuscripcion[] getTipos() {
        return obj.getTipos();
    }  
    
    // ORDENAR
    public Suscripcion[] sort(String attribute, Integer orden, Integer method) throws Exception {
        return this.obj.sort(attribute, orden, method);
    }

    // BUSCAR 
    public Suscripcion[] search(String attribute, String value) throws Exception {
        return this.obj.search(attribute, value);
    }
} 