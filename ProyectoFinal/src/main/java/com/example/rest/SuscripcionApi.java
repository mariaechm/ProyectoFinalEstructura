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

import com.example.controller.dao.services.SuscripcionServices;
import com.example.models.enumerator.TipoSuscripcion;
import com.fasterxml.jackson.databind.ObjectMapper;


@Path("suscripcion")
public class SuscripcionApi {
    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllEstadistica() throws Exception{
        HashMap<String,Object> map = new HashMap<>();
        SuscripcionServices ssrv = new SuscripcionServices();
        map.put("msg", "OK");
        map.put("data", ssrv.listAll().toArray());

        if (ssrv.listAll().getSize() == 0) {
            map.put("data", new Object[]{});
        }
        ObjectMapper om = new ObjectMapper();
        return Response.ok(om.writeValueAsString(map)).build();

    }

    @Path("/save")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(HashMap<String,Object> map) {
        HashMap<String,Object> res = new HashMap<>();
        SuscripcionServices ssrv = new SuscripcionServices();
      
        try {

            ssrv.getSuscripcion().setFechaInicio(map.get("fechaInicio").toString());
            //ssrv.getSuscripcion().setFechaFinalizacion(map.get("fechaFinalizacion").toString());
            ssrv.getSuscripcion().setTipo((TipoSuscripcion.valueOf(map.get("tipo").toString())));
            
            
            

            ssrv.save();
            res.put("msg", "OK");
            res.put("data", "Suscripcion Registarada");
            return Response.ok(res).build();

        } catch (Exception e) {
            System.out.println("Error" + e.toString());
            res.put("msg", "Error");
            res.put("data", e.toString());
            return Response.status(Status.BAD_REQUEST).entity(res).build();

        }

    }

    @Path("/delete/{id}")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") Integer id) { // Extraer el id de la URL {id}
        HashMap<String,Object> res = new HashMap<>();

        SuscripcionServices ssrv = new SuscripcionServices();
      
        try {
            ssrv.delete(id);
            res.put("msg", "OK");
            res.put("status", "Suscripcion Eliminada");
            ObjectMapper om = new ObjectMapper();
            return Response.ok(om.writeValueAsString(res)).build();

        } catch (Exception e) {
            System.out.println("Error" + e.toString());
            res.put("msg", "Error");
            res.put("data", e.toString());
            return Response.status(Status.BAD_REQUEST).entity(res).build();

        }

    }

    @Path("/update")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(HashMap<String,Object> map) {
        HashMap<String,Object> res = new HashMap<>();
        SuscripcionServices ssrv = new SuscripcionServices();
      
        try {
            ssrv.getSuscripcion().setId(Integer.parseInt(map.get("id").toString()));
            ssrv.getSuscripcion().setFechaInicio(map.get("fechaInicio").toString());
            //ssrv.getSuscripcion().setFechaFinalizacion(map.get("fechaFinalizacion").toString());
            ssrv.getSuscripcion().setTipo((TipoSuscripcion.valueOf(map.get("tipo").toString())));
            //ssrv.getSuscripcion().setPrecio((TipoSuscripcion.valueOf(map.get("tipo").toString()).getPrecio()));
            ssrv.getSuscripcion().setDuracionDias((TipoSuscripcion.valueOf(map.get("tipo").toString()).getDuracionDias()));
            

            ssrv.update();
            res.put("msg", "OK");
            res.put("data", "Suscripcion Modificada");
            return Response.ok(res).build();

        } catch (Exception e) {
            System.out.println("Error" + e.toString());
            res.put("msg", "Error");
            res.put("data", e.toString());
            return Response.status(Status.BAD_REQUEST).entity(res).build();

        }
    }

    @Path("/get/{id}")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("id") Integer id) {
        HashMap<String,Object> res = new HashMap<>();
        SuscripcionServices ssrv = new SuscripcionServices();
      
        try {         
            res.put("msg", "OK");
            res.put("data", ssrv.getById(id));
            ObjectMapper om = new ObjectMapper();
            return Response.ok(om.writeValueAsString(res)).build();

        } catch (Exception e) {
            System.out.println("Error" + e.toString());
            res.put("msg", "Error");
            res.put("data", e.toString());
            return Response.status(Status.BAD_REQUEST).entity(res).build();

        }
    }


    @Path("/tipoSuscripcion")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response enumeration() {
        HashMap<String,Object> res = new HashMap<>();
        SuscripcionServices ssrv = new SuscripcionServices();
      
        try {         
            res.put("msg", "OK");
            res.put("data", ssrv.tiposSuscripcion());
            ObjectMapper om = new ObjectMapper();
            return Response.ok(om.writeValueAsString(res)).build();

        } catch (Exception e) {
            System.out.println("Error" + e.toString());
            res.put("msg", "Error");
            res.put("data", e.toString());
            return Response.status(Status.BAD_REQUEST).entity(res).build();

        }
    }

}
