package steps;

import dk.dtu.pay.utils.models.AccountId;
import dk.dtu.pay.utils.models.Role;
import dk.dtu.pay.utils.models.User;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import jakarta.ws.rs.core.Response;
import utils.BankServiceUtils;
import utils.CustomerRestClient;
import utils.RepositoriesCleaner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * @author Piotr
 */
 
public class RegisterCustomerSteps {

    private final CustomerRestClient customerRestClient = new CustomerRestClient();
    private final BankServiceUtils bankServiceUtils = new BankServiceUtils();

    private AccountId customerAccountId;
    private Response response;

    @After
    public void cleanUp() {
        new RepositoriesCleaner().clean();
    }

    @Given(": [RegisterCustomer] Customer {string} {string} with cpr {string} with an account balance of {int} exists")
    public void customerWithCprWithAnAccountBalanceOfExists(String name, String lastname, String cprNumber, int amount) {
        dtu.ws.fastmoney.User user = new dtu.ws.fastmoney.User();
        user.setFirstName(name);
        user.setLastName(lastname);
        user.setCprNumber(cprNumber);
        customerAccountId = new AccountId(bankServiceUtils.registerOrGetAccountId(user, BigDecimal.valueOf(amount)));
    }

    @Given("Customer's account id")
    public void customerSAccountId() {
    }

    @Given("Already registered customer")
    public void alreadyRegisteredCustomer() {
        User user = customerRestClient.register(customerAccountId).readEntity(User.class);
        assertNotNull(user);
        assertEquals(Role.CUSTOMER, user.getRole());
        assertNotNull(user.getId());
    }

    @When("the customer requests for registration")
    public void theCustomerRequestsForRegistration() {
        response = customerRestClient.register(customerAccountId);
    }

    @Then("the registration finishes successfully and registered customer is returned")
    public void theRegistrationFinishesSuccessfullyAndRegisteredUserIsReturned() {
        User user = response.readEntity(User.class);
        assertNotNull(user);
        assertEquals(Role.CUSTOMER, user.getRole());
        assertNotNull(user.getId());
    }

    @Then("customer registration finishes with failure and the following message is returned {string}")
    public void theRegistrationFinishesWithFailureAndTheFollowingMessageIsReturned(String message) {
        String returnedMessage = response.readEntity(String.class);
        assertEquals(message, returnedMessage);
    }
    
}
