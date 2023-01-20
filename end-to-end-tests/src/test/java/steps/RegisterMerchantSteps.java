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
import utils.MerchantRestClient;
import utils.RepositoriesCleaner;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Piotr
 */
 
public class RegisterMerchantSteps {

    private final MerchantRestClient merchantRestClient = new MerchantRestClient();
    private final BankServiceUtils bankServiceUtils = new BankServiceUtils();

    private AccountId merchantAccountId;
    private Response response;

    @After
    public void cleanUp() {
        new RepositoriesCleaner().clean();
    }

    @Given(": [RegisterMerchant] Merchant {string} {string} with cpr {string} with an account balance of {int} exists")
    public void customerWithCprWithAnAccountBalanceOfExists(String name, String lastname, String cprNumber, int amount) {
        dtu.ws.fastmoney.User user = new dtu.ws.fastmoney.User();
        user.setFirstName(name);
        user.setLastName(lastname);
        user.setCprNumber(cprNumber);
        merchantAccountId = new AccountId(bankServiceUtils.registerOrGetAccountId(user, BigDecimal.valueOf(amount)));
    }

    @Given("Merchant's account id")
    public void customerSAccountId() {
    }

    @Given("Already registered merchant")
    public void alreadyRegisteredMerchant() {
        User user = merchantRestClient.register(merchantAccountId).readEntity(User.class);
        assertNotNull(user);
        assertEquals(Role.MERCHANT, user.getRole());
        assertNotNull(user.getId());
    }

    @Given("Not existing merchant account Id")
    public void notExistingMerchantAccountId() {
        merchantAccountId = new AccountId("AccountIdThatDoesNotExist");
    }

    @When("the merchant requests for registration")
    public void theCustomerRequestsForRegistration() {
        response = merchantRestClient.register(merchantAccountId);
    }

    @Then("the registration finishes successfully and registered merchant is returned")
    public void theRegistrationFinishesSuccessfullyAndRegisteredUserIsReturned() {
        User user = response.readEntity(User.class);
        assertNotNull(user);
        assertEquals(Role.MERCHANT, user.getRole());
        assertNotNull(user.getId());
    }


    @Then("merchant registration finishes with failure and the following message is returned {string}")
    public void merchantRegistrationFinishesWithFailureAndTheFollowingMessageIsReturned(String message) {
        String returnedMessage = response.readEntity(String.class);
        assertEquals(message, returnedMessage);
    }

}
