package com.example.controller.dao;

import com.example.controller.dao.implement.AdapterDao;
import com.example.controller.dao.implement.JsonFileManager;
import com.example.controller.tda.list.LinkedList;
import com.example.models.Suscripcion;

public class SuscripcionDao extends AdapterDao<Suscripcion> {
    private Suscripcion suscripcion;
    private LinkedList<Suscripcion> listAll;

    public SuscripcionDao(){
        super(Suscripcion.class);

    }

    public Suscripcion getSuscripcion(){
        if (suscripcion == null) {
            suscripcion = new Suscripcion();
        }
        return this.suscripcion;
    }

    public void setSuscripcion( Suscripcion Suscripcion){
        this.suscripcion = Suscripcion;
    }

    public LinkedList<Suscripcion> getListAll(){
        if (listAll == null) {
            this.listAll = listAll();
        }

        return listAll;
    }

    public Suscripcion save () throws Exception {
        Integer id = JsonFileManager.readAndUpdateCurrentIdOf(className);
        suscripcion.setId(id);
        this.persist(this.suscripcion);
        this.listAll = listAll();
        return this.getSuscripcion();

    }

    public Suscripcion update() throws Exception {
        Integer id =  this.getSuscripcion().getId();
        return merge(this.suscripcion,id);
    }

    public Suscripcion delete(Integer id) throws Exception{
        return remove(id);
    }

    public Suscripcion getById(Integer id) throws Exception {
        return get(id);
    }
    
}
  