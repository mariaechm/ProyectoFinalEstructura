package com.example.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.example.controller.dao.services.EjercicioServices;
import com.example.rest.response.ResponseFactory;


@Path("/ejercicios")
public class EjercicioApi {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/all")
    public Response getAll() {
        return ResponseFactory.buildResponse(new EjercicioServices(), "getAllEjercicios");
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/get/{id}")
    public Response getById(@PathParam("id") Integer id) {
        return ResponseFactory.buildResponse(new EjercicioServices(), "getEjercicioById", id);
    }


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/save")
    public Response save(String ejercicioJson) {
        return ResponseFactory.buildResponse(new EjercicioServices(), "saveEjercicio", ejercicioJson);
    }


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/update")
    public Response update(String ejercicioJson){
        return ResponseFactory.buildResponse(new EjercicioServices(), "updateEjercicio", ejercicioJson);
    }


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/delete/{id}")
    public Response delete(@PathParam("id") Integer id) {
        return ResponseFactory.buildResponse(new EjercicioServices(), "deleteEjercicio", id);
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/typeEjercicio")
    public Response tipoEjercicio() {
        return ResponseFactory.buildResponse(new EjercicioServices(), "getTipos");
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/grupoMuscularObjetivo")
    public Response getGrupoMuscularObjetivo() throws Exception {
        return ResponseFactory.buildResponse(new EjercicioServices(), "getGrupos");
    }

   
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("sort/{attribute}/{orden}/{method}")
    public Response sort(@PathParam("attribute") String attribute, @PathParam("orden") Integer orden, @PathParam("method") Integer method) {
        return ResponseFactory.buildResponse(new EjercicioServices(), "sort", attribute, orden, method);
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("search/{attribute}/{value}")
    public Response search(@PathParam("attribute") String attribute, @PathParam("value") String value) {
        return ResponseFactory.buildResponse(new EjercicioServices(), "search", attribute, value);
    }
}