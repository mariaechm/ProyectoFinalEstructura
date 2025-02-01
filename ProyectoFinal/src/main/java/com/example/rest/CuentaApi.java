package com.example.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.example.controller.auth.Secured;
import com.example.controller.dao.services.CuentaServices;
import com.example.models.enumerator.Rol;
import com.example.rest.response.ResponseFactory;


@Path("/cuenta")
public class CuentaApi {
    
    @GET
    @Secured(rolesAllowed = {Rol.ADMINISTRADOR})
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/list")
    public Response getAll() {
        return ResponseFactory.buildResponse(new CuentaServices(),"getAllCuentas");
    }

    @POST
    @Secured(rolesAllowed = {Rol.ADMINISTRADOR})
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/save")
    public Response save(String personaJson) {
        return ResponseFactory.buildResponse(new CuentaServices(),"saveCuenta", personaJson);
    }

    @GET
    @Secured(rolesAllowed = {Rol.ADMINISTRADOR})
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/get/{id}")
    public Response get(@PathParam("id") Integer id) {
        return ResponseFactory.buildResponse(new CuentaServices(),"getCuentaById",id);
    }

    @POST
    @Secured(rolesAllowed = {Rol.ADMINISTRADOR})
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/update")
    public Response update(String personaJson) {
       return ResponseFactory.buildResponse(new CuentaServices(),"updateCuenta", personaJson);
    }

    
    @DELETE
    @Secured(rolesAllowed = {Rol.ADMINISTRADOR})
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/delete/{id}")
    public Response delete(@PathParam("id") Integer id) {
        return ResponseFactory.buildResponse(new CuentaServices(),"deleteCuenta",id);
    }

    @GET
    @Secured(rolesAllowed = {Rol.ADMINISTRADOR})
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/enumerations")
    public Response enumerations() {
        return ResponseFactory.buildResponse(new CuentaServices(),"enumerations");
    }

    @GET
    @Secured(rolesAllowed = {Rol.ADMINISTRADOR})
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/sort/{attribute}/{orden}/{method}")
    public Response sort(@PathParam("attribute") String attribute,
                        @PathParam("orden") Integer orden,
                        @PathParam("method") Integer method) 
    {
        return ResponseFactory.buildResponse(new CuentaServices(),"sort",attribute,orden,method);
    }

    @GET
    @Secured(rolesAllowed = {Rol.ADMINISTRADOR, Rol.CLIENTE})
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/search/{attribute}/{x}")
    public Response enumerations(@PathParam("attribute") String attribute, @PathParam("x") String x) {
        return ResponseFactory.buildResponse(new CuentaServices(),"search", attribute, x);
    } 
}