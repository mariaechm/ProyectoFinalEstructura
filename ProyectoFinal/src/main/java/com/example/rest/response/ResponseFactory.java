package com.example.rest.response;

import java.lang.reflect.Method;
import java.util.HashMap;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
/* import com.example.controller.exception.EmptyFieldException;
import com.example.controller.exception.IdNotFoundException; */

public class ResponseFactory {
    
    public static Response buildResponseWithStatus(Status status, Object object) throws Exception {
        HashMap<String, Object> responseHashMap = new HashMap<>();
        ObjectMapper om = new ObjectMapper();
        responseHashMap.put("status", "succes");
        responseHashMap.put("data", object);

        final String response = om.writeValueAsString(responseHashMap);
        return Response.status(status).entity(response).build();
    }

    public static Response buildResponseWithStatus(Status status, Object object, String info) throws Exception {
        HashMap<String, Object> responseHashMap = new HashMap<>();
        ObjectMapper om = new ObjectMapper();
        responseHashMap.put("status", "Succes");
        responseHashMap.put("data", object);
        responseHashMap.put("info",info);

        final String response = om.writeValueAsString(responseHashMap);
        return Response.status(status).entity(response).build();
    }

    public static Response buildResponseOk(Object object) throws Exception {
        return buildResponseWithStatus(Status.OK, object);
    }

    /* Método que devuelve una respuesta evaluando una función sin parámetros */
    public static Response buildResponseOk(Object modelService, String methodName) throws Exception {
        Method serviceMethod = modelService.getClass().getMethod(methodName);
        Object data = null;
        try {
            data = serviceMethod.invoke(modelService);
        } catch (Exception e) {
            throw (Exception)e.getCause();
        }
        return buildResponseOk(data);
    }

    /* Devuelve una respuesta evaluando una función con parámetros */
    public static Response buildResponseOk(Object modelService, String methodName, Object... args) throws Exception {
        Class<?>[] parameters = new Class[args.length];
        for(int i = 0; i < args.length; i++) {
            parameters[i] = args[i].getClass();
        }
        
        Method serviceMethod = modelService.getClass().getMethod(methodName,parameters);
        Object data = null;
        try {
            data = serviceMethod.invoke(modelService,args);
        } catch (Exception e) {
            throw (Exception)e.getCause();
        }
        if(methodName.contains("save")) 
            return buildResponseWithStatus(Status.CREATED, data,"Información guardada con éxito!");
        if(methodName.contains("update"))
            return buildResponseWithStatus(Status.OK, data,"Información actualizada con éxito!");
        if(methodName.contains("delete"))
            return buildResponseWithStatus(Status.OK, data, "Información eliminada correctamente!");
        return buildResponseOk(data);
    }

    public static Response buildResponseWithException(Exception e) {
        HashMap<String,Object> responseHashMap = new HashMap<>();
        Gson gson = new Gson();
        responseHashMap.put("status", "Error");
        responseHashMap.put("info",e.getMessage());

       /*  if(e.getClass().equals(IdNotFoundException.class)
        ) {
            responseHashMap.put("recomendation","Ingrese un Id válido");
            return Response.status(Status.BAD_REQUEST).entity(gson.toJson(responseHashMap)).build();
        } else if (e.getClass().equals(EmptyFieldException.class)) {
            responseHashMap.put("recomendation","Complete la información");
            return Response.status(Status.BAD_REQUEST).entity(gson.toJson(responseHashMap)).build();
        } */
        
        // TODO: add exception handlers (Para enviar respuestas en casos específicos)

        return Response.status(Status.INTERNAL_SERVER_ERROR).entity(gson.toJson(responseHashMap)).build();
    }

    public static Response buildResponse(Object data) {
        try {
            return buildResponseOk(data);
        } catch (Exception e) {
            e.printStackTrace();
            return buildResponseWithException(e);
        }
    }

    public static Response buildResponse(Object modelService, String methodName) {
        try {
            return buildResponseOk(modelService,methodName);
        } catch (Exception e) {
            e.printStackTrace();
            return buildResponseWithException(e);
        }
    }

    public static Response buildResponse(Object modelService, String methodName, Object... args) {
        try {
            return buildResponseOk(modelService,methodName,args);
        } catch (Exception e) {
            e.printStackTrace();
            return buildResponseWithException(e);
        }
    }



    
}
