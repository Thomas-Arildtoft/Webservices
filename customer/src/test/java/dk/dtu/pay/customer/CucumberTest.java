package dk.dtu.pay.customer;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.CucumberOptions.SnippetType;
import org.junit.runner.RunWith;

/**
 * @author Thomas
 */
 
@RunWith(Cucumber.class)
@CucumberOptions(plugin = "summary", snippets = SnippetType.CAMELCASE, features = "src/test/resources")
public class CucumberTest {
}
