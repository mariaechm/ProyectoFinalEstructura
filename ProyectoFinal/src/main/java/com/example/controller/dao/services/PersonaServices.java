/* 
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

    public void setPersona(Persona persona) {
        this.obj.setPersona(persona);
    }

    public Persona getPersonaById(Integer id) throws Exception {
        return this.obj.getPersonaById(id);
    }

    public String getPersonaJsonById(Integer id) throws Exception {
        return this.obj.getPersonaJsonById(id);
    }

    public Boolean save() throws Exception {
        return this.obj.save();
    }

    public void update(Persona persona) throws Exception {
        this.obj.updatePersona(persona);
    }

    public void delete(Integer id) throws Exception {
        this.obj.deletePersona(id);
    }

    public Genero[] generos() {
        return this.obj.generos();
    }

    public Rol[] roles() {
        return this.obj.roles();
    }
    
    public TipoIdentificacion[] tiposIdentificacion() {
        return this.obj.tiposIdentificaion();
    }
}
 */