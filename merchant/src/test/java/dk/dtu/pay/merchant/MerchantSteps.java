package dk.dtu.pay.merchant;

import com.rabbitmq.client.Channel;
import dk.dtu.pay.utils.messaging.Event;
import dk.dtu.pay.utils.messaging.MessageQueue;
import dk.dtu.pay.utils.models.AccountId;
import dk.dtu.pay.utils.models.Role;
import dk.dtu.pay.utils.models.User;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Consumer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class MerchantSteps {

    @Given(": Set number to {int}")
    public void setNumberTo(int arg0) {
    }

    @When(": Add {int} to number")
    public void addToNumber(int arg0) {
    }

    @Then(": Result is {int}")
    public void resultIs(int arg0) {
    }
}
