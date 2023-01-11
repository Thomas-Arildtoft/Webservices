package dtu.pay;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class PaymentSteps {

    int number;

    @Given("Number {int}")
    public void number(int n) {
        number = n;
    }

    @When("Add {int}")
    public void add(int value) {
        number += value;
    }

    @Then("Result {int}")
    public void result(int result) {
        assertEquals(result, number);
    }

}
