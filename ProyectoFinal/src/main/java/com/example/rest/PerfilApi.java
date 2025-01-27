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

import com.example.controller.dao.PerfilDao;
import com.example.controller.dao.services.PerfilServices;
import com.example.rest.response.ResponseFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

@Path("/perfil")
public class PerfilApi {

    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPerfiles() throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        PerfilServices ps = new PerfilServices();
        map.put("msg", "OK");
        map.put("data", ps.listAll().toArray());

        if (ps.listAll().getSize() == 0) {
            map.put("data", new Object[]{});
        }
        ObjectMapper om = new ObjectMapper();
        return Response.ok(om.writeValueAsString(map)).build();

    }

    @Path("/save")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(HashMap map) {
        HashMap<String, Object> res = new HashMap<>();

        /// TODO
        /// VALIDACION

        PerfilDao ps = new PerfilDao();

        try {

            ps.getPerfil().setNickName((map.get("nickName").toString()));
            ps.getPerfil().setImagen(map.get("imagen").toString());
            ps.getPerfil().setObjetivoCliente(map.get("objetivoCliente").toString());
            ps.getPerfil().setFechaCreacion(map.get("FechaCreacion").toString());

            ps.save();
            res.put("msg", "OK");
            res.put("data", "Perfil Registrado");
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
    public Response update(HashMap map) {
        HashMap<String, Object> res = new HashMap<>();
        PerfilDao ps = new PerfilDao();

        try {
            // Validar campos requeridos
            if (map.get("nickName") == null || map.get("imagen") == null
                    || map.get("objetivoCliente") == null) {
                throw new IllegalArgumentException("Todos los campos son obligatorios: nickName, imagen, objetivoCLiente.");
            }

            // Asignar valores
            ps.getPerfil().setNickName((map.get("nickName").toString()));
            ps.getPerfil().setImagen(map.get("imagen").toString());
            ps.getPerfil().setObjetivoCliente(map.get("objetivoCliente").toString());
            ps.getPerfil().setFechaCreacion(ps.get(Integer.valueOf((String)map.get("id"))).getFechaCreacion());
            ps.getPerfil().setId(Integer.valueOf((String)map.get("id")));

            ps.update();

            res.put("msg", "OK");
            res.put("data", "Perfil actualizado correctamente");
            ObjectMapper om = new ObjectMapper();
            return Response.ok(om.writeValueAsString(res)).build();

        } catch (IllegalArgumentException e) {
            res.put("msg", "Error");
            res.put("data", e.getMessage());
            e.printStackTrace();
            return Response.status(Status.BAD_REQUEST).entity(res).build();

        } catch (Exception e) {
            res.put("msg", "Error");
            res.put("data", e.toString());
            e.printStackTrace();
            return Response.status(Status.BAD_REQUEST).entity(res).build();
        }
    }

    @Path("/get/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getId(@PathParam("id") Integer id) throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        PerfilServices ps = new PerfilServices();
        try {
            ps.setPerfil(ps.get(id));
        } catch (Exception e) {

        }
        map.put("msg", "Ok");
        map.put("data", ps.getPerfil());

        if (ps.getPerfil().getId() == null) {

            map.put("data", "Perfil no encontrado");
            return Response.status(Status.BAD_REQUEST).entity(map).build();
        }

        ObjectMapper om = new ObjectMapper();
        return Response.ok(om.writeValueAsString(map)).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/delete/{id}")
    public Response delete(@PathParam("id") Integer id) throws Exception {
        return ResponseFactory.buildResponse(new PerfilServices(), "deletePerfil", id);
    }

}
