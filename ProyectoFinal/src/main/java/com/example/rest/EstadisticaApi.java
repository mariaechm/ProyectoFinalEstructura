package com.example.rest;


import java.util.HashMap;

import javax.print.attribute.standard.Media;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import com.example.controller.tda.list.LinkedList;
import com.example.controller.dao.EstadisticaDao;
import com.example.controller.dao.services.EstadisticaServices;
import com.example.models.*;

@Path("Estadistica")
public class EstadisticaApi {
    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllEstadistica(){
        HashMap map = new HashMap<>();
        EstadisticaServices ps = new EstadisticaServices();
        map.put("msg", "OK");
        map.put("data", ps.listAll().toArray());

        if (ps.listAll().getSize() == 0) {
            map.put("data", new Object[]{});
        }
        return Response.ok(map).build();

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
        EstadisticaDao ps = new EstadisticaDao();
      
        try {

            ps.getEstadistica().setMedidaEspalda(Float.parseFloat(map.get("medidaEspalda").toString()));
            ps.getEstadistica().setMedidaEspalda(Float.parseFloat(map.get("medidaEspalda").toString()));
            ps.getEstadistica().setMedidaPierna(Float.parseFloat(map.get("medidaPierna").toString()));
            ps.getEstadistica().setMedidaBrazo(Float.parseFloat(map.get("medidaBrazo").toString()));
            ps.getEstadistica().setMedidaCintura(Float.parseFloat(map.get("medidaCintura").toString()));
            ps.getEstadistica().setPeso(Float.parseFloat(map.get("peso").toString()));
            ps.getEstadistica().setAltura(Float.parseFloat(map.get("altura").toString()));
            
            System.out.println("ASs");

            ps.save();
            res.put("msg", "OK");
            res.put("data", "Proyecto Registarado");
            return Response.ok(res).build();

        } catch (Exception e) {
            System.out.println("Error" + e.toString());
            res.put("msg", "Error");
            res.put("data", e.toString());
            return Response.status(Status.BAD_REQUEST).entity(res).build();

        }

    }
    
    
}