package com.example.controller.dao;

import java.time.LocalDateTime;

import com.example.controller.dao.implement.AdapterDao;
import com.example.controller.dao.implement.JsonFileManager;
import com.example.models.EventoCrud;

public class EventoCrudManager extends AdapterDao<EventoCrud> {

    private EventoCrud eventoCrud;
    
    public EventoCrudManager() {
        super(EventoCrud.class);
    }

    public EventoCrud getEventoCrud() {
        return (this.eventoCrud == null) ? new EventoCrud() : this.eventoCrud;
    }

    public void setEventoCrud(EventoCrud eventoCrud) {
        this.eventoCrud = eventoCrud;
    }

    public EventoCrud[] eventosCrud() {
        return getArray();
    }

    private EventoCrud nuevoEventoCrud(String descripcion) {
        String[] dateTime = LocalDateTime.now().toString().split("T");
        return new EventoCrud(dateTime[0], dateTime[1].substring(1,8), descripcion);
    }

    public void registrarEventoCrud(String descripcion) throws Exception {
        Integer id = JsonFileManager.readAndUpdateCurrentIdOf(className);
        EventoCrud ec = nuevoEventoCrud(descripcion);
        ec.setId(id);
        persist(ec);
    }
    
    public void eliminarEventoCrud(Integer id) throws Exception {
        remove(id);
    }

}
