package com.example.controller.dao.services;

import java.util.HashMap;

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

    public HashMap<String,Object> enumerations() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("tipos", TipoIdentificacion.values());
        map.put("roles", Rol.values());
        map.put("generos", Genero.values());
        return map;
    }
}
