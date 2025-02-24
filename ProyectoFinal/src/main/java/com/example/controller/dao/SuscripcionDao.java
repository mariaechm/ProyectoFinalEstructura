package com.example.controller.dao;

import com.example.controller.dao.implement.AdapterDao;
import com.example.controller.dao.implement.JsonFileManager;
import com.example.controller.tda.list.LinkedList;
import com.example.models.Suscripcion;
import com.example.models.enumerator.TipoSuscripcion;

public class SuscripcionDao extends AdapterDao<Suscripcion> {
    private Suscripcion suscripcion;
    private LinkedList<Suscripcion> listAll;

    //Constructor
    public SuscripcionDao(){
        super(Suscripcion.class);

    }

    //Getters 
    public Suscripcion getSuscripcion(){
        if (suscripcion == null) {
            suscripcion = new Suscripcion();
        }
        return this.suscripcion;
    }

    //Metodos de acceso
    public void SuscripcionFromJson(String SuscripcionJson) {
        this.suscripcion = g.fromJson(SuscripcionJson, Suscripcion.class);
    }

    //Crud

    public LinkedList <Suscripcion> getAllSuscripciones() throws Exception {
        return this.listAll();
    }

    public Suscripcion getSuscripcionById(Integer id) throws Exception {
        return get(id);
    }

    public Suscripcion save() throws Exception {
        if(!camposLlenos()) {
            throw new Exception("Los campos están vacíos, por favor completarlos.");
        }
        this.getSuscripcion().setId(JsonFileManager.readAndUpdateCurrentIdOf(className));
        
        suscripcion.setDuracionDias(suscripcion.getTipo().getDuracionDias());
        suscripcion.setPrecio(suscripcion.getTipo().getPrecio());
        suscripcion.setFechaFinalizacion(calcularFechaFinalizacion(suscripcion.getFechaInicio(), suscripcion.getTipo()));

        this.persist(this.suscripcion);
        this.listAll = listAll();
        return this.getSuscripcion();
    }

    public Suscripcion update() throws Exception {
        Integer id = this.getSuscripcion().getId();
        if(!camposLlenos()) {
            throw new Exception("Los campos están vacíos, por favor completarlos.");
        }
        suscripcion.setDuracionDias(suscripcion.getTipo().getDuracionDias());
        suscripcion.setPrecio(suscripcion.getTipo().getPrecio());
        suscripcion.setFechaFinalizacion(calcularFechaFinalizacion(suscripcion.getFechaInicio(), suscripcion.getTipo()));
        merge(this.getSuscripcion(), id);
        return this.suscripcion;
    }
    
    public Suscripcion delete(Integer id) throws Exception {
        Suscripcion suscripcion = get(id);
        remove(id);
        return suscripcion;
    }

    //Enumerations
    public TipoSuscripcion getTipoSuscripcion(String tipoSuscripcion) {
        return TipoSuscripcion.valueOf(tipoSuscripcion);
    }

    public TipoSuscripcion[] getTipos() {
        return TipoSuscripcion.values();
    }

    //VALIDADORES

    public Boolean camposLlenos() {
            if (this.getSuscripcion().getFechaInicio() == null || this.getSuscripcion().getFechaInicio().isEmpty()) return false;
            if (this.getSuscripcion().getTipo() == null) return false;
           // if(this.getSuscripcion().getId() == null) return false;
        return true;
    }

    //Calcular fecha finalizacion a partir de la fecha de inicio y el tipo de suscripcion
    private String calcularFechaFinalizacion(String fechaInicio, TipoSuscripcion tipo) throws Exception {
        if (!isFechaValida(fechaInicio)) {
            throw new Exception ("Fecha de inicio no válida");
        }

        TipoSuscripcion tipoSuscripcion = suscripcion.getTipo();
        int duracionDias = tipo.getDuracionDias();

        //Dividir la fecha de inicio en partes
        String[] partes = fechaInicio.split("-");
        int dia = Integer.parseInt(partes[2]);
        int mes = Integer.parseInt(partes[1]);
        int anio = Integer.parseInt(partes[0]);

        dia += duracionDias;
        while (dia > diasEnMes(mes, anio)) {
            dia -= diasEnMes(mes, anio);
            mes++;
            if (mes > 12) {
                mes = 1;
                anio++;
                
            }
        }
        return String.format("%d-%02d-%02d", anio, mes, dia);
    }

    //Calcular la cantidad de dias en un mes
    private int diasEnMes(int mes, int anio) {
        switch (mes) {
            case 1: case 3: case 5: case 7: case 8: case 10: case 12:
                return 31;
            case 4: case 6: case 9: case 11:
                return 30;
            case 2:
                return (esBisiesto(anio) ? 29 : 28);
            default:
                return 0;
        }
    }

    //Determinar si un año es bisiesto
    private boolean esBisiesto(int anio) {
        return (anio % 4 == 0 && anio % 100 != 0) || (anio % 400 == 0);
    }

    //Validar si la fecha es válida
    private Boolean isFechaValida(String fechaInicio) {
        if (!fechaInicio.matches("\\d{4}-\\d{2}-\\d{2}")) {
            return false;
        }

        String[] partes = fechaInicio.split("-");
        if (partes.length != 3) {
            return false;
        }
        int anio = Integer.parseInt(partes[0]);
        int mes = Integer.parseInt(partes[1]);
        int dia = Integer.parseInt(partes[2]);

        if (partes[0].length() != 4 || anio < 2023 || mes < 1 || mes > 12 || dia < 1 || dia > diasEnMes(mes, anio)) {
            return false;
        }
        return true;
    }
}

  