package utils;

import dk.dtu.pay.utils.models.AccountId;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * @author Piotr
 */
 
public class CustomerRestClient {

    private static final String BASE_URL = "http://localhost:8083/customers";

    private Client client = ClientBuilder.newClient();

    public Response register(AccountId accountId) {
        return client
                .target(BASE_URL + "/register")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(accountId, MediaType.APPLICATION_JSON));
    }

    public Response getTokens(int numberOfTokens) {
        return client
                .target(BASE_URL + "/tokens")
                .queryParam("numOfTokens", numberOfTokens)
                .request(MediaType.APPLICATION_JSON)
                .get(Response.class);
    }

    public void clean() {
        client
            .target(BASE_URL)
            .request()
            .delete();
    }
}
