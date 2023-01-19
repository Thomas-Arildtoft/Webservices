package dk.dtu.pay.merchant;

import dk.dtu.pay.utils.messaging.RabbitMQQueue;
import dk.dtu.pay.utils.models.AccountId;
import dk.dtu.pay.utils.models.InitiatePaymentDTO;
import dk.dtu.pay.utils.models.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/merchants")
public class Resource {
    private final Service service = new Service(new RabbitMQQueue()); //TODO: restore it later

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response register(AccountId accountId) {
        try {
            User user = service.register(accountId);
            return Response.ok().entity(user).build();
        } catch (Exception e) {
            return Response.status(404).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("/initiate-payment")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response initiatePayment(InitiatePaymentDTO initiatePaymentDTO) {
        try {
            String message = service.initiatePayment(initiatePaymentDTO);
            return Response.ok().entity(message).build();
        } catch (Exception e) {
            return Response.status(404).entity("Payment failed").build();
        }
    }

    @DELETE
    public void clean() {
        service.clean();
    }
}