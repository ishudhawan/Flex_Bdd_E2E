package runners;


import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.*;
import stepdefinitions.StepDefinitions;
import utilities.Log4j2Config;
import utilities.Mail;
import utilities.PathAndVariable;
//import org.testng.annotations.AfterClass;
import utilities.Reporting;

@CucumberOptions(
        plugin = {"pretty",
                "html:target/cucumber-reports/cucumber-pretty",
                "json:target/cucumber.json"},
        features = {"src/test/resources/features"},
        glue = {"stepdefinitions"},
        tags = "@api",
        monochrome = true)

public class TestRunner extends AbstractTestNGCucumberTests {
//    public TestNGCucumberRunner testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
//        @AfterClass(alwaysRun = true)
//        public void tearDownClass() {
//            testNGCucumberRunner.finish();
//            StepDefinitions.feature_check = 0;
//            StepDefinitions.initialize = 0;
//            Log4j2Config.logger.info("=========================================================================================================================================================================");
//            Log4j2Config.logger.info("                                      Feature << " + PathAndVariable.feature.toUpperCase() + " >> execution ended");
//            Log4j2Config.logger.info("=========================================================================================================================================================================\n\n");
//            PathAndVariable.feature = "";
//            new Reporting().configureReport();
//            if (PathAndVariable.Telegram.equalsIgnoreCase("Yes")) {
//                Telegram.send_file();
//            }
//            new Mail().sendMail();
//        }
}
