package com.example.rest;

import java.io.File;
import java.io.FileInputStream;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/images")
public class ImageController {

    private static final String IMAGE_DIR = "./images"; 

    @GET
    @Path("/{fileName}")
    @Produces("image/*")
    public Response getImage(@PathParam("fileName") String fileName) {
        try {
            File file = new File(IMAGE_DIR, fileName);
            if (!file.exists()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("La imagen no existe").build();
            }

            return Response.ok(new FileInputStream(file), MediaType.APPLICATION_OCTET_STREAM)
                    .header("Content-Disposition", "inline; filename=\"" + fileName + "\"")
                    .build();

        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Error al obtener la imagen").build();
        }
    }
}
