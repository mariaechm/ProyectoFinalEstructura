package com.example.rest;

import java.util.HashMap;

import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.example.controller.tda.list.LinkedList;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.FileInputStream;

import com.example.controller.dao.services.SuscripcionServices;
import com.example.models.enumerator.TipoSuscripcion;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.controller.dao.SuscripcionDao;
import com.example.models.Suscripcion;
import com.example.models.*;

@Path("Suscripcion")
public class SuscripcionApi {
    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllEstadistica() throws Exception{
        HashMap map = new HashMap<>();
        SuscripcionServices ps = new SuscripcionServices();
        map.put("msg", "OK");
        map.put("data", ps.listAll().toArray());

        if (ps.listAll().getSize() == 0) {
            map.put("data", new Object[]{});
        }
        ObjectMapper om = new ObjectMapper();
        return Response.ok(om.writeValueAsString(map)).build();

    }

    @Path("/save")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(HashMap map) {
        HashMap res = new HashMap<>();

        /// TODO
        /// VALIDACION

        System.out.println("ASAS");
        SuscripcionDao ps = new SuscripcionDao();
      
        try {

            ps.getSuscripcion().setFechaInicio(map.get("fechaInicio").toString());
            ps.getSuscripcion().setFechaFinalizacion(map.get("fechaFinalizacion").toString());
            ps.getSuscripcion().setTipo((TipoSuscripcion.valueOf(map.get("tipo").toString())));
            

            ps.save();
            res.put("msg", "OK");
            res.put("data", "Suscripcion Registarada");
            return Response.ok(res).build();

        } catch (Exception e) {
            System.out.println("Error" + e.toString());
            res.put("msg", "Error");
            res.put("data", e.toString());
            return Response.status(Status.BAD_REQUEST).entity(res).build();

        }

    }

    @Path("/delete/{id}")
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") Integer id) { // Extraer el id de la URL {id}
        HashMap res = new HashMap<>();

        SuscripcionDao ps = new SuscripcionDao();
      
        try {
            ps.delete(id);
            res.put("msg", "OK");
            res.put("status", "Suscripcion Eliminada");
            ObjectMapper om = new ObjectMapper();
            return Response.ok(om.writeValueAsString(res)).build();

        } catch (Exception e) {
            System.out.println("Error" + e.toString());
            res.put("msg", "Error");
            res.put("data", e.toString());
            return Response.status(Status.BAD_REQUEST).entity(res).build();

        }

    }

    @Path("/update")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(HashMap map) {
        HashMap res = new HashMap<>();

        System.out.println("ASAS");
        SuscripcionDao ps = new SuscripcionDao();
      
        try {
            ps.getSuscripcion().setId(Integer.parseInt(map.get("id").toString()));
            ps.getSuscripcion().setFechaInicio(map.get("fechaInicio").toString());
            ps.getSuscripcion().setFechaFinalizacion(map.get("fechaFinalizacion").toString());
            ps.getSuscripcion().setTipo((TipoSuscripcion.valueOf(map.get("tipo").toString())));
            

            ps.update();
            res.put("msg", "OK");
            res.put("data", "Suscripcion Registarada");
            return Response.ok(res).build();

        } catch (Exception e) {
            System.out.println("Error" + e.toString());
            res.put("msg", "Error");
            res.put("data", e.toString());
            return Response.status(Status.BAD_REQUEST).entity(res).build();

        }
    }

    @Path("/get/{id}")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("id") Integer id) {
        HashMap res = new HashMap<>();

        
        SuscripcionDao ps = new SuscripcionDao();
      
        try {         
            res.put("msg", "OK");
            res.put("data", ps.getById(id));
            ObjectMapper om = new ObjectMapper();
            return Response.ok(om.writeValueAsString(res)).build();

        } catch (Exception e) {
            System.out.println("Error" + e.toString());
            res.put("msg", "Error");
            res.put("data", e.toString());
            return Response.status(Status.BAD_REQUEST).entity(res).build();

        }
    }



    
    
    
}
