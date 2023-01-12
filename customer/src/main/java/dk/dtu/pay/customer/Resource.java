package dk.dtu.pay.customer;

import dk.dtu.pay.utils.messaging.Event;
import dk.dtu.pay.utils.messaging.MessageQueue;
import dk.dtu.pay.utils.messaging.QueueNames;
import dk.dtu.pay.utils.messaging.RabbitMQQueue;
import dk.dtu.pay.utils.models.Role;
import dk.dtu.pay.utils.models.User;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Path("/customers")
public class Resource {

    private MessageQueue messageQueue = new RabbitMQQueue("rabbitMq");

    @GET
    @Path("/send")
    @Produces(MediaType.APPLICATION_JSON)
    public String sendMessage() {
        User user = new User("someID", Role.CUSTOMER);
        messageQueue.publish(QueueNames.REGISTER_CUSTOMER_REQUESTED, new Event(new Object[]{ user }));
        return "User sent to queue";
    }

    @GET
    @Path("/read")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readMessage() {
        CompletableFuture<User> completableFuture = new CompletableFuture<>();
        messageQueue.addHandler(QueueNames.REGISTER_CUSTOMER_REQUESTED,
                (event) -> completableFuture.complete(event.getArgument(0, User.class)));

        try {
            User user = completableFuture.orTimeout(5, TimeUnit.SECONDS).join();
            return Response.ok().entity(user).build();
        } catch (Exception e) {
            return Response.status(404).entity("Retrieving message from the queue failed").build();
        }
    }

    // @Path("/registration")
    // POST register(accountId) - registers customer at DTU Pay (account is manually created and its id is passed here)

    // @Path("/payments")
    // GET getAll() - get all customer's payments from DTU Pay

}