package runners;


import io.cucumber.junit.Cucumber;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty",
                "html:target/cucumber-reports/cucumber-pretty",
                "json:target/cucumber-reports/CucumberTestReport.json",
                "json:target/cucumber.json"},
        features = {"src/test/resources/features"},
        glue = {"stepdefinitions"},
        tags = "@api",
        monochrome = true)

public class TestRunner extends AbstractTestNGCucumberTests {
}