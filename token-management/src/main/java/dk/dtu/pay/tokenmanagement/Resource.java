package dk.dtu.pay.tokenmanagement;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/tokens")
public class Resource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getMessage() {
        return "works works";
    }

    // GET token(accountId, numOfTokens) - return one token associated to accountId

}