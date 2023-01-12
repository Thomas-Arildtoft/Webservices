package dk.dtu.pay.customer;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import dk.dtu.pay.utils.models.Role;
import dk.dtu.pay.utils.models.User;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;

@Path("/customers")
public class Resource {

    private Client client = ClientBuilder.newClient();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getMessage() {
        User user = new User("someID", Role.CUSTOMER); //Just checking if User class is available
        return "works works";
    }

    // @Path("/registration")
    // POST register(accountId) - registers customer at DTU Pay (account is manually created and its id is passed here)

    // @Path("/payments")
    // GET getAll() - get all customer's payments from DTU Pay

}