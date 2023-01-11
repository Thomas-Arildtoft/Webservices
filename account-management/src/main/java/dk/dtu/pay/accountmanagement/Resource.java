package dk.dtu.pay.accountmanagement;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/accounts")
public class Resource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getMessage() {
        return "works works";
    }

    // POST register(accountId) - registers account at DTU Pay

}