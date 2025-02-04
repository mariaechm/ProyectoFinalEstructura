package com.example.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.example.controller.dao.services.RutinaServices;
import com.example.rest.response.ResponseFactory;


@Path("/rutinas")
public class RutinaApi {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/all")
    public Response getAll() throws Exception {
        return ResponseFactory.buildResponse(new RutinaServices(), "getAllRutinas");
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/showAll")
    public Response showListAll() {
        return ResponseFactory.buildResponse(new RutinaServices(), "showListAll");
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/get/{id}")
    public Response getById(@PathParam("id") Integer id) throws Exception {
        return ResponseFactory.buildResponse(new RutinaServices(), "getRutinaById", id);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/save")
    public Response save(String rutinaJson) throws Exception {
        return ResponseFactory.buildResponse(new RutinaServices(), "saveRutina", rutinaJson);
    }


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/update/")
    public Response update(String rutinaJson) throws Exception {
        return ResponseFactory.buildResponse(new RutinaServices(), "updateRutina", rutinaJson);
    }


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/delete/{id}")
    public Response delete(@PathParam("id") Integer id) throws Exception {
        return ResponseFactory.buildResponse(new RutinaServices(), "deleteRutina", id);
    }

    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/objetivoRutina")
    public Response getObjetivoRutina() throws Exception {
        return ResponseFactory.buildResponse(new RutinaServices(), "getObjetivos");
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("sort/{attribute}/{orden}/{method}")
    public Response sort(@PathParam("attribute") String attribute, @PathParam("orden") Integer orden, @PathParam("method") Integer method) {
        return ResponseFactory.buildResponse(new RutinaServices(), "sort", attribute, orden, method);
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("search/{attribute}/{value}")
    public Response search(@PathParam("attribute") String attribute, @PathParam("value") String value) {
        return ResponseFactory.buildResponse(new RutinaServices(), "search", attribute, value);
    }
}