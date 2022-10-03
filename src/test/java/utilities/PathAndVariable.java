package utilities;

import io.cucumber.java.Scenario;
import org.junit.Assume;
import org.openqa.selenium.WebDriver;
import stepdefinitions.StepDefinitions;

import java.io.File;
import java.io.FileReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class PathAndVariable {
    public static Scenario scenario_name = null;
    public static String scenario = null;
    public static HashMap<String, String> variables = new HashMap<>();
    public static HashMap<String, String> form_params = new HashMap<>();
    public static String statusCode;
    public static String message;
    public static String params = null;
    public static String api_path;
    public static String basic_auth;
    public static String email = "Automation.User@airtel.com";
    public static String time_difference;
    public static String feature = "";
    public static String flag = "No";
    public static String csv;
    public static String response_value;
    public static String command;
    public static String service;
    static Random random = new Random();

    public static WebDriver driver2 = null;
    public static String screenshot_name;
    public static String log_name;
    public static String Downloads;
    public static String report_Name;
    public static int random_value = random.nextInt(10000);
    public static String env = null;
    public static String slack = "No";
    public static String path = System.getProperty("user.dir");
    public static String api_type;
    public static String parent = new File(path).getParent();
    public static String prop_dir;
    public static HashMap<String, HashMap<String, String>> saving_all_details = new HashMap<>();
    public static DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
    public static DateFormat dateFormat2 = new SimpleDateFormat("HH-mm-ss");
    public static Date date = new Date();
    public static String date1 = dateFormat.format(date);
    public static String timestamp = dateFormat2.format(date);
    public static WebDriver driver = null;
    public static String url;
    public static String endpoint = "";
    public static String api_username;
    public static String api_pwd = "";
    public static String body;
    public static String tags;
    public static String xpath_dir;
    public static Properties prop = new Properties();
    public static ArrayList<String> selected_tabs = new ArrayList<>();
    public static FileReader fr;
    public static String error;
    public static String api_value;
    public static String encryptedPin;

//    public static void path_creation() {
//        try {
//            File theDir;
//            boolean file = false;
//            String folder;
//            if (PathAndVariable.tags.toLowerCase().contains("regression")) {
//                folder = "Regression";
//            } else if (PathAndVariable.tags.toLowerCase().contains("sanity")) {
//                folder = "Sanity";
//            } else {
//                folder = "TestRun";
//            }
//            PathAndVariable.screenshot_name = PathAndVariable.parent + '/' + "screenshots_" + folder + '/' +
//                    PathAndVariable.tags.split("@")[1] + '/' + PathAndVariable.date1 + '/' + PathAndVariable.timestamp;
//            PathAndVariable.log_name = PathAndVariable.parent + '/' + "Logs_" + folder + '/' +
//                    PathAndVariable.tags.split("@")[1] + '/' + PathAndVariable.date1 + '/' + PathAndVariable.timestamp;
//            theDir = new File(PathAndVariable.screenshot_name);
//            PathAndVariable.report_Name = PathAndVariable.parent + '/' + "Reports_" + folder + '/' +
//                    PathAndVariable.tags.split("@")[1] + '/' + PathAndVariable.date1 + '/' + PathAndVariable.timestamp;
//            PathAndVariable.screenshot_name = PathAndVariable.screenshot_name + '/';

//            if (!theDir.exists()) {
//                file = theDir.mkdirs();
//            }
//            theDir = new File(PathAndVariable.log_name);
//            if (!theDir.exists()) {
//                file = theDir.mkdirs();
//            }
//            theDir = new File(PathAndVariable.report_Name);
//            if (!theDir.exists()) {
//                file = theDir.mkdirs();
//            }
//            PathAndVariable.Downloads = PathAndVariable.parent + "/Downloads/";
//            theDir = new File(PathAndVariable.Downloads);
//            if (!theDir.exists()) {
//                file = theDir.mkdirs();
//            }
//        } catch (Exception e) {
//            StepDefinitions.LOGGER.info("Error in folders creation");
//            Assume.assumeTrue(false);
//        }
//    }
}