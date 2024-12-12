package com.example.controller.dao;

import com.example.controller.dao.implement.AdapterDao;
import com.example.controller.tda.list.LinkedList;
import com.example.models.Suscripcion;
import com.example.models.enumerator.TipoSuscripcion;

public class SuscripcionDao extends AdapterDao<Suscripcion> {
    private Suscripcion suscripcion;
    private LinkedList listAll;

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

    public LinkedList getListAll(){
        if (listAll == null) {
            this.listAll = listAll();
        }

        return listAll;
    }

    public Boolean save () throws Exception {
        Integer id = getListAll().getSize() +1;
        suscripcion.setId(id);
        this.persist(this.suscripcion);
        this.listAll = listAll();
        return true;

    }
    //TODO: CAMBIAR EL TIPO DE RETORNO
    public void update() throws Exception {
        Integer id =  this.getSuscripcion().getId();
        merge(this.suscripcion,id);
    }

    public void delete(Integer id) throws Exception{
        remove(id);
    }

    public Suscripcion getById(Integer id) throws Exception{
        return get(id);
    }
    
}
  