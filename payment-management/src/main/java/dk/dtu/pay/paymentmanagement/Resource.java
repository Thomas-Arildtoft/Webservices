package dk.dtu.pay.paymentmanagement;

import dtu.ws.fastmoney.BankService;
import dtu.ws.fastmoney.BankServiceService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/payments")
public class Resource {


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getMessage() {
        return "works works";
    }

    // POST pay(customerToken, merchantId, amount) - sends money from customer's account to merchant's account

    // GET getAllManager() - returns all transactions

    // @Path("/customer/{customerId})
    // GET getAllCustomer(customerId) - returns all payments (amount, merchantId, token) associated with customer's account id

    // @Path("/merchant/{merchantId})
    // GET getAllMerchant(merchantId) - returns all payments (amount, token) associated with merchant's account id

}