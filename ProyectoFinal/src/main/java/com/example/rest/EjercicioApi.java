package com.example.rest;

import java.util.HashMap;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.controller.dao.services.EjercicioServices;


@Path("/ejercicios")
public class EjercicioApi {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/all")
    public Response getAllEjercicios() throws Exception {
        EjercicioServices es = new EjercicioServices();
        HashMap<String,Object> responseMap = new HashMap<>();
        ObjectMapper om = new ObjectMapper();

        try {
            responseMap.put("status","OK");
            if(es.getAllEjercicios() == null) {
                responseMap.put("data",new Object[0]);
                return Response.ok(om.writeValueAsString(responseMap)).build();
            }
            responseMap.put("data",es.getAllEjercicios());
            return Response.ok(om.writeValueAsString(responseMap)).build();
        } catch (Exception e) {
            e.printStackTrace();
            responseMap.put("status","ERROR");
            responseMap.put("data",e.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                            .entity(om.writeValueAsString(responseMap)).build();
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/save")
    public Response saveEjercicio(String ejercicioJson) throws Exception {
        EjercicioServices es = new EjercicioServices();
        HashMap<String,Object> responseMap = new HashMap<>();
        ObjectMapper om = new ObjectMapper();

        try {
            es.EjercicioFromJson(ejercicioJson);
            es.saveEjercicio();
            responseMap.put("status","OK");
            responseMap.put("data",es.getEjercicio());
            return Response.ok(om.writeValueAsString(responseMap)).build();
        } catch (Exception e) {
            e.printStackTrace();
            responseMap.put("status","ERROR");
            responseMap.put("data",e.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                            .entity(om.writeValueAsString(responseMap)).build();
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/delete/{id}")
    public Response deleteEjercicio(@PathParam("id") Integer id) throws Exception {
        EjercicioServices es = new EjercicioServices();
        HashMap<String,Object> responseMap = new HashMap<>();
        ObjectMapper om = new ObjectMapper();

        try {
            responseMap.put("status","OK");
            responseMap.put("data",es.getEjercicioById(id));
            es.deleteEjercicio(id);
            return Response.ok(om.writeValueAsString(responseMap)).build();
        } catch (Exception e) {
            e.printStackTrace();
            
            responseMap.put("status","ERROR");
            responseMap.put("data",e.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                            .entity(om.writeValueAsString(responseMap)).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/get/{id}")
    public Response getEjercicioById(@PathParam("id") Integer id) throws Exception {
        EjercicioServices es = new EjercicioServices();
        HashMap<String,Object> responseMap = new HashMap<>();
        ObjectMapper om = new ObjectMapper();

        try {
            responseMap.put("status","OK");
            responseMap.put("data",es.getEjercicioById(id));
            return Response.ok(om.writeValueAsString(responseMap)).build();
        } catch (Exception e) {
            responseMap.put("status","ERROR");
            responseMap.put("data",e.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                            .entity(om.writeValueAsString(responseMap)).build();
        }
    }


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/update/")
    public Response getAllEjercicios(String ejercicioJson) throws Exception {
        EjercicioServices es = new EjercicioServices();
        HashMap<String,Object> responseMap = new HashMap<>();
        ObjectMapper om = new ObjectMapper();

        try {
            es.EjercicioFromJson(ejercicioJson);
            es.update();
            responseMap.put("status","OK");
            responseMap.put("data",es.getEjercicio());
            return Response.ok(om.writeValueAsString(responseMap)).build();
        } catch (Exception e) {
            responseMap.put("status","ERROR");
            responseMap.put("data",e.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                            .entity(om.writeValueAsString(responseMap)).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/typeEjercicio")
    public Response enumerations() throws Exception {
        EjercicioServices es = new EjercicioServices();
        HashMap<String,Object> responseMap = new HashMap<>();
        ObjectMapper om = new ObjectMapper();

        try {
            responseMap.put("status", "OK");
            responseMap.put("data",es.getTipos());

            return Response.ok(om.writeValueAsString(responseMap)).build();
        } catch (Exception e) {
            e.printStackTrace();
            responseMap.put("status", "ERROR");
            responseMap.put("data", e.getMessage());

            return Response.status(Status.INTERNAL_SERVER_ERROR)
            .entity(responseMap).build();
        }
    }
}