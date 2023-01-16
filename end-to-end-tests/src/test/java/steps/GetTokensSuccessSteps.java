package steps;

import dk.dtu.pay.utils.models.AccountId;
import dk.dtu.pay.utils.models.Role;
import dk.dtu.pay.utils.models.User;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.Response;
import utils.BankServiceUtils;
import utils.CustomerRestClient;
import utils.RepositoriesCleaner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class GetTokensSuccessSteps {

    private final CustomerRestClient customerRestClient = new CustomerRestClient();
    private final BankServiceUtils bankServiceUtils = new BankServiceUtils();
    private AccountId customerAccountId;
    private Response response;

    @Before
    public void cleanUp() {
        new RepositoriesCleaner().clean();
    }

    @Given(": [GetToken] Customer {string} {string} with cpr {string} with an account balance of {int} exists")
    public void customerWithCprWithAnAccountBalanceOfExists(String name, String lastname, String cprNumber, int amount) {
        dtu.ws.fastmoney.User user = new dtu.ws.fastmoney.User();
        user.setFirstName(name);
        user.setLastName(lastname);
        user.setCprNumber(cprNumber);
        customerAccountId = new AccountId(bankServiceUtils.registerOrGetAccountId(user, BigDecimal.valueOf(amount)));
    }

    @And(": Registered customer at DTUPay")
    public void customerIsRegisteredAtDTUPay() {
        response = customerRestClient.register(customerAccountId);
        User user = response.readEntity(User.class);
        assertNotNull(user);
        assertEquals(Role.CUSTOMER, user.getRole());
        assertNotNull(user.getId());
    }

    @When(": Customer requests for {int} tokens")
    public void customerRequestsForTokens(int numberOfTokens) {
        response = customerRestClient.getTokens(numberOfTokens);
    }

    @Then(": Customer receive {int} tokens")
    public void customerReceiveTokens(int numberOfTokens) {
        List<String> tokens = response.readEntity(new GenericType<List<String>>(){});
        assertNotNull(tokens);
        assertEquals(tokens.size(), numberOfTokens);
    }

}
