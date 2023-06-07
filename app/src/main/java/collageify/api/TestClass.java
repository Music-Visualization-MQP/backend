package collageify.api;


import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;



@Path("/api")
public class TestClass {
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String sayHello(){
        return "hey there sexy";
    }
}
