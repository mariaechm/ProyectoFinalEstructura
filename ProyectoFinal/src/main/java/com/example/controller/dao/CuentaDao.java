package com.example.controller.dao;

import java.util.regex.Pattern;

import com.example.controller.dao.implement.AdapterDao;
import com.example.controller.dao.implement.JsonFileManager;
import com.example.models.Cuenta;
import com.example.models.Persona;

public class CuentaDao extends AdapterDao<Cuenta> {
    private Cuenta cuenta;

    public CuentaDao() {
        super(Cuenta.class);
    }
    
    // GET & SET (SET FROM JSON) =================================================================

    public Cuenta getCuenta() {
        return (this.cuenta == null) ? new Cuenta() : this.cuenta;  
    }

    public void cuentaFromJson(String json) {
        this.cuenta = g.fromJson(json,Cuenta.class);
    }

    //  CRUD METHODS =========================================================================
    
    public Cuenta[] getAllCuentas() {
        return listAll().toArray();
    }

    public Cuenta saveCuenta() throws Exception {
        this.getCuenta().setId(JsonFileManager.readAndUpdateCurrentIdOf(className));
        validateData();
        persist(cuenta);
        return this.cuenta;
    }

    public Cuenta updateCuenta() throws Exception {
        Integer id = this.getCuenta().getId();
        validateData(true);
        merge(this.cuenta,id);
        return this.cuenta;
    }

    public Cuenta deleteCuenta(Integer id) throws Exception {
        Cuenta p = get(id);
        remove(id);
        return p;
    }

    public Cuenta getCuentaById(Integer id) throws Exception {
        return get(id);
    }

    // VALIDADORES ================================================================

    public Boolean isThereAllFields() {
        if (this.getCuenta().getCorreoElectronico() == null) return false;
        if (this.getCuenta().getContrasena() == null) return false;
        if (this.getCuenta().getFechaCreacion() == null) return false;
        if (this.getCuenta().getId() == null) return false;
        return true;
    }
    
    public Boolean isValidEmail(String email) {
        final String PATTERN = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        if( email.equals("") || email.isEmpty()) {
            return false;
        }
        return Pattern.matches(PATTERN, email);
    }

    public Boolean isValidPassword(String password) {
        if (password.length() < 8) return false;
        return true;
    } 

    public Boolean existsPersonaWith(Integer idPersona) {
        try {
            Persona p = new PersonaDao().getPersonaById(idPersona);
            if(p == null) return false;
            
            return true;
        } catch (Exception e) {
            System.out.println("CuentaDao.existsPersonaWith(PersonaId) dice: " + e.getMessage());
            return false;
        }
    }

    public Boolean existeCuentaWithPersonaId(Integer idPersona) {
        try {
            if (listAll().busquedaBinaria("PersonaId", idPersona) != null) 
                return true;
        } catch (Exception e) {
            System.out.println("CuentaDao.existeCuentaWithPersona(Id) dice: " + e.getMessage());
        }
        return false;
    }

    public void validateData(boolean forUpdate) throws Exception {
        if (!isThereAllFields())
            throw new Exception("Los datos están incompletos, no se guardarán");

        final Integer personaId = this.getCuenta().getId();
        if (!existsPersonaWith(personaId)) 
            throw new Exception("No existe registro de Persona con Id: " + personaId);
        
        if(!forUpdate) {
        if (existeCuentaWithPersonaId(personaId))
            throw new Exception("Ya existe una cuenta asociada a IdPersona=" + personaId);
        }
        
        final String email = this.getCuenta().getCorreoElectronico();
        if (!isValidEmail(email))
            throw new Exception("Email no válido");

        final String password = this.getCuenta().getContrasena();
        if (!isValidPassword(password)) 
            throw new Exception("Contraseña no válida");        
    }

    public void validateData() throws Exception {
        validateData(false);
    }

    // BÚSQUEDA Y ORDENACIÓN ============================================================
}