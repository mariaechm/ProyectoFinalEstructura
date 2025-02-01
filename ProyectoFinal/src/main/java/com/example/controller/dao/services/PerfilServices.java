package com.example.controller.dao.services;

import com.example.controller.dao.PerfilDao;
import com.example.controller.tda.list.LinkedList;
import com.example.models.Perfil;

public class PerfilServices {

    private PerfilDao obj;

    public PerfilServices() {
        this.obj = new PerfilDao();
    }

    public Boolean save() throws Exception {
        return obj.save();
    }

    public LinkedList listAll() {
        return obj.getListAll();
    }

    public Perfil getPerfil() {
        return obj.getPerfil();
    }

    public void setPerfil(Perfil perfil) {
        obj.setPerfil(perfil);
    }

    public Perfil get(Integer id) throws Exception {
        return obj.get(id);
    }

    public Perfil deletePerfil(Integer id) throws Exception {

    return this.obj.deletePerfil(id);
    }

}
