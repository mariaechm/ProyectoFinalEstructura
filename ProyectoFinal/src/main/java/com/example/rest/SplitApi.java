package com.example.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.example.controller.dao.services.SplitServices;
import com.example.rest.response.ResponseFactory;


@Path("/splits")
public class SplitApi {
    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() throws Exception {
        return ResponseFactory.buildResponse(new SplitServices(), "getAllSplits");
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/showAll")
    public Response showListAll() {
        return ResponseFactory.buildResponse(new SplitServices(), "showListAll");
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/get/{id}")
    public Response getById(@PathParam("id") Integer id) throws Exception {
        return ResponseFactory.buildResponse(new SplitServices(), "getSplitById", id);
    }


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/save")
    public Response save(String SplitJson) throws Exception {
        return ResponseFactory.buildResponse(new SplitServices(), "saveSplit", SplitJson);
    }


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/update")
    public Response update(String SplitJson) throws Exception {
        return ResponseFactory.buildResponse(new SplitServices(), "updateSplit", SplitJson);
    }


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/delete/{id}")
    public Response delete(@PathParam("id") Integer id) throws Exception {
        return ResponseFactory.buildResponse(new SplitServices(), "deleteSplit", id);
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("sort/{attribute}/{orden}/{method}")
    public Response sort(@PathParam("attribute") String attribute, @PathParam("orden") Integer orden, @PathParam("method") Integer method) {
        return ResponseFactory.buildResponse(new SplitServices(), "sort", attribute, orden, method);
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("search/{attribute}/{value}")
    public Response search(@PathParam("attribute") String attribute, @PathParam("value") String value) {
        return ResponseFactory.buildResponse(new SplitServices(), "search", attribute, value);
    }
}