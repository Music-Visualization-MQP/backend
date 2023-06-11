package collageify.deprecated.api;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import collageify.deprecated.auth.LoginAttempt;

@Path("/api")
public class TestClass {
    private ObjectMapper objectMapper;

    public TestClass(){
        objectMapper = new ObjectMapper();
    }


    /**
     * this function simply produces a random integer thanks chatgpt and ibutts
     * was useful before i even started doing anything with angular will probably
     * @return random integer between 0 and 100
     */
    @GET
    @Path("/test")
    @Produces(MediaType.TEXT_PLAIN)
    public int getRandomInteger() {
        // Generate a random integer
        int randomInteger =(int) Math.floor(Math.random() * 100);

        return randomInteger;
    }

    /**
     *
     * @param data  is a json sent from the client
     *
     *             The main idea is that this functon initiates login but thats kind of a pipe dream right now...
     * this is strictly debug because it prints the users email
     *
     * @return
     */
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
