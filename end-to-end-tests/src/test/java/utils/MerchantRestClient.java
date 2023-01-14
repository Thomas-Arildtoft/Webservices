package utils;

import dk.dtu.pay.utils.models.AccountId;
import dk.dtu.pay.utils.models.InitiatePaymentDTO;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class MerchantRestClient {

    private static final String BASE_URL = "http://localhost:8084/merchants/";

    private Client client = ClientBuilder.newClient();

    public Response register(AccountId accountId) {
        return client
                .target(BASE_URL + "/register")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(accountId, MediaType.APPLICATION_JSON));
    }

    public Response initiatePayment(InitiatePaymentDTO initiatePaymentDTO) {
        return client
                .target(BASE_URL + "/initiate-payment")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(initiatePaymentDTO, MediaType.APPLICATION_JSON));
    }

    public void clean() {
        client
            .target(BASE_URL)
            .request()
            .delete();
    }
}
