package com.example.controller.auth;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

import com.example.models.enumerator.Rol;

@Provider
@Priority(Priorities.AUTHORIZATION)
public class AuthorizationFilter implements ContainerRequestFilter {
    
    @Inject
    ResourceInfo resourceInfo;

    @Override
    public void filter(ContainerRequestContext containerRequestContext) {
        Secured secured = resourceInfo.getResourceMethod().getAnnotation(Secured.class);

        if(secured == null) {
            return;
        }

        Rol rol = Rol.valueOf((String)containerRequestContext.getProperty("role"));

        if(!authorized(secured.rolesAllowed(), rol)) {
            containerRequestContext.abortWith(Response.status(Status.FORBIDDEN).build());
            return;
        }
    }

    public boolean authorized(Rol[] roles, Rol rol) {
        for(int i = 0; i < roles.length; i++) {
            if(rol.equals(roles[i])) {
                return true;
            }
        } 
        return false;
    }


}
