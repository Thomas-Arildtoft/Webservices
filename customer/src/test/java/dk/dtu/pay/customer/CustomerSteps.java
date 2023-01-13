package dk.dtu.pay.customer;

import com.rabbitmq.client.Channel;
import dk.dtu.pay.utils.messaging.Event;
import dk.dtu.pay.utils.messaging.MessageQueue;
import dk.dtu.pay.utils.models.AccountId;
import dk.dtu.pay.utils.models.User;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.*;
import java.util.function.Consumer;

import static org.junit.Assert.assertEquals;

public class CustomerSteps {

    private Queue<Event> publishedEvents = new LinkedList<>();
    private Service service = new Service(getMockedMessageQueue());
    private String accountId;
    private User user;


    @Given(": Customer has bank account id {word}")
    public void givenCustomerSBankAccountId(String aId) {
        accountId = aId;
    }

    @When(": Register merchant at DTU Pay")
    public void registerMerchantAtDTUPay() {
        user = service.register(new AccountId(accountId));
    }

    @Then(": User with role Customer and bank account id {word} is returned")
    public void userWithRoleCustomerAndBankAccountIdIsReturned(String accountId) {
        assertEquals(accountId, user.getId());
    }

    private MessageQueue getMockedMessageQueue() {
        return new MessageQueue() {

            @Override
            public void publish(String s, Event event) {
                publishedEvents.add(event);
            }

            @Override
            public Channel addHandler(String queueName, Consumer<Event> handler) {
                handler.accept(publishedEvents.poll());
                return null;
            }
        };
    }

}
