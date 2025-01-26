package com.example.rest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

@Path("/image")
public class ImageResource {

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Path("/upload")
    public Response uploadImage(@FormDataParam("image") InputStream uploadedInputStream,
                                @FormDataParam("image") FormDataContentDisposition fileDetail) {
        
                                    System.out.println("ImageResource.uploadImage()");
        try {
            
            String uploadedFileLocation = "./images/" + fileDetail.getFileName();

            saveToFile(uploadedInputStream, uploadedFileLocation);

            return Response.status(200).entity("Archivo subido correctamente a " + uploadedFileLocation).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(500).entity("Error al subir el archivo").build();
        }
    }
    
    private void saveToFile(InputStream uploadedInputStream, String target) throws IOException {
        try (OutputStream out = new FileOutputStream(new File(target))) {
            int read;
            byte[] bytes = new byte[1024];
            while ((read = uploadedInputStream.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
        }
    }
    
}
