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
    private final Service service = new Service(RabbitMQQueue.getInstance());

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response register(AccountId accountId) {
        try {
            return Response.ok().entity(service.register(accountId)).build();
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