package stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import org.junit.Assume;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utilities.*;
import java.io.File;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

public class StepDefinitions {
    public static String status = "Pass";
    String value;
    String fetch_data = "";
    String[] arr;
    int len;
    static int counter = 1;
    public static int indexSI = 0;
    public static int initialize = 0;
    public static int feature_check = 0;
    Date startDate;
    Date endDate;
    String[] featureName;
    public static final Logger LOGGER = LoggerFactory.getLogger(StepDefinitions.class);

    @Before
    public void beforeScenario(Scenario scenario) {
        startDate = new Date();
        PathAndVariable.scenario = scenario.getName();
        PathAndVariable.scenario_name = scenario;
        if (initialize == 0) {
            PathAndVariable.tags = System.getProperty("cucumber.filter.tags");
            PathAndVariable.error = "No error";
            PathAndVariable.env = System.getProperty("env");
            PathAndVariable.slack = System.getProperty("Slack");
            PathAndVariable.email = System.getProperty("email");
            PathAndVariable.prop_dir = PathAndVariable.path + "/properties/" + PathAndVariable.env + ".properties";
            if (PathAndVariable.env.toLowerCase().contains("flex")) {
                PathAndVariable.xpath_dir = PathAndVariable.path + "/properties/" + "xpath_Flex.properties";
            }
            if (PathAndVariable.tags == null)
                PathAndVariable.tags = "Test run";
            PathAndVariable.path_creation();
        }
        PathAndVariable.params = null;
        PathAndVariable.api_username = null;
        PathAndVariable.saving_all_details.put(PathAndVariable.scenario, new HashMap<>());
        int start=scenario.getUri().toString().indexOf(File.separator+"features"+File.separator);
        int end=scenario.getUri().toString().indexOf(".");

        featureName=scenario.getUri().toString().substring(start,end).split(File.separator+"features"+File.separator);

        if (counter == 1) {
            counter++;
        }
        if (!status.equalsIgnoreCase("Pass") && PathAndVariable.feature.equalsIgnoreCase(featureName[1]) && !PathAndVariable.feature.equals("")) {
            status = "Skip";
            LOGGER.info("==============================================================================================================================\n");
            LOGGER.info("                                      Skipping the scenario << " + scenario.getName() + " >> as the previous scenario failed");
            Assume.assumeTrue(false);
        }
        else if (!PathAndVariable.feature.equals("") && !PathAndVariable.feature.equalsIgnoreCase(featureName[1])) {
            status = "Pass";
            LOGGER.info("=========================================================================================================================================================================\n");
            LOGGER.info("                              Feature << " + PathAndVariable.feature.toUpperCase() + " >> execution ended");
            PathAndVariable.feature = "";
            feature_check = 0;
        }
        else {
            status = "Pass";
        }
        if(feature_check == 0 ) {
            LOGGER.info("======================================================================================================================================================================================================");
            LOGGER.info("                                                  Feature << " + featureName[1] + " >> execution started");
        }
        LOGGER.info("======================================================================================================================================================================================================\n");
        LOGGER.info("                                              Scenario << " + scenario.getName() + " >> execution started");
        LOGGER.info("======================================================================================================================================================================================================\n");
    }

    @After
    public void afterScenario(Scenario scenario) {
        initialize = 1;
        feature_check = 1;
        endDate = new Date();
        long differenceInMilliSeconds
                = Math.abs(endDate.getTime() - startDate.getTime());
        long hours = (differenceInMilliSeconds / (60 * 60 * 1000)) % 24;
        long minutes = (differenceInMilliSeconds / (60 * 1000)) % 60;
        long seconds = (differenceInMilliSeconds / 1000) % 60;
        if (hours > 0 && minutes > 0) {
            PathAndVariable.time_difference = hours + " hours " + minutes + " minutes " + seconds + " seconds";
        } else if (hours == 0 && minutes > 0) {
            PathAndVariable.time_difference = minutes + " minutes " + seconds + " seconds";
        } else if (minutes == 0) {
            PathAndVariable.time_difference = seconds + " seconds";
        } else {
            PathAndVariable.time_difference = hours + " hours " + minutes + " minutes " + seconds + " seconds";
        }
        if (!status.equals("Skip")) {
            LOGGER.info("=========================================================================================================================================================================");
            LOGGER.info("                                      Scenario << " + scenario.getName() + " >> execution ended with status " + status.toUpperCase());
        }
        PathAndVariable.feature = featureName[1];
        indexSI++;
        PathAndVariable.saving_all_details.get(PathAndVariable.scenario).put("Index", String.valueOf(indexSI));
        PathAndVariable.saving_all_details.get(PathAndVariable.scenario).put("Feature", PathAndVariable.feature.toUpperCase());
        PathAndVariable.saving_all_details.get(PathAndVariable.scenario).put("Status", status);
        PathAndVariable.saving_all_details.get(PathAndVariable.scenario).put("Error", PathAndVariable.error);
        PathAndVariable.saving_all_details.get(PathAndVariable.scenario).put("Execution", String.valueOf(PathAndVariable.time_difference));
        switch (status) {
            case "Fail":
                PathAndVariable.saving_all_details.get(PathAndVariable.scenario).put("Status", "Fail");
//                new Reporting().updateResult(PathAndVariable.scenario, indexSI, PathAndVariable.feature, "Fail", PathAndVariable.error);
                break;
            case "Pass":
//                new Reporting().updateResult(PathAndVariable.scenario, indexSI, PathAndVariable.feature, "Pass", PathAndVariable.error);
                break;
            case "Skip":
//                new Reporting().updateResult(PathAndVariable.scenario, indexSI, PathAndVariable.feature, "Skip", PathAndVariable.error);
                break;
        }
    }

