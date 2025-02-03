package com.example.controller.dao;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import com.example.controller.dao.implement.AdapterDao;
import com.example.controller.dao.implement.JsonFileManager;
import com.example.controller.tda.list.LinkedList;
import com.example.models.Persona;
import com.example.models.enumerator.TipoIdentificacion;

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
        validateData();
        this.getPersona().setId(JsonFileManager.readAndUpdateCurrentIdOf(className)); 
        persist(persona);
        new EventoCrudManager().registrarEventoCrud("Se ha registrado la información personal");
        return this.persona;
    }

    public Persona updatePersona() throws Exception {
        Integer id = this.getPersona().getId();
        validateData(true);
        merge(this.persona,id);
        new EventoCrudManager().registrarEventoCrud("Se actualizó con éxito la información personal");
        return this.persona;
    }

    public Persona deletePersona(Integer id) throws Exception {
        Persona p = get(id);
        remove(id);
        new EventoCrudManager().registrarEventoCrud("Se ha eliminado la información personal");
        return p;
    }

    public Persona getPersonaById(Integer id) throws Exception {
        return get(id);
    }

    // VALIDADORES =============================================================================

    public Boolean isThereAllFields() {
        if(this.getPersona().getApellido() == null) return false;
        if(this.getPersona().getNombre() == null) return false;
        if(this.getPersona().getCelular() == null) return false;
        if(this.getPersona().getIdentificacion() == null) return false;
        if(this.getPersona().getDireccion() == null) return false;
        if(this.getPersona().getFechaNacimiento() == null) return false;
        if(this.getPersona().getGenero() == null) return false;
        if(this.getPersona().getTipoIdentificacion() == null) return false;
        return true;
    }

    public boolean charsLength(String str, Integer min, Integer max) {
        return str.length() >= min && str.length() <= max;
    }

    public Boolean isValidDate(String date) {
        final String[] components = date.split("-");
        
        if(components.length != 3) 
            return false;
    
        final String year = components[0];
        final String month = components[1];
        final String day = components[2];
    
        Integer yyyy = 0;
        Integer mm = 0; 
        Integer dd = 0; 
    
        try {
            yyyy = Integer.parseInt(year);
            mm = Integer.parseInt(month); 
            dd = Integer.parseInt(day);
        } catch (NumberFormatException e) {
            return false;
        }
    
        try {
            LocalDate parsedDate = LocalDate.of(yyyy, mm, dd);
            LocalDate currentDate = LocalDate.now();
            LocalDate date15YearsAgo = currentDate.minusYears(15);
    
            if (parsedDate.isAfter(currentDate) || parsedDate.isAfter(date15YearsAgo)) {
                return false;
            }
        } catch (DateTimeParseException | IllegalArgumentException e) {
            return false;
        }
    
        return true;
    }

    public Boolean validNumeric(String numeric) {
        for(int i = 0; i < numeric.length(); i++) {
            Character c = numeric.charAt(i);
            if(!Character.isDigit(c)) return false;
        }
        return true;
    }

    public Boolean isValidIdent(String ident, TipoIdentificacion tipo) {
        final int len = ident.length();
        if(tipo.equals(TipoIdentificacion.CEDULA))
            if(len != 10) return false;

        if(tipo.equals(TipoIdentificacion.PASAPORTE))
            if(len < 6 || len > 9) return false;
        
        if(tipo.equals(TipoIdentificacion.RUC))
            if(len != 13) return false;
        
        return validNumeric(ident);
    }

    public Boolean isValidPhone(String phone) {
        if(phone.length() != 10) return false;
        return validNumeric(phone);
    }

    public Boolean isValidString(String str) {
        return str.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+");
    }

    public Boolean isValidAddress(String address) {
        return address.matches("[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ ,.]+");
    }


    public void validateData(boolean forUpdate) throws Exception {
        LinkedList<Persona> list = listAll();
        
        if (!isThereAllFields()) 
            throw new Exception("Los datos están incompletos, no se guardarán!");
        
        final String identificacion = this.getPersona().getIdentificacion();
        final TipoIdentificacion tipo = this.getPersona().getTipoIdentificacion();

        if (!charsLength(this.getPersona().getNombre(), 3, 50) || !isValidString(this.getPersona().getNombre())) {
            throw new Exception("Nombre no válido");
        } 

        if(!charsLength(this.getPersona().getApellido(), 3, 50) || !isValidString(this.getPersona().getApellido())) {
            throw new Exception("Apellido no válido");
        }

        if(!charsLength(this.getPersona().getDireccion(), 3, 100) || !isValidAddress(this.getPersona().getDireccion())) {
            throw new Exception("Dirección no válida");
        }

        if (!forUpdate) {
            if (list.busquedaBinaria("identificacion", identificacion) != null)
                throw new Exception("Ya existe una persona con identificación: " + identificacion);
        }

        if (!isValidIdent(identificacion,tipo)) 
            throw new Exception("Identificación no válida");

        final String date = this.getPersona().getFechaNacimiento();
        if (!isValidDate(date))
            throw new Exception("Fecha no válida");
        
        final String phone = this.getPersona().getCelular();
        boolean phoneUnique;
        
        if (forUpdate) {
            phoneUnique = true;
        } else {
            phoneUnique = list.buscarPorAtributo("celular", phone).isEmpty();
        }
        
        listAll().buscarPorAtributo("celular", phone).isEmpty();
        if (!isValidPhone(phone) || !phoneUnique) {
            throw new Exception("Número de celular no válido");
        }            
    }  
    
    public void validateData() throws Exception {
        validateData(false);
    }

    // BÚSQUEDA Y ORDENACIÓN ===================================================================

    public Persona[] sort(String attribute, Integer orden, Integer method) throws Exception {
        LinkedList<Persona> list = listAll();
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
                break;
        }
        return list.toArray();
    }

    public Persona[] search(String attribute, String x) throws Exception {
        LinkedList<Persona> list = listAll();
        if (attribute.equalsIgnoreCase("nombre") || attribute.equalsIgnoreCase("apellido")) {
            return list.buscarPorAtributo(attribute, x).toArray();
        } else if (attribute.equalsIgnoreCase("celular") || attribute.equalsIgnoreCase("identificacion")) {
            return new Persona[] {
                list.busquedaBinaria(attribute,x),
            }; 
        } else {
            return list.busquedaLinealBinaria(attribute, x).toArray();
        }
    }
}
