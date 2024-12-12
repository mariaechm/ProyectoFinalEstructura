package com.example.rest;

import java.util.HashMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.example.controller.dao.services.PersonaServices;
import com.fasterxml.jackson.databind.ObjectMapper;

@Path("/persona")
public class PersonaApi {
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/all")
    public Response getAll() {
        HashMap<String,Object> responseMap = new HashMap<>();
        PersonaServices ps = new PersonaServices();
        
        responseMap.put("msg", "OK");
        responseMap.put("data", ps.getAllPersonas());

        ObjectMapper om = new ObjectMapper();
        String response;
        try {
            response = om.writeValueAsString(responseMap);
        } catch (Exception e) {
            response = "";
        }
        return Response.ok(response).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/save")
    public Response save(String personaJson) {
        HashMap<String,Object> responseMap = new HashMap<>();
        PersonaServices ps = new PersonaServices();
        
        try {
            responseMap.put("msg", "OK");
            responseMap.put("data", ps.savePersona(personaJson));
        } catch (Exception e) {
            responseMap.put("msg", "ERROR");
            responseMap.put("data", e.getMessage());
        }
        
        ObjectMapper om = new ObjectMapper();
        String response;
        try {
            response = om.writeValueAsString(responseMap);
        } catch (Exception e) {
            response = "";
        }
        return Response.ok(response).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/get/{id}")
    public Response get(@PathParam("id") Integer id) {
        HashMap<String,Object> responseMap = new HashMap<>();
        PersonaServices ps = new PersonaServices();
        
        try {
            responseMap.put("msg", "OK");
            responseMap.put("data", ps.getPersonaById(id));
        } catch (Exception e) {
            responseMap.put("msg", "ERROR");
            responseMap.put("data", e.getMessage());    
        }

        ObjectMapper om = new ObjectMapper();
        String response;
        try {
            response = om.writeValueAsString(responseMap);
        } catch (Exception e) {
            response = "";
        }
        return Response.ok(response).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/update")
    public Response update(String personaJson) {
        HashMap<String,Object> responseMap = new HashMap<>();
        PersonaServices ps = new PersonaServices();
        
        try {
            responseMap.put("msg", "OK");
            responseMap.put("data", ps.updatePersona(personaJson));
        } catch (Exception e) {
            responseMap.put("msg", "ERROR");
            responseMap.put("data", e.getMessage());
        }
        

        ObjectMapper om = new ObjectMapper();
        String response;
        try {
            response = om.writeValueAsString(responseMap);
        } catch (Exception e) {
            response = "";
        }
        return Response.ok(response).build();
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/delete/{id}")
    public Response delete(@PathParam("id") Integer id) {
        HashMap<String,Object> responseMap = new HashMap<>();
        PersonaServices ps = new PersonaServices();
        
        try {
            responseMap.put("msg", "OK");
            responseMap.put("data", ps.deletePersona(id));
        } catch (Exception e) {
            responseMap.put("msg", "ERROR");
            responseMap.put("data", e.getMessage());
        }
        

        ObjectMapper om = new ObjectMapper();
        String response;
        try {
            response = om.writeValueAsString(responseMap);
        } catch (Exception e) {
            response = "";
        }
        return Response.ok(response).build();
    }
}