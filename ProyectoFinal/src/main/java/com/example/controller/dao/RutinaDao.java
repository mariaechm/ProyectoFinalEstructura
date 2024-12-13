package com.example.controller.dao;

import com.example.controller.dao.implement.AdapterDao;
import com.example.controller.tda.list.LinkedList;
import com.example.models.Rutina;
import com.example.models.enumerator.GrupoMuscularObjetivo;
import com.example.models.enumerator.ObjetivoRutina;
import com.example.models.enumerator.TipoEjercicio;

public class RutinaDao extends AdapterDao<Rutina> {
    private Rutina rutina;

    // CONSTRUCTOR
    public RutinaDao() {
        super(Rutina.class);
    }

    // GETTER
    public Rutina getRutina() {
        if(this.rutina == null) {
            this.rutina = new Rutina();
        }
        return this.rutina;
    }

    // METODOS DE ACCESO
    public LinkedList<Rutina> getAllRutinas() throws Exception {
        return this.listAll();
    }

    public void RutinaFromJson(String RutinaJson) {
        this.rutina = g.fromJson(RutinaJson, Rutina.class);
    }

    // CRUD
    public Rutina getRutinaById(Integer id) throws Exception {
        return get(id);
    }

    public void saveRutina() throws Exception {
        Integer id = listAll().getSize() + 1;
        this.getRutina().setId(id);
        if(!camposLlenos()) {
            throw new Exception("Los campos están vacíos, por favor completarlos");
        }
        persist(this.rutina);
    }

    public void updateRutina() throws Exception {
        Integer id = getRutina().getId();
        if(!camposLlenos()) {
            throw new Exception("Los campos están vacíos, por favor completarlos");
        }
        merge(getRutina(), id);
    }

    public void deleteRutina(Integer id) throws Exception {
        remove(id);
    }

    public TipoEjercicio getTipoEjercicio(String tipoEjercicio) {
        return TipoEjercicio.valueOf(tipoEjercicio);
    }

    public GrupoMuscularObjetivo getGrupoMuscularObjetivo(String grupoMuscularObjetivo) {
        return GrupoMuscularObjetivo.valueOf(grupoMuscularObjetivo);
    }
    public GrupoMuscularObjetivo[] getGrupos() {
        return GrupoMuscularObjetivo.values();
    }

    public ObjetivoRutina getObjetivoRutina(String objetivoRutina) {
        return ObjetivoRutina.valueOf(objetivoRutina);
    }

    public ObjetivoRutina[] getObjetivos() {
        return ObjetivoRutina.values();
    }
   

    // VALIDACIONES
    public Boolean camposLlenos() {
        if(this.getRutina().getNombreRutina() == null) return false;
        if(this.getRutina().getDescripcion() == null) return false;
        if(this.getRutina().getNroEjercicios() == 0) return false;
        //if(this.getRutina().getIdEjercicio() == null) return false;
        if(this.getRutina().getGrupoMuscularObjetivo() == null) return false;
        if(this.getRutina().getObjetivoRutina() == null) return false;
        return true;
    }


}