package com.example.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.example.controller.dao.services.SuscripcionServices;
import com.example.rest.response.ResponseFactory;



@Path("suscripcion")
public class SuscripcionApi {
    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() throws Exception{
        return ResponseFactory.buildResponse(new SuscripcionServices(), "getAllSuscripciones");

    }

   /* @Path("/showAll")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response showListAll() {
        return ResponseFactory.buildResponse(new SuscripcionServices(), "showListAll");
    }*/

    @Path("/save")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response save (String suscripcionJson) throws Exception {
        return ResponseFactory.buildResponse(new SuscripcionServices(), "save", suscripcionJson);

    }

    @Path("/delete/{id}")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") Integer id) throws Exception {
        return ResponseFactory.buildResponse(new SuscripcionServices(), "delete", id);

    }

    @Path("/update")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(String suscripcionJson) throws Exception {
        return ResponseFactory.buildResponse(new SuscripcionServices(), "update", suscripcionJson);
    }

    @Path("/get/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("id") Integer id) {
            return ResponseFactory.buildResponse(new SuscripcionServices(), "getSuscripcionById", id);
    }


    @Path("/tipoSuscripcion")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTipoSuscripcion() throws Exception {
        return ResponseFactory.buildResponse(new SuscripcionServices(),"getTipos");
    }

    @Path("sort/{attribute}/{orden}/{method}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response sort(@PathParam("attribute") String attribute, @PathParam("orden") Integer orden, @PathParam("method") Integer method) {
        return ResponseFactory.buildResponse(new SuscripcionServices(), "sort", attribute, orden, method);
    }


    @Path("search/{attribute}/{value}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response search(@PathParam("attribute") String attribute, @PathParam("value") String value) {
        return ResponseFactory.buildResponse(new SuscripcionServices(), "search", attribute, value);
    }    
}
