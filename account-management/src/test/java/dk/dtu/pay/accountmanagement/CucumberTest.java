package dk.dtu.pay.accountmanagement;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.CucumberOptions.SnippetType;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = "summary", snippets = SnippetType.CAMELCASE, features = "src/test/resources")
public class CucumberTest {
}
