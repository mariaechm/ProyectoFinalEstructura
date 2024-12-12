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
import com.example.controller.dao.services.RutinaServices;


@Path("/rutinas")
public class RutinaApi {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/all")
    public Response getAllRutinas() throws Exception {
        RutinaServices rs = new RutinaServices();
        HashMap<String,Object> responseMap = new HashMap<>();
        ObjectMapper om = new ObjectMapper();

        try {
            responseMap.put("status","OK");
            if(rs.getAllRutinas() == null) {
                responseMap.put("data",new Object[0]);
                return Response.ok(om.writeValueAsString(responseMap)).build();
            }
            responseMap.put("data",rs.getAllRutinas());
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
    public Response saveRutina(String rutinaJson) throws Exception {
        RutinaServices rs = new RutinaServices();
        HashMap<String,Object> responseMap = new HashMap<>();
        ObjectMapper om = new ObjectMapper();

        try {
            rs.RutinaFromJson(rutinaJson);
            rs.saveRutina();
            responseMap.put("status","OK");
            responseMap.put("data",rs.getRutina());
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
    public Response deleteRutina(@PathParam("id") Integer id) throws Exception {
        RutinaServices rs = new RutinaServices();
        HashMap<String,Object> responseMap = new HashMap<>();
        ObjectMapper om = new ObjectMapper();

        try {
            responseMap.put("status","OK");
            responseMap.put("data",rs.getRutinaById(id));
            rs.deleteRutina(id);
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
    @Path("/get/{id}")
    public Response getRutinaById(@PathParam("id") Integer id) throws Exception {
        RutinaServices rs = new RutinaServices();
        HashMap<String,Object> responseMap = new HashMap<>();
        ObjectMapper om = new ObjectMapper();

        try {
            responseMap.put("status","OK");
            responseMap.put("data",rs.getRutinaById(id));
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
    public Response getAllRutinas(String rutinaJson) throws Exception {
        RutinaServices rs = new RutinaServices();
        HashMap<String,Object> responseMap = new HashMap<>();
        ObjectMapper om = new ObjectMapper();

        try {
            rs.RutinaFromJson(rutinaJson);
            rs.update();
            responseMap.put("status","OK");
            responseMap.put("data",rs.getRutina());
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
    @Path("/enumerations")
    public Response enumerations() throws Exception {
        RutinaServices rs = new RutinaServices();
        HashMap<String,Object> responseMap = new HashMap<>();
        HashMap<String,Object> enumerations = new HashMap<>();
        ObjectMapper om = new ObjectMapper();

        try {
            enumerations.put("gruposMuscularesObjetivos", rs.gruposMuscularesObjetivos());
            enumerations.put("objetivosRutina",rs.objetivosRutina());

            responseMap.put("status", "OK");
            responseMap.put("data",enumerations);

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