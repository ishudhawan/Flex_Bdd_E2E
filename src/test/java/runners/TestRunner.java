package runners;


import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.*;
import org.testng.annotations.AfterClass;
import stepdefinitions.StepDefinitions;
import utilities.PathAndVariable;

@CucumberOptions(
        plugin = {"pretty",
                "html:target/cucumber-reports/cucumber-pretty",
                "json:target/cucumber.json",
                "com.epam.reportportal.cucumber.ScenarioReporter"},
        features = {"src/test/resources/features"},
        glue = {"stepdefinitions"},
        monochrome = true)

public class TestRunner extends AbstractTestNGCucumberTests {
//    public TestNGCucumberRunner testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
//        @AfterClass(alwaysRun = true)
//        public void tearDownClass() {
//            testNGCucumberRunner.finish();
//            StepDefinitions.feature_check = 0;
//            StepDefinitions.initialize = 0;
//            StepDefinitions.LOGGER.info("=========================================================================================================================================================================");
//            StepDefinitions.LOGGER.info("                                      Feature << " + PathAndVariable.feature.toUpperCase() + " >> execution ended");
//            StepDefinitions.LOGGER.info("=========================================================================================================================================================================\n\n");
//            PathAndVariable.feature = "";
//            new Reporting().configureReport();
//            new Mail().sendMail();
//        }
}
