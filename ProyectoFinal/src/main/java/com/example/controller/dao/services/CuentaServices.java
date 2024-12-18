package com.example.controller.dao.services;

import com.example.controller.dao.CuentaDao;
import com.example.models.Cuenta;

public class CuentaServices {
    private CuentaDao obj;

    public CuentaServices() {
        this.obj = new CuentaDao();
    }

    public Cuenta getCuenta() {
        return this.obj.getCuenta();
    }

    public Cuenta[] getAllCuentas() {
        return this.obj.getAllCuentas();
    }

    public Cuenta getCuentaById(Integer id) throws Exception {
        return this.obj.getCuentaById(id);
    }

    public Cuenta saveCuenta(String json) throws Exception {
        this.obj.cuentaFromJson(json);
        return this.obj.saveCuenta();
    }

    public Cuenta updateCuenta(String json) throws Exception {
        this.obj.cuentaFromJson(json);
        return this.obj.updateCuenta();
    }

    public Cuenta deleteCuenta(Integer id) throws Exception {
        return this.obj.deleteCuenta(id);
    }

    // BUSQUEDA Y ORDENACIÓN ==========================================================

    public Cuenta[] sort(String attribute, Integer orden, Integer method) throws Exception {
        return this.obj.sort(attribute,orden,method);
    }

    public Cuenta[] search(String attribute, String x) throws Exception {
        return this.obj.search(attribute, x);
    }

}

