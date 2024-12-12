package com.example.controller.dao;

import com.example.controller.dao.implement.AdapterDao;
import com.example.models.Persona;

public class PersonaDao extends AdapterDao<Persona> {
    private Persona persona;

    public PersonaDao() {
        super(Persona.class);
    }
    
    // GET & SET (SET FROM JSON) =================================================================

    public Persona getPersona() {
        return (this.persona == null) ? new Persona() : this.persona;  
    }

    public void personaFromJson(String json) {
        this.persona = g.fromJson(json,Persona.class);
    }

    //  CRUD METHODS =========================================================================
    
    public Persona[] getAllPersonas() {
        return listAll().toArray();
    }

    public Persona savePersona() throws Exception {
        this.getPersona().setId(listAll().getSize()+1);
        persist(persona);
        return this.persona;
    }

    public Persona updatePersona() throws Exception {
        Integer id = this.getPersona().getId();
        merge(this.persona,id);
        return this.persona;
    }

    public Persona deletePersona(Integer id) throws Exception {
        Persona p = get(id);
        remove(id);
        return p;
    }

    public Persona getPersonaById(Integer id) throws Exception {
        return get(id);
    }
}