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
import com.example.controller.dao.services.SplitServices;

@Path("/splits")
public class SplitApi {
    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllSplits() throws Exception {
        SplitServices ss = new SplitServices();
        HashMap<String,Object> responseMap = new HashMap<>();
        ObjectMapper om = new ObjectMapper();

        try {
            responseMap.put("status","OK");
            if(ss.getAllSplits() == null) {
                responseMap.put("data",new Object[0]);
                return Response.ok(om.writeValueAsString(responseMap)).build();
            }
            responseMap.put("data",ss.getAllSplits());
            return Response.ok(om.writeValueAsString(responseMap)).build();
        } catch (Exception e) {
            e.printStackTrace();
            responseMap.put("status","ERROR");
            responseMap.put("data",e.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                            .entity(om.writeValueAsString(responseMap)).build();
        }
    }
}