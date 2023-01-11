package dk.dtu.pay.manager;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/managers")
public class Resource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getMessage() {
        return "works works";
    }

    // @Path("/payments")
    // GET getAll() - get all payments from DTU Pay
    
}