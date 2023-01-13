package dk.dtu.pay.customer;

import dk.dtu.pay.utils.messaging.RabbitMQQueue;
import dk.dtu.pay.utils.models.AccountId;
import dk.dtu.pay.utils.models.User;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/customers")
public class Resource {
    private final Service service = new Service(RabbitMQQueue.getInstance());

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response pay(AccountId accountId) {
        try {
            User user = service.register(accountId);
            return Response.ok().entity(user).build();
        } catch (Exception e) {
            return Response.status(404).entity("Registration failed").build();
        }
    }

    // @Path("/payments")
    // GET getAll() - get all customer's payments from DTU Pay

}