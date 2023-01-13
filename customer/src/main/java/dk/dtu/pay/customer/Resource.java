package dk.dtu.pay.customer;

import dk.dtu.pay.utils.messaging.RabbitMQQueue;
import dk.dtu.pay.utils.models.AccountId;
import dk.dtu.pay.utils.models.User;

import javax.ws.rs.*;
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

    @GET
    @Path("/tokens")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTokens(@QueryParam("numOfTokens") int numOfTokens) {
        try {
            return Response.ok().entity(service.getTokens(numOfTokens)).build();
        } catch (Exception e) {
            return Response.status(404).entity("Getting tokens failed").build();
        }
    }

    @DELETE
    public void clean() {
        service.clean();
    }

}