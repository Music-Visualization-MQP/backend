package collageify.api;


import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.ws.rs.*;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.ext.Provider;
import com.fasterxml.jackson.annotation.JsonCreator;
import collageify.auth.LoginAttempt;

@Path("/api")
public class TestClass {



    @GET
    @Path("/test")
    @Produces(MediaType.TEXT_PLAIN)
    public int getRandomInteger() {
        // Generate a random integer
        int randomInteger = 8;

        return randomInteger;
    }
    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String loginAttempt(LoginAttempt loginAttempt) {
        System.out.println("hehe");
        return loginAttempt.getEmail();




    }
}
