package utilities;

import io.cucumber.java.Scenario;
import org.apache.commons.io.FileUtils;
import org.junit.Assume;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.interactions.Actions;
import stepdefinitions.StepDefinitions;

import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Browser {

    public static String temp = "";
    public static String screenshot_name;
    public static ArrayList<String> errorScreenShot_name = new ArrayList<String>();
    public static boolean isScreenShotAttach = false;

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
        String webUrl = "";
        try {
            PathAndVariable.fr = new FileReader(PathAndVariable.prop_dir);
            PathAndVariable.prop.load(PathAndVariable.fr);
            webUrl = PathAndVariable.prop.getProperty(url);
            if (webUrl != null) {
                PathAndVariable.driver.navigate().to(new URL(webUrl));
                Browser.generate_logs("Info", "Opening the URl: ", webUrl);
            } else {
                PathAndVariable.driver.get(url);
                Browser.generate_logs("Info", "Opening the URl: ", url);
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

    public static String get_values(String var) {
        try {
            temp = PathAndVariable.saving_all_details.get(PathAndVariable.scenario).get(var);
            return temp;
        } catch (Exception e) {
            return null;
        }
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

    public void press_enter() {
        try {
            Actions act = new Actions(PathAndVariable.driver);
            act.sendKeys(Keys.ENTER).build().perform();
            take_screenshot("");
            generate_logs("Info", "Preforming enter action ", "");
        } catch (Exception e) {
            PathAndVariable.error = "Error while pressing enter";
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
//                String extension = ".png";
//                String spcl = "--";
//                Date date = new Date();
//                DateFormat dateFormat2 = new SimpleDateFormat("HH-mm-ss");
//                String timestamp = dateFormat2.format(date);
//                screenshot_name = PathAndVariable.screenshot_name + timestamp + spcl + name
//                        + spcl + PathAndVariable.scenario + spcl + PathAndVariable.random_value + extension;
//                TakesScreenshot scrShot = ((TakesScreenshot) PathAndVariable.driver);
//                File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
//                File DestFile = new File(screenshot_name);
//                FileUtils.copyFile(SrcFile, DestFile);
//                if (name.equalsIgnoreCase("Error")) {
//                    isScreenShotAttach = true;
//                    errorScreenShot_name.add(screenshot_name);
//                }
            }
        } catch (Exception e) {
            try {
                if (PathAndVariable.driver2 != null) {
//                    String extension = ".png";
//                    String spcl = "--";
//                    Date date = new Date();
//                    DateFormat dateFormat2 = new SimpleDateFormat("HH-mm-ss");
//                    String timestamp = dateFormat2.format(date);
//                    screenshot_name = PathAndVariable.screenshot_name + timestamp + spcl + name
//                            + spcl + PathAndVariable.scenario + spcl + PathAndVariable.random_value + extension;
//                    TakesScreenshot scrShot = ((TakesScreenshot) PathAndVariable.driver);
//                    File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
//                    File DestFile = new File(screenshot_name);
//                    FileUtils.copyFile(SrcFile, DestFile);
//                    if (name.equalsIgnoreCase("Error")) {
//                        isScreenShotAttach = true;
//                        errorScreenShot_name.add(screenshot_name);
//                    }
                }
            } catch (Exception ex) {
                Browser.generate_logs("Error", "Unable to take screenshot", name);
            }
        }
    }
}