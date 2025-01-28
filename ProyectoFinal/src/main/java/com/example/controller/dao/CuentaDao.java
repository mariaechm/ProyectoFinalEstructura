package com.example.controller.dao;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.regex.Pattern;

import com.example.controller.auth.JWTManager;
import com.example.controller.dao.implement.AdapterDao;
import com.example.controller.dao.implement.JsonFileManager;
import com.example.controller.tda.list.LinkedList;
import com.example.models.Cuenta;
import com.example.models.Estadistica;
import com.example.models.Perfil;
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
        String contrasena = this.getCuenta().getContrasena();
        this.getCuenta().setContrasena(JWTManager.base64Encode(contrasena));
        validateData();
        this.getCuenta().setId(JsonFileManager.readAndUpdateCurrentIdOf(className));
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
        if (this.getCuenta().getPersonaId() == null) return false;
        if (this.getCuenta().getPerfilId() == null) return false;
        if (this.getCuenta().getCorreoElectronico() == null) return false;
        if (this.getCuenta().getContrasena() == null) return false;
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

    // Validar si existe una persona en registrada con el idPersona
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

    // Validar si ya está registrada una cuenta con algún atributo que no debe duplicarse
    public Boolean existeCuentaWith(String attribute, Object x) {
        try {
            if (listAll().busquedaBinaria(attribute, x) != null) 
                return true;
        } catch (Exception e) {
            System.out.println("CuentaDao.existeCuentaWithPersona(Id) dice: " + e.getMessage());
        }
        return false;
    }

    public void validateData(boolean forUpdate) throws Exception {
        if (!isThereAllFields())
            throw new Exception("Los datos están incompletos, no se guardarán");

        final Integer personaId = this.getCuenta().getPersonaId();
        if (!existsPersonaWith(personaId)) 
            throw new Exception("No existe registro de Persona con Id: " + personaId);

        final String email = this.getCuenta().getCorreoElectronico();
        if (!isValidEmail(email))
            throw new Exception("Email no válido");  
        
        if(!forUpdate) {
            if (existeCuentaWith("PersonaId",personaId))
                throw new Exception("Ya existe una cuenta asociada a IdPersona=" + personaId);

            if(existeCuentaWith("CorreoElectronico", email)) 
                throw new Exception("Ya existe una cuenta con el Email: " + email); 

            final Integer perfilId = this.getCuenta().getPerfilId();
            if(existeCuentaWith("PerfilId", perfilId))
                throw new Exception("Ya existe una cuenta asociada a IdPerfil=");
        }

        final String password = this.getCuenta().getContrasena();
        if (!isValidPassword(password)) 
            throw new Exception("Contraseña no válida");        
    }

    public void validateData() throws Exception {
        validateData(false);
    }

    // BÚSQUEDA Y ORDENACIÓN ============================================================

    public Cuenta[] sort(String attribute, Integer orden, Integer method) throws Exception {
        LinkedList<Cuenta> list = listAll();
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
    
    public Cuenta[] search(String attribute, String x) throws Exception {
        LinkedList<Cuenta> list = listAll();
        if (attribute.equalsIgnoreCase("correoElectronico")) {
            return new Cuenta[] {
                list.busquedaBinaria(attribute,x),
            }; 
        } else if(attribute.equalsIgnoreCase("personaId") || attribute.equalsIgnoreCase("perfilId")) {
            return new Cuenta[] {
                list.busquedaBinaria(attribute, Integer.parseInt(x)),
            };
        } else {
            return list.busquedaLinealBinaria(attribute, x).toArray();
        }
    }

    // AUTENTICACIÓN Y AUTORIZACIÓN =======================================================

    public String validateCredentialsAndGetToken(String json) throws Exception {
        Cuenta cuenta = this.g.fromJson(json, Cuenta.class);
        Cuenta registered = listAll().busquedaBinaria("correoElectronico", cuenta.getCorreoElectronico()); 
        if(registered != null) {
            if(!registered.getContrasena().equals(JWTManager.base64Encode(cuenta.getContrasena()))) {
                throw new Exception("Credenciales inválidas");
            } else {
                return JWTManager.createToken(registered, 60);
            }
        }

        throw new Exception("Credenciales inválidas");
    }

    public HashMap<String,Object> getUserInfo(Integer cuentaId) throws Exception {
        Cuenta cuenta = getCuentaById(cuentaId);
        Persona persona = new PersonaDao().getPersonaById(cuenta.getPersonaId());
        Perfil perfil = new PerfilDao().get(cuenta.getPerfilId());

        HashMap<String,Object> map = new HashMap<>();

        map.put("cuenta", cuenta);
        map.put("persona", persona);
        map.put("perfil",perfil);

        return map;
    }

    // TODO: IMPLEMENTAR MÉTODOS EN DAOS DE PERSONA, PERFIL Y ESTADÍSTICA
    public HashMap<String, Object> registerNewUser(String json) throws Exception {
        try {
            PersonaDao pd = new PersonaDao();
            PerfilDao pfd = new PerfilDao();
            EstadisticaDao ed = new EstadisticaDao();
            
            this.cuentaFromJson(json);
            pd.personaFromJson(json);
            pfd.setPerfil(new Perfil(0, pd.getPersona().getNombre(), "user.png", "Objetivo ...", LocalDateTime.now().toString().substring(0,10)));
            ed.setEstadistica(new Estadistica(0,0f,0f,0f,0f,0f,0f));

            //TODO: ESTOS METODOS
            //pfd.perfilWithGenericValues(); 

            //ed.estadisticaWithGenericValues();

            //pfd.getPerfil().setNickName(pd.getPersona().getNombre());

            pd.savePersona();
            pfd.save();

            //ed.getEstadistica().setPerfilId(pfd.getPerfil().getId());
            ed.save();

            this.getCuenta().setPerfilId(pfd.getPerfil().getId());
            this.getCuenta().setPersonaId(pd.getPersona().getId());
            this.saveCuenta();

            HashMap<String, Object> res = new HashMap<>();
            res.put("cuenta", this.getCuenta());
            res.put("persona", pd.getPersona());
            res.put("perfil", pfd.getPerfil());

            return res;

        } catch (Exception e) {
            throw new Exception("No se pudo completar el registro, " + e.getMessage());
        }
    }
    
    @SuppressWarnings("unchecked")
    public Cuenta changePassword(String json) throws Exception {
        HashMap<String, Object> map = this.g.fromJson(json, HashMap.class);
        String newContrasena = map.get("newContrasena").toString();
        Cuenta cuenta = this.g.fromJson(json, this.getCuenta().getClass());
        System.out.println(cuenta);
        Cuenta registered = get(cuenta.getId());

        System.out.println(registered);

        if(registered != null) {
            if(registered.getContrasena().equals(JWTManager.base64Encode(cuenta.getContrasena()))) {
                registered.setContrasena(JWTManager.base64Encode(newContrasena));
                this.cuenta = registered;
                updateCuenta();
                return registered;
            }
        }

        throw new Exception("La contraseña es incorrecta, no se actualizará!");
    }
}