    @Given("I open the web window")
    public void iOpenTheWebWindow() {
        Driver.open_Browser();
    }

    @And("I double click on {string}")
    public void doubleClickOn(String text) {
        Elements.doubleClick(text);
    }

    @And("I press TAB {string} times")
    public void iPressTABTimes(int value) {
        Browser.scroll_horizontally_with_tab(value);
    }

    @And("I select from dropdown {string} with value {string}")
    public void iSelectOptionFromDropdownWithValue(String obj, String value) {
        if (value.isEmpty()) {
            Browser.generate_logs("INFO", "text is empty =" + value, "");
        } else {
            Elements.select_value_from_dropdown(obj, value);
        }
    }

    @And("I verify value of {string} to be null")
    public void iVerifyValueToBeNull(String obj) {
        fetch_data = Elements.getVariable(obj);
        if (fetch_data == null) {
            Browser.generate_logs("INFO", "text is empty = ", "");
        } else {
            PathAndVariable.error = "Exception while validating text is empty";
            Browser.generate_error(PathAndVariable.error);
        }
    }

    @And("I verify value of variable {string} to contain {string}")
    public void iVerifyValueOfVariableToContains(String var, String compare_with) {
        var = Elements.getVariable(var);
        Elements.compareValues(var, compare_with, "Contains");
    }

    @And("I wait for title {string}")
    public void iWaitForTitle(String text) {
        String get_title = Browser.getTitle();
        Elements.compareValues(text, get_title, "Equal");
    }

    @And("I select option from dropdown {string} with text {string}")
    public void iSelectOptionFromDropdownWithText(String obj, String text) {
        if (text.isEmpty()) {
            Browser.generate_logs("INFO", "text is empty =" + text, "");
        } else {
            Elements.select_option_from_dropdown(obj, text);
        }
    }

    @And("I close all browsers")
    public void iCloseAllBrowsers() {
        Browser.close();
    }

    @And("I verify existence of object {string}")
    public void iVerifyExistenceOfObject(String obj) {
        Elements.verify_existence(obj);
    }

    @And("I verify non existence of object {string}")
    public void iVerifyNonExistenceOfObject(String obj) {
        Elements.verify_nonexistence(obj);
    }

    @And("I select {string} checkbox")
    public void clickCheckbox(String obj) {
        Elements.selectCheckbox(obj);
    }

    @And("I press down arrow {string} times")
    public void iPressDownArrowTimes(int value) {
        Browser.press_down_arrow(value);
    }

    @And("I get text {string} and store in variable {string}")
    public void iGetTextAndStoreInVar(String obj, String var) {
        value = Elements.getTextFromAll(obj);
        PathAndVariable.variables.put(var + "_" + PathAndVariable.random_value, value);
    }
    @And("I add service {string} with URL and replace {string} to set API endpoint")
    public void iAddServiceWithURLToSetAPIEndpoint(String service, String value) {
        APISetUp.addService(service, value);
    }

    @And("I get authentication username {string} and password {string} for API authentication")
    public void iGetAuthenticationUsernameAndPasswordForAPIAuthentication(String user, String pass) {
        APISetUp.getUserPassCredentials(user, pass);
    }

    @And("I add header name {string} with header value {string} in API")
    public void iAddHeaderNameWithHeaderValueInAPI(String param, String value) {
        APISetUp.getHeaderValues(param, value);
    }
    
    @And("I hit {string} api with API endpoint and request body")
    public void iHitApiWithAPIEndpointAndRequestBody(String type) throws IOException {
        if (PathAndVariable.params != null) {
            PathAndVariable.params = PathAndVariable.params.replace(" ", "");
//            PathAndVariable.endpoint = PathAndVariable.endpoint + PathAndVariable.params;
        }
        PathAndVariable.message = "";
        HitRequest.send_basic_auth_post_request(PathAndVariable.body, type);
    }

    @And("I scroll to object {string} with value {string}")
    public void iScrollDownAndClickOn(String obj, String value) {
        Elements.Scroll(obj, value);
    }
    @And("I save response status to variable {string} and response body to variable {string}")
    public void iSaveResponseStatusToVariableAndResponseBodyToVariable(String status, String message) {
        PathAndVariable.endpoint = "";
        PathAndVariable.variables.put(status + "_" + PathAndVariable.random_value, PathAndVariable.statusCode);
        PathAndVariable.variables.put(message + "_" + PathAndVariable.random_value, PathAndVariable.message.toString());
    }

    @And("I verify value of {string} to be {string}")
    public void iVerifyValueOfToBe(String var, String compare_with) {
        fetch_data = Elements.getVariable(var);
        value = Elements.getVariable(compare_with);
        if (fetch_data != null && value != null) {
            Elements.compareValues(fetch_data, value, "Equal");
        } else if (fetch_data == null && value != null) {
            Elements.compareValues(var, value, "Equal");
        } else if (fetch_data != null) {
            Elements.compareValues(fetch_data, compare_with, "Equal");
        } else {
            Elements.compareValues(var, compare_with, "Equal");
        }
    }

    @And("I validate response tag {string} with value {string}")
    public void iValidateResponseTagWithValue(String tag, String var) {
        len = tag.split(",").length;
        arr = new String[len];
        String[] var_array = new String[len];
        if (tag.contains(",")) {
            arr = tag.split(",");
            var_array = var.split(",");
        } else {
            arr[0] = tag;
            var_array[0] = var;
        }
        for (int i = 0; i < len; i++) {
            String[] tag_name;
            tag_name = arr[i].split("__");
            arr[i] = tag_name[0];
            try {
                value = tag_name[1];
            } catch (Exception e) {
                value = "1";
            }
            if (PathAndVariable.variables.containsKey(arr[i])) {
                fetch_data = Elements.getVariable(arr[i]);
                value = APIParsing.getJSONTagValue(fetch_data, value);
            } else {
                value = APIParsing.getJSONTagValue(arr[i], value);
            }
            if (value.equals("No")) {
                PathAndVariable.error = "No Tag present in api";
                Browser.generate_error(PathAndVariable.error);
            } else {
                fetch_data = Elements.getVariable(var_array[i]);
                if (fetch_data != null) {
                    Elements.compareValues(value, fetch_data, "Equal");
                } else {
                    String[] arr1 = var_array[i].split("<");
                    try {
                        if (arr1[1] != null) {
                            String result = arr1[1].replace(">", "");
                            fetch_data = arr1[0] + Elements.getVariable(result);
                            Elements.compareValues(value, fetch_data, "Equal");
                        } else {
                            Elements.compareValues(value, arr1[0], "Equal");
                        }
                    } catch (Exception e) {
                        Elements.compareValues(value, arr1[0], "Equal");
                    }
                }
            }
        }
    }

    @And("I get authentication {string} for API authentication")
    public void iGetAuthenticationForAPIAuthentication(String auth) {
        APISetUp.getCredentials(auth);
    }

    @Given("I set api with URL {string}")
    public void iSetApiWithURL(String url) {
        PathAndVariable.api_pwd = "";
        PathAndVariable.flag = "No";
        PathAndVariable.body = "";
        APISetUp.createURL(url);
    }

    @And("I add query param {string} with value {string} in API")
    public void iAddQueryParamWithValueInAPI(String param, String var) {
        int len = param.split(",").length;
        String[] param_array = new String[len];
        String[] var_array = new String[len];
        if (param.contains(",")) {
            param_array = param.split(",");
            var_array = var.split(",");
        } else {
            param_array[0] = param;
            var_array[0] = var;
        }
        for (int i = 0; i < len; i++) {
            fetch_data = Elements.getVariable(var_array[i]);
            if (fetch_data != null) {
                APISetUp.getParams(param_array[i], fetch_data);
            } else {
                APISetUp.getParams(param_array[i], var_array[i]);
            }
        }
    }

    @And("I open the url {string}")
    public void iOpenTheUrl(String url) {
        Browser.openURL(url);
    }

    @And("I wait for {string}")
    public void iWaitFor(String obj) {
        Elements.waitForElement(obj);
    }

    @And("I click on {string}")
    public void iClickOn(String obj) {
        Elements.click(obj);
    }

    @And("I input text {string} to object {string}")
    public void iInputTextToObject(String text, String obj) {
        if ((obj.equals("username") && text.equals(""))){
            Elements.input(System.getProperty("username"), obj);
        }
        else if ((obj.equals("password") && text.equals(""))){
            Elements.input(System.getProperty("password"), obj);
        }
        else {
            fetch_data = Elements.getVariable(text);
            if (fetch_data != null) {
                Elements.input(fetch_data, obj);
            } else {
                Elements.input(text, obj);
            }
        }
    }

    @And("I add body param {string} with value {string} in API")
    public void iAddBodyParamWithValueInAPI(String param, String var) {
        int len = param.split(",").length;
        String[] param_array = new String[len];
        String[] var_array = new String[len];
        if (param.contains(",")) {
            param_array = param.split(",");
            var_array = var.split(",");
        } else {
            param_array[0] = param;
            var_array[0] = var;
        }
        for (int i = 0; i < len; i++) {
            fetch_data = Elements.getVariable(var_array[i]);
            if (fetch_data != null) {
                APISetUp.getBodyParams(param_array[i], fetch_data);
            } else {
                APISetUp.getBodyParams(param_array[i], var_array[i]);
            }
        }
    }

    @And("I save value in {string} response tag {string} to variable {string}")
    public void iSaveValueInStringResponseTagStringToVariableString(String type, String tag, String var) {
        len = tag.split(",").length;
        arr = new String[len];
        String[] var_array = new String[len];
        if (tag.contains(",")) {
            arr = tag.split(",");
            var_array = var.split(",");
        } else {
            arr[0] = tag;
            var_array[0] = var;
        }
        for (int i = 0; i < len; i++) {
            String[] tag_name;
            if (!arr[i].contains("recharged_at__gte") && !arr[i].contains("recharged_at__lte")) {
                tag_name = arr[i].split("__");
                arr[i] = tag_name[0];
                try {
                    value = tag_name[1];
                } catch (Exception e) {
                    value = "1";
                }
            } else {
                tag_name = arr[i].split("__");
                arr[i] = tag_name[0] + "__" + tag_name[1];
                try {
                    value = tag_name[2];
                } catch (Exception e) {
                    value = "1";
                }
            }
            if (PathAndVariable.variables.containsKey(arr[i])) {
                fetch_data = Elements.getVariable(arr[i]);
                value = APIParsing.getJSONTagValue(fetch_data, value);
            } else {
                value = APIParsing.getJSONTagValue(arr[i], value);
            }
            if (value.equals("No")) {
                PathAndVariable.error = "No Tag present in api";
                Browser.generate_error(PathAndVariable.error);
            } else {
                PathAndVariable.variables.put(var + "_" + PathAndVariable.random_value, value);
                    }
                }
            }

    @And("I force a sleep for {string} seconds")
    public void iForceASleepForSeconds(String time) {
        Browser.force_wait(Integer.parseInt(time));
    }

    @And("I accept alert box")
    public void iAcceptAlertBox() {
        PathAndVariable.driver.switchTo().alert().accept();
    }

    @And("I switch to window {string}")
    public void iSwitchToWindow(String window) {
        PathAndVariable.driver.switchTo().window(window);
    }

    @And("A different agent logs in")
    public void aDifferentAgentLogsIn(){
        Elements.new_agent_login();
    }

    @And("The call is accepted by the agent")
    public void theCallIsAcceptedByTheAgent(){
        Elements.agent_acceptance();
    }

    @And("I mouse hover on {string}")
    public void iMouseHoverOn(String obj) {
        Elements.mouse_hover(obj);

    }

    @And("I input text {string} to object {string} and press enter")
    public void iInputTextToObjectAndPressEnter(String text, String obj) {
        fetch_data = Elements.getVariable(text);
        if (fetch_data != null) {
            Elements.inputAndPressEnter(fetch_data, obj);
        } else {
            Elements.inputAndPressEnter(text, obj);
        }
    }
}
