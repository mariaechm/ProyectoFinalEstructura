package com.example.controller.dao.services;

import com.example.controller.dao.PersonaDao;
import com.example.models.Persona;
import com.example.models.enumerator.Genero;
import com.example.models.enumerator.Rol;
import com.example.models.enumerator.TipoIdentificacion;


public class PersonaServices {
    private PersonaDao obj;

    public PersonaServices() {
        this.obj = new PersonaDao();
    }

    public Persona getPersona() {
        return this.obj.getPersona();
    }

    public Persona[] getAllPersonas() {
        return this.obj.getAllPersonas();
    }

    public Persona getPersonaById(Integer id) throws Exception {
        return this.obj.getPersonaById(id);
    }

    public Persona savePersona(String json) throws Exception {
        this.obj.personaFromJson(json);
        return this.obj.savePersona();
    }

    public Persona updatePersona(String json) throws Exception {
        this.obj.personaFromJson(json);
        return this.obj.updatePersona();
    }

    public Persona deletePersona(Integer id) throws Exception {
        return this.obj.deletePersona(id);
    }

    public Genero[] generos() {
        return Genero.values();
    }

    public Rol[] roles() {
        return Rol.values();
    }
    
    public TipoIdentificacion[] tiposIdentificacion() {
        return TipoIdentificacion.values();
    }
}
