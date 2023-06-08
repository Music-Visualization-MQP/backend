package collageify.api;


import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    private ObjectMapper objectMapper;

    public TestClass(){
        objectMapper = new ObjectMapper();
    }



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
    public String loginAttempt(String data) {
        try{

            LoginAttempt loginAttempt = objectMapper.readValue(data, LoginAttempt.class);
            System.out.println(loginAttempt.getEmail());
            loginAttempt.CheckDB();
            return loginAttempt.getEmail();
        } catch(Exception e){
            e.printStackTrace();
            return "get fucked";

        }




    }
}
