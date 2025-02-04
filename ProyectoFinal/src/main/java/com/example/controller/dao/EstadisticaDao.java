package com.example.controller.dao;

import com.example.controller.dao.implement.AdapterDao;
import com.example.controller.dao.implement.JsonFileManager;
import com.example.models.Estadistica;
import com.example.controller.tda.list.LinkedList;

public class EstadisticaDao extends AdapterDao<Estadistica> {
    private Estadistica estadistica;
    private LinkedList listAll;

    public EstadisticaDao(){
        super(Estadistica.class);
    }
    public Estadistica getEstadistica() {
        if (estadistica == null) {
            estadistica = new Estadistica();
        }
        return this.estadistica;
    }
    public void setEstadistica(Estadistica estadistica) {
        this.estadistica = estadistica;
    }
    public LinkedList getListAll() {
        if (listAll == null) {
            this.listAll = listAll();
        }
        return listAll;
    }
    public Boolean save() throws Exception{
        Integer id = JsonFileManager.readAndUpdateCurrentIdOf(className);
        validations();
        estadistica.setId(id);
        this.persist(this.estadistica);
        this.listAll = listAll();
        return true;
    }

    public Boolean update() throws Exception{
        validations();
        this.merge(getEstadistica(),getEstadistica().getId());
        this.listAll = listAll();
        return true;
    }
    public Boolean deleteEstadistica(Integer id) throws Exception{
        this.remove(id);
        this.listAll = listAll();
        return true;
    }

    public Boolean validations()throws Exception{
        if(!camposLlenos())return false;
        try {
            if(!checkBlanceSpaces())return false;
            if(!camposValidos())return false;
            if(!camposValidosMedidas())return false;
            if(!checkSpecialCharacteres())return false;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
      
    }

    public Boolean checkBlanceSpaces() throws Exception {
        if (Float.toString(this.getEstadistica().getMedidaEspalda()).startsWith(" ")) {
            throw new Exception("El campo medida espalda no puede empezar con espacios en blanco");
        }
        if (Float.toString(this.getEstadistica().getMedidaPierna()).startsWith(" ")) {
            throw new Exception("El campo medida pierna no puede empezar con espacios en blanco");
        }
        if (Float.toString(this.getEstadistica().getMedidaBrazo()).startsWith(" ")) {
            throw new Exception("El campo medida brazo no puede empezar con espacios en blanco");
        }
        if (Float.toString(this.getEstadistica().getMedidaCintura()).startsWith(" ")) {
            throw new Exception("El campo medida cintura no puede empezar con espacios en blanco");
        }
        if (Float.toString(this.getEstadistica().getMedidaPecho()).startsWith(" ")) {
            throw new Exception("El campo medida pecho no puede empezar con espacios en blanco");
        }
        if (Float.toString(this.getEstadistica().getPeso()).startsWith(" ")) {
            throw new Exception("El campo peso no puede empezar con espacios en blanco");
        }
        if (Float.toString(this.getEstadistica().getAltura()).startsWith(" ")) {
            throw new Exception("El campo altura no puede empezar con espacios en blanco");
        }

        return true;
    }

    public Boolean camposLlenos() throws Exception{
        if(this.getEstadistica().getMedidaEspalda() == 0.0f)
        {
          throw new Exception("El campo medida espalda no puede estar vacio");  
        }
        if(this.getEstadistica().getMedidaPierna() == 0.0f)
        {
          throw new Exception("El campo medida pierna no puede estar vacio");  
        }
        if(this.getEstadistica().getMedidaBrazo() == 0.0f)
        {
          throw new Exception("El campo medida brazo no puede estar vacio");  
        }
        if(this.getEstadistica().getMedidaCintura() == 0.0f)
        {
          throw new Exception("El campo medida cintura no puede estar vacio");  
        }
        if(this.getEstadistica().getMedidaPecho() == 0.0f)
        {
          throw new Exception("El campo medida pecho no puede estar vacio");  
        }
        if(this.getEstadistica().getPeso() == 0.0f)
        {
          throw new Exception("El campo peso no puede estar vacio");  
        }
        if(this.getEstadistica().getAltura() == 0.0f)
        {
          throw new Exception("El campo altura no puede estar vacio");  
        }
        return true;
    }

    public Boolean camposValidos() throws Exception{
        if(this.getEstadistica().getMedidaEspalda() < 0.0f)
        {
          throw new Exception("El campo medida espalda no puede ser negativo");  
        }
        if(this.getEstadistica().getMedidaPierna() < 0.0f)
        {
          throw new Exception("El campo medida pierna no puede ser negativo");  
        }
        if(this.getEstadistica().getMedidaBrazo() < 0.0f)
        {
          throw new Exception("El campo medida brazo no puede ser negativo");  
        }
        if(this.getEstadistica().getMedidaCintura() < 0.0f)
        {
          throw new Exception("El campo medida cintura no puede ser negativo");  
        }
        if(this.getEstadistica().getMedidaPecho() < 0.0f)
        {
          throw new Exception("El campo medida pecho no puede ser negativo");  
        }
        if(this.getEstadistica().getPeso() < 0.0f)
        {
          throw new Exception("El campo peso no puede ser negativo");  
        }
        if(this.getEstadistica().getAltura() < 0.0f)
        {
          throw new Exception("El campo altura no puede ser negativo");  
        }
        return true;
    }

    public Boolean camposValidosMedidas() throws Exception{
        if(this.getEstadistica().getMedidaEspalda() > 100.0f)
        {
          throw new Exception("El campo medida espalda no puede ser mayor a 100");  
        }
        if(this.getEstadistica().getMedidaPierna() > 200.0f)
        {
          throw new Exception("El campo medida pierna no puede ser mayor a 200");  
        }
        if(this.getEstadistica().getMedidaBrazo() > 200.0f)
        {
          throw new Exception("El campo medida brazo no puede ser mayor a 200");  
        }
        if(this.getEstadistica().getMedidaCintura() > 200.0f)
        {
          throw new Exception("El campo medida cintura no puede ser mayor a 200");  
        }
        if(this.getEstadistica().getMedidaPecho() > 200.0f)
        {
          throw new Exception("El campo medida pecho no puede ser mayor a 200");  
        }
        return true;
    }

    public Boolean checkSpecialCharacteres() throws Exception {
        if (Float.toString(this.getEstadistica().getMedidaEspalda()).matches(".*[^0-9.].*")) {
            throw new Exception("El campo medida espalda no puede contener caracteres especiales");
        }
        if (Float.toString(this.getEstadistica().getMedidaPierna()).matches(".*[^0-9.].*")) {
            throw new Exception("El campo medida pierna no puede contener caracteres especiales");
        }
        if (Float.toString(this.getEstadistica().getMedidaBrazo()).matches(".*[^0-9.].*")) {
            throw new Exception("El campo medida brazo no puede contener caracteres especiales");
        }
        if (Float.toString(this.getEstadistica().getMedidaCintura()).matches(".*[^0-9.].*")) {
            throw new Exception("El campo medida cintura no puede contener caracteres especiales");
        }
        if (Float.toString(this.getEstadistica().getMedidaPecho()).matches(".*[^0-9.].*")) {
            throw new Exception("El campo medida pecho no puede contener caracteres especiales");
        }
        if (Float.toString(this.getEstadistica().getPeso()).matches(".*[^0-9.].*")) {
            throw new Exception("El campo peso no puede contener caracteres especiales");
        }
        if (Float.toString(this.getEstadistica().getAltura()).matches(".*[^0-9.].*")) {
            throw new Exception("El campo altura no puede contener caracteres especiales");
        }
    
        return true;
    }
    
}