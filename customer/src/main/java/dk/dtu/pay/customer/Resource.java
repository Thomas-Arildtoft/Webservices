package dk.dtu.pay.customer;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/customers")
public class Resource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getMessage() {
        return "works works";
    }

    // @Path("/registration")
    // POST register(accountId) - registers customer at DTU Pay (account is manually created and its id is passed here)

    // @Path("/payments")
    // GET getAll() - get all customer's payments from DTU Pay

}