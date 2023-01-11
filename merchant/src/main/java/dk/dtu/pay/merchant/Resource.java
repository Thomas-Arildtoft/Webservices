package dk.dtu.pay.merchant;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/merchants")
public class Resource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getMessage() {
        return "works works";
    }

    // @Path("/payments")
    // POST initiatePayment(amount) - initiates payment from customer to merchant

    // @Path("/registration")
    // POST register(accountId) - registers merchant at DTU Pay (account is manually created and its id is passed here)

    // @Path("/payments")
    // GET getAll() - get all merchant's payments from DTU Pay

}