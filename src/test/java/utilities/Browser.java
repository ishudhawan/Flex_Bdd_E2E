package utilities;

import org.junit.Assume;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.interactions.Actions;
import stepdefinitions.StepDefinitions;

import java.io.FileReader;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class Browser {

//    public static String temp = "";
//    public static String screenshot_name;
//    public static ArrayList<String> errorScreenShot_name = new ArrayList<String>();
//    public static boolean isScreenShotAttach = false;

    public static void generate_error(String error) {
        generate_logs("Error", error, "");
        save_in_map("Error", error);
        take_screenshot("Error");
        StepDefinitions.status = "Fail";
        StepDefinitions.feature_check = 1;
        close();
        Assume.assumeTrue(false);
    }

    public static void openURL(String url) {
        String webUrl;
        try {
            PathAndVariable.fr = new FileReader(PathAndVariable.prop_dir);
            PathAndVariable.prop.load(PathAndVariable.fr);
            webUrl = PathAndVariable.prop.getProperty(url);
            Browser.generate_logs("Info", "Property file is: ", PathAndVariable.prop_dir);
            Browser.generate_logs("Info", "URL is: ", webUrl);
            if (webUrl != null) {
                PathAndVariable.driver.get(webUrl);
//                PathAndVariable.driver.navigate().to(new URL(webUrl));
                Browser.generate_logs("Info", "Opening the URL: ", webUrl);
            } else {
                PathAndVariable.driver.get(url);
                Browser.generate_logs("Info", "Opening the URL: ", url);
            }
        } catch (Exception e) {
            PathAndVariable.error = "Error while opening url: " + e;
            Browser.generate_error(PathAndVariable.error);
        }
    }

    public static String getTitle() {
        PathAndVariable.driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        generate_logs("Info", "Getting title of page " + PathAndVariable.driver.getTitle(), "");
        return PathAndVariable.driver.getTitle();
    }

    public static void close() {
        if (PathAndVariable.driver != null) {
            generate_logs("Info", "Closing the webDriver " + PathAndVariable.driver, "");
            PathAndVariable.driver.quit();
        }
    }

    public static void save_in_map(String var, String text) {
        PathAndVariable.saving_all_details.get(PathAndVariable.scenario).put(var, text);
    }

    public static void generate_logs(String type, String text, String obj) {
        if (type.equalsIgnoreCase("INFO"))
            StepDefinitions.LOGGER.info(text + " " + obj);
        else if (type.equalsIgnoreCase("Error"))
            StepDefinitions.LOGGER.error(text + " " + obj);
        else if (type.equalsIgnoreCase("Debug"))
            StepDefinitions.LOGGER.debug(text + " " + obj);
    }

    public static void scroll_horizontally_with_tab(int tab) {
        try {
            Actions act = new Actions(PathAndVariable.driver);
            for (int i = 1; i <= tab; i++) {
                act.sendKeys(Keys.TAB).build().perform();
            }
            take_screenshot("");
            generate_logs("Info", "Performing horizontal scroll with tab value" + tab, "");
        } catch (Exception e) {
            PathAndVariable.error = "Error while pressing tab";
            Browser.generate_error(PathAndVariable.error);
        }
    }

    public static void press_down_arrow(int tab) {
        try {
            Actions act = new Actions(PathAndVariable.driver);
            for (int i = 1; i <= tab; i++) {
                act.sendKeys(Keys.ARROW_DOWN).build().perform();
            }
            take_screenshot("");
            generate_logs("Info", "Performing down arrow action with tab count " + tab, "");
        } catch (Exception e) {
            PathAndVariable.error = "Error while pressing down arrow";
            Browser.generate_error(PathAndVariable.error);
        }
    }

    public static void force_wait(int time) {
        try {
            time = time * 1000;
            Thread.sleep(time);
            generate_logs("Info", "Force sleep for " + time / 1000 + " seconds", "");
        } catch (Exception e) {
            PathAndVariable.error = "Error while waiting for " + time / 1000 + " seconds";
            Browser.generate_error(PathAndVariable.error);
        }
    }

    public static void take_screenshot(String name) {
        try {
            if (PathAndVariable.driver != null) {
                if (PathAndVariable.driver instanceof TakesScreenshot) {
                    PathAndVariable.scenario_name.attach(((TakesScreenshot) PathAndVariable.driver).getScreenshotAs(OutputType.BYTES), "image/png", "Screenshot");
                }
            }
        } catch (Exception e) {
            Browser.generate_logs("Error", "Unable to take screenshot", name);
        }
    }
}