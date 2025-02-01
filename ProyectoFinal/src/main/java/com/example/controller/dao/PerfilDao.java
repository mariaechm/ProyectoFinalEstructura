package com.example.controller.dao;

import com.example.controller.dao.implement.AdapterDao;
import com.example.controller.dao.implement.JsonFileManager;
import com.example.controller.tda.list.LinkedList;
import com.example.models.Perfil;

public class PerfilDao extends AdapterDao<Perfil> {
    private Perfil perfil;
    private LinkedList listAll;

    public PerfilDao(){
        super(Perfil.class);
    }
    public Perfil getPerfil() {
        if (perfil == null) {
            perfil = new Perfil();
        }
        return this.perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }
    
    public LinkedList getListAll() {
        if (listAll == null) {
            this.listAll = listAll();
        }
        return listAll;
    }
    public Boolean save() throws Exception{
        Integer id = JsonFileManager.readAndUpdateCurrentIdOf(className);
        perfil.setId(id);
        this.persist(this.perfil);
        this.listAll = listAll();
        return true;
    }
    
    public Perfil getPerfilById(Integer id) throws Exception {
        return get(id);
    }
    
    public Boolean update() throws Exception {
        if (getPerfil() == null) {
            throw new Exception("El perfil no puede ser nulo.");
        }
    
        if (getPerfil().getId() == null) {
            throw new Exception("El ID del perfil no puede ser nulo.");
        }
    
        
        this.merge(getPerfil(), getPerfil().getId());
        this.listAll = listAll(); 
        return true;
    }
    
    public Perfil deletePerfil(Integer id) throws Exception {
    
        Perfil perfil = get(id);
        remove(id);
        return perfil;
    }

}