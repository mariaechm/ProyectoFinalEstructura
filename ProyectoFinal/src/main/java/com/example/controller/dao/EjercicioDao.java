package com.example.controller.dao;

import com.example.controller.dao.implement.AdapterDao;
import com.example.controller.dao.implement.JsonFileManager;
import com.example.controller.tda.list.LinkedList;
import com.example.models.Ejercicio;
import com.example.models.enumerator.GrupoMuscularObjetivo;
import com.example.models.enumerator.TipoEjercicio;


public class EjercicioDao extends AdapterDao<Ejercicio> {
    private Ejercicio ejercicio;

    // CONSTRUCTOR
    public EjercicioDao() {
        super(Ejercicio.class);
    }

    // GETTER
    public Ejercicio getEjercicio() {
        if(this.ejercicio == null) {
            this.ejercicio = new Ejercicio();
        }
        return this.ejercicio;
    }

    // METODOS DE ACCESO
    public void EjercicioFromJson(String EjercicioJson) {
        this.ejercicio = g.fromJson(EjercicioJson, Ejercicio.class);
    }

    // CRUD
    public Ejercicio[] getAllEjercicios() throws Exception {
        return this.listAll().toArray();
    }

    public Ejercicio getEjercicioById(Integer id) throws Exception {
        return get(id);
    }

    public Ejercicio saveEjercicio() throws Exception {
        validations();
        this.getEjercicio().setId(JsonFileManager.readAndUpdateCurrentIdOf(className));    
        persist(ejercicio);
        return this.ejercicio;
    }

    public Ejercicio updateEjercicio() throws Exception {
        Integer id = this.getEjercicio().getId();
        validations();
        merge(this.getEjercicio(), id);
        return this.ejercicio;
    }

    public Ejercicio deleteEjercicio(Integer id) throws Exception {
        Ejercicio ejercicio = get(id);
        remove(id);
        return ejercicio;
    }

    // ENUMERACIONES
    public GrupoMuscularObjetivo getGrupoMuscularObjetivo(String grupoMuscularObjetivo) {
        return GrupoMuscularObjetivo.valueOf(grupoMuscularObjetivo);
    }
    
    public GrupoMuscularObjetivo[] getGrupos() {
        return GrupoMuscularObjetivo.values();
    }
    
    public TipoEjercicio getTipoEjercicio(String tipoEjercicio) {
        return TipoEjercicio.valueOf(tipoEjercicio);
    }

    public TipoEjercicio[] getTipos() {
        return TipoEjercicio.values();
    }

    // VALIDACIONES
    public Boolean validations() throws Exception {
        if(!camposLlenos()) return false;
        try {
            if(!checkNumbers()) return false;
            if(!checkBlankSpaces()) return false;
            if(!checkSpecialCharacters()) return false;
            if(!checkLength()) return false;
            if(!checkSameName()) return false;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
    
    // VALIR CAMPOS LLENOS
    public Boolean camposLlenos() throws Exception {
        if(this.getEjercicio().getNombreEjercicio() == null) {
            throw new Exception("El nombre del ejercicio no puede estar vacío");
        } 
        if(this.getEjercicio().getDescripcion() == null) {
            throw new Exception("La descripción del ejercicio no puede estar vacía");
        } 
        if(this.getEjercicio().getTiempoDescanso() == 0.0) {
            throw new Exception("El tiempo de descanso debe ser superior a 0");
        } 
        if(this.getEjercicio().getNroSeries() == 0) {
            throw new Exception("El número de series debe ser superior a 0");
        }
        if(this.getEjercicio().getNroRepSerie() == 0) {
            throw new Exception("El número de repeticiones por serie debe ser superior a 0");
        }
        if(this.getEjercicio().getTipoEjercicio() == null) {
            throw new Exception("Seleccione un tipo de ejercicio");
        } 
        
        return true;
    }

    // VALIDAR NÚMEROS
    public Boolean checkNumbers() throws Exception {
        if (this.getEjercicio().getNroRepSerie() <= 0 || this.getEjercicio().getNroRepSerie() > 30 ||
            this.getEjercicio().getTiempoDescanso() <= 0 || this.getEjercicio().getTiempoDescanso() > 6.00 ||
            this.getEjercicio().getNroSeries() <= 0 || this.getEjercicio().getNroSeries() > 12) {
            throw new Exception("Número inválido");
        }
        return true; 
    }

    // VALIDAR QUE EL NOMBRE Y DESCRIPCIÓN NO EMPIECEN CON BLANKSPACES
    public Boolean checkBlankSpaces() throws Exception {
        if(this.getEjercicio().getNombreEjercicio().startsWith(" ")) {
            throw new Exception("El nombre del ejercicio no puede empezar con espacios en blanco");
        }
        if(this.getEjercicio().getDescripcion().startsWith(" ")) {
            throw new Exception("La descripción del ejercicio no puede empezar con espacios en blanco");
        }
        return true;
    }

    // VALIDAR CARACTERES ESPECIALES
    public Boolean checkSpecialCharacters() throws Exception {
        if(this.getEjercicio().getNombreEjercicio().matches(".*[!@#$%^&*()_+=\\[\\]{};':\"\\\\|,.<>\\/?].*")) {
            throw new Exception("El nombre del ejercicio no puede contener caracteres especiales");
        }
        if(this.getEjercicio().getDescripcion().matches("*[!@#$^&*_+=\\[\\]{};'\"\\\\|<>\\/?]*")) {
            throw new Exception("La descripción del ejercicio no puede contener caracteres especiales");
        }
        return true;
    }

    // VALIDAR CANTIDAD DE CARACTERES
    public Boolean checkLength() throws Exception {
        if(this.getEjercicio().getNombreEjercicio().length() < 5 || this.getEjercicio().getNombreEjercicio().length() > 90) {
            throw new Exception("El nombre del ejercicio debe tener entre 5 y 90 caracteres");
        }
        if(this.getEjercicio().getDescripcion().length() < 50 || this.getEjercicio().getDescripcion().length() > 600) {
            throw new Exception("La descripción del ejercicio debe tener entre 50 y 600 caracteres");
        }
        return true;
    }
    
    // VALIDAR QUE NO SE REPITAN LOS NOMBRES DE LOS EJERCICIOS
    public Boolean checkSameName() throws Exception {
        LinkedList<Ejercicio> list = listAll();
        for(Ejercicio ejercicio : list.toArray()) {
            if(ejercicio.getNombreEjercicio().equalsIgnoreCase(this.getEjercicio().getNombreEjercicio())) {
                throw new Exception("El nombre del ejercicio ya existe");
            }
        }
        return true;
    }

    // ORDENAR
    public Ejercicio[] sort(String attribute, Integer orden, Integer method) throws Exception {
        LinkedList<Ejercicio> list = listAll();
        switch (method) {
            case 0:
                list.quickSort(attribute, orden);
                break;
            case 1:
                list.mergeSort(attribute, orden);
                break;
            case 2:
                list.shellSort(attribute, orden);
                break;
            default:
                throw new Exception("Método de ordenamiento no válido");
        }
        return list.toArray();
    }


    // BUSCAR
    public Ejercicio[] search(String attribute, String value) throws Exception {
        LinkedList<Ejercicio> list = listAll();
        try {
            if(attribute.equalsIgnoreCase("nombreEjercicio")) {
                return list.buscarPorAtributo(attribute, value).toArray();
            } else if (attribute.equalsIgnoreCase("nroSeries") || attribute.equalsIgnoreCase("nroRepSerie") ||
                 attribute.equalsIgnoreCase("tiempoDescanso")) {
                    return list.busquedaLinealBinaria(attribute, Integer.parseInt(value)).toArray();
            } else {
                return list.busquedaLinealBinaria(attribute, value).toArray();
            }
        } catch (Exception e) {
            return new Ejercicio[] {};
        }
    }
}