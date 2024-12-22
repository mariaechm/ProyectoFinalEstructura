package com.example.controller.auth;

import java.util.HashMap;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {
    
    public static String[] allowedPaths = {
        "auth/login"
    };

    @Override
    public void filter(ContainerRequestContext containerRequestContext) {
        String currentPath = containerRequestContext.getUriInfo().getPath();
        
        if(isFreePath(currentPath)) {
            return;
        }

        String authHeader = containerRequestContext.getHeaderString("Authorization");
        
        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            containerRequestContext.abortWith(Response.status(Status.UNAUTHORIZED).build());
            return;
        }

        String jwt = authHeader.substring("Bearer ".length());

        if(!JWTManager.isValidToken(jwt)) {
            containerRequestContext.abortWith(Response.status(Status.UNAUTHORIZED).build());
            return;
        }
        
        HashMap<String,String> tk = JWTManager.decodeTokenHashMap(jwt);
        
        containerRequestContext.setProperty("sub", tk.get("sub").toString());
        containerRequestContext.setProperty("role", tk.get("role").toString());

    }

    public Boolean isFreePath(String currentPath) {
        for(String path : allowedPaths) {
            if(path.equals(currentPath)) {
                return true;
            }
        }
        return false;
    }
}
