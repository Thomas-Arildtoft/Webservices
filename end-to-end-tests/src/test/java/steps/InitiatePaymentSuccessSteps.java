package steps;

import dk.dtu.pay.utils.models.AccountId;
import dk.dtu.pay.utils.models.InitiatePaymentDTO;
import dk.dtu.pay.utils.models.Role;
import dk.dtu.pay.utils.models.User;
import dtu.ws.fastmoney.Account;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.Response;
import utils.BankServiceUtils;
import utils.CustomerRestClient;
import utils.MerchantRestClient;
import utils.RepositoriesCleaner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class InitiatePaymentSuccessSteps {

    private final CustomerRestClient customerRestClient = new CustomerRestClient();
    private final MerchantRestClient merchantRestClient = new MerchantRestClient();
    private final BankServiceUtils bankServiceUtils = new BankServiceUtils();
    private AccountId customerAccountId;
    private AccountId merchantAccountId;
    private Response response;
    private long previousCustomerBalance;
    private long previousMerchantBalance;

    @After
    public void cleanUp() {
        new RepositoriesCleaner().clean();
    }
    
    @Given(": [InitiatePayment] Customer {string} {string} with cpr {string} with an account balance of {int} exists")
    public void initiatePaymentCustomerWithCprWithAnAccountBalanceOfExists(String name, String lastname, String cprNumber, int amount) {
        dtu.ws.fastmoney.User user = new dtu.ws.fastmoney.User();
        user.setFirstName(name);
        user.setLastName(lastname);
        user.setCprNumber(cprNumber);
        customerAccountId = new AccountId(bankServiceUtils.registerOrGetAccountId(user, BigDecimal.valueOf(amount)));
    }

    @And(": [InitiatePayment] Merchant {string} {string} with cpr {string} with an account balance of {int} exists")
    public void initiatePaymentMerchantWithCprWithAnAccountBalanceOfExists(String name, String lastname, String cprNumber, int amount) {
        dtu.ws.fastmoney.User user = new dtu.ws.fastmoney.User();
        user.setFirstName(name);
        user.setLastName(lastname);
        user.setCprNumber(cprNumber);
        merchantAccountId = new AccountId(bankServiceUtils.registerOrGetAccountId(user, BigDecimal.valueOf(amount)));
    }

    @Given(": Customer and merchant are registered")
    public void customerAndMerchantAreRegistered() {
        User customerUser = customerRestClient.register(customerAccountId).readEntity(User.class);
        assertNotNull(customerUser);
        assertEquals(Role.CUSTOMER, customerUser.getRole());
        assertNotNull(customerUser.getId());
        User merchantUser = merchantRestClient.register(merchantAccountId).readEntity(User.class);
        assertNotNull(merchantUser);
        assertEquals(Role.MERCHANT, merchantUser.getRole());
        assertNotNull(merchantUser.getId());
    }


    @When(": Payment is of {int} kr is initiated")
    public void paymentIsOfKrIsInitiated(int amount) {
        previousCustomerBalance = bankServiceUtils.getAccount(customerAccountId).getBalance().longValue();
        previousMerchantBalance = bankServiceUtils.getAccount(merchantAccountId).getBalance().longValue();
        String customerToken = customerRestClient.getTokens(1)
                .readEntity(new GenericType<List<String>>(){})
                .get(0);
        response = merchantRestClient.initiatePayment(new InitiatePaymentDTO(null, customerToken, BigDecimal.valueOf(amount)));
    }

    @Then(": {int} kr where successfully transferred")
    public void krWhereSuccessfullyTransferred(int amount) {
        long actualCustomerBalance = bankServiceUtils.getAccount(customerAccountId).getBalance().longValue();
        long actualMerchantBalance = bankServiceUtils.getAccount(merchantAccountId).getBalance().longValue();
        String result = response.readEntity(String.class);
        assertNotNull(result);
        assertEquals("Payment success", result);
        assertEquals(amount, previousCustomerBalance - actualCustomerBalance);
        assertEquals(amount, actualMerchantBalance - previousMerchantBalance);
    }
}
