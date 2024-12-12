package com.example.controller.dao;

import com.example.controller.dao.implement.AdapterDao;
import com.example.models.Cuenta;

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
        this.getCuenta().setId(listAll().getSize()+1);
        persist(cuenta);
        return this.cuenta;
    }

    public Cuenta updateCuenta() throws Exception {
        Integer id = this.getCuenta().getId();
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
}