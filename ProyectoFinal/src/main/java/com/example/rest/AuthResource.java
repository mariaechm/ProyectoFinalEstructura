package com.example.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.example.controller.auth.Secured;
import com.example.controller.dao.services.CuentaServices;
import com.example.models.enumerator.Rol;
import com.example.rest.response.ResponseFactory;

@Path("/auth")
public class AuthResource {
    
    @Context
    ContainerRequestContext containerRequestContext;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/login")
    public Response login(String credentials) {
        return ResponseFactory.buildResponse(new CuentaServices(),"validateCredentialsAndGetToken", credentials);
    }

    @Secured(rolesAllowed = {Rol.ADMINISTRADOR, Rol.CLIENTE})
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/usr/info")
    public Response userData() {
        Integer cuentaId = Integer.valueOf((String)containerRequestContext.getProperty("sub"));
        return ResponseFactory.buildResponse(new CuentaServices(), "getUserInfo",cuentaId);
    }

    @Secured(rolesAllowed = {Rol.ADMINISTRADOR})
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/register")
    public Response register(String json) {
        return ResponseFactory.buildResponse(new CuentaServices(),"registerNewUser",json);
    }

    @Secured(rolesAllowed = {Rol.ADMINISTRADOR})
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/delete/{id}")
    public Response delete(@PathParam("id") Integer id) {
        return ResponseFactory.buildResponse(new CuentaServices(),"deleteUser",id);
    }

    @POST
    @Secured(rolesAllowed = {Rol.ADMINISTRADOR, Rol.CLIENTE})
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/change/password")
    public Response changePassword(String json) {
        return ResponseFactory.buildResponse(new CuentaServices(),"changePassword",json);
    }

}
