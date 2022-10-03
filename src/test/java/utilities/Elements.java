package utilities;

import io.cucumber.java.Scenario;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.util.*;


public class Elements {
    static WebElement element;
    static List<WebElement> list = new ArrayList<>();

    static {
        try {
            PathAndVariable.fr = new FileReader(PathAndVariable.xpath_dir);
            PathAndVariable.prop.load(PathAndVariable.fr);
        } catch (IOException e) {
            PathAndVariable.error = "Error while loading xpath file";
            Browser.generate_error(PathAndVariable.error);
        }
    }

    public static void compareValues(String actual_value, String var, String check) {
        boolean result = actual_value.trim().equalsIgnoreCase(var.trim());
        switch (check) {
            case "Equal":
                if (result) {
                    Browser.take_screenshot(check);
                    Browser.generate_logs("INFO", "Actual result = << " + actual_value, "");
                    Browser.generate_logs("INFO", "Expected result = <<  " + var, "");
                    Browser.generate_logs("INFO", "Assertion passed", "");
                } else {
                    PathAndVariable.error = "The value that we got was " + actual_value + " and we were expecting it to be " + var;
                    Browser.generate_error(PathAndVariable.error);
                }
                break;
            case "Not Equal":
                if (!result) {
                    Browser.take_screenshot(check);
                    Browser.generate_logs("Info", "Actual result = <<  " + actual_value, "");
                    Browser.generate_logs("Info", "Expected result = <<  " + var, "");
                    Browser.generate_logs("INFO", "Assertion passed", "");
                } else {
                    if (!actual_value.equals("") && !var.equals("")) {
                        PathAndVariable.error = "The value that we got was " + actual_value + " and we were expecting " +
                                "it not to be " + var;
                    } else {
                        PathAndVariable.error = "The value that we got was blank and we were expecting it not to be " +
                                "blank";
                    }
                    Browser.generate_error(PathAndVariable.error);
                }
                break;
            case "Not Contains":
                if (var.contains(actual_value)) {
                    PathAndVariable.error = "The value that we got was " + actual_value + " and we were expecting it " +
                            "not to contain in " + var;
                    Browser.generate_error(PathAndVariable.error);
                } else {
                    Browser.take_screenshot(actual_value);
                    Browser.generate_logs("Info", actual_value + " does not contains in " + var, "");
                    Browser.generate_logs("INFO", "Assertion passed", "");
                }
                break;
            case "Contains":
                if (actual_value.contains(var)) {
                    Browser.take_screenshot(actual_value);
                    Browser.generate_logs("Info", var + "contains in" + actual_value, "");
                    Browser.generate_logs("INFO", "Assertion passed", "");
                } else {
                    PathAndVariable.error = "The value that we got was " + actual_value + " and we were expecting it to be" +
                            "contained in " + var;
                    Browser.generate_error(PathAndVariable.error);
                }
                break;
        }
    }

    public static void click(String obj) {
        try {
            PathAndVariable.fr = new FileReader(PathAndVariable.xpath_dir);
            PathAndVariable.prop.load(PathAndVariable.fr);
            element = PathAndVariable.driver.findElement(By.xpath(PathAndVariable.prop.getProperty(obj)));
            element.click();
            Browser.generate_logs("Info", "Clicking on the element", obj);
        } catch (Exception e) {
            click_element(obj);
        }
    }

    public static void click_element(String obj) {
        try {
            PathAndVariable.fr = new FileReader(PathAndVariable.xpath_dir);
            PathAndVariable.prop.load(PathAndVariable.fr);
            element = PathAndVariable.driver.findElement(By.xpath(PathAndVariable.prop.getProperty(obj)));
            JavascriptExecutor js = (JavascriptExecutor) PathAndVariable.driver;
            js.executeScript("arguments[0].scrollIntoView(true);", element);
            js.executeScript("arguments[0].click();", element);
            Browser.generate_logs("Info", "Clicking on the element", obj);
        } catch (Exception e) {
            PathAndVariable.error = "Exception while clicking element  " + obj + " Exception-" + e;
            Browser.generate_error(PathAndVariable.error);
        }
    }

    public static void new_agent_login() {
        try {
            WebDriverManager.chromedriver().setup();
            HashMap<String, Object> chromePrefs = new HashMap<>();
            chromePrefs.put("profile.default_content_settings.popups", 0);
            chromePrefs.put("download.default_directory", PathAndVariable.Downloads);
            ChromeOptions options = new ChromeOptions();
            options.setExperimentalOption("prefs", chromePrefs);
            options.setAcceptInsecureCerts(true);
            options.addArguments("use-fake-device-for-media-stream");
            options.addArguments("use-fake-ui-for-media-stream");
            options.addArguments("--allow-insecure-localhost");
            options.addArguments("Headless");
            PathAndVariable.driver2 = new ChromeDriver(options);
            PathAndVariable.driver2.manage().window().maximize();
            PathAndVariable.fr = new FileReader(PathAndVariable.prop_dir);
            PathAndVariable.prop.load(PathAndVariable.fr);
            String webUrl = PathAndVariable.prop.getProperty("flex");
            PathAndVariable.driver2.navigate().to(new URL(webUrl));
            WebDriverWait w = new WebDriverWait(PathAndVariable.driver2, 120);
            String res = PathAndVariable.prop.getProperty("username");
            w.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(res)));
            PathAndVariable.fr = new FileReader(PathAndVariable.xpath_dir);
            PathAndVariable.prop.load(PathAndVariable.fr);
            WebElement element = PathAndVariable.driver2.findElement(By.xpath(PathAndVariable.prop.getProperty("username")));
            element.sendKeys("idhawan+11@twilio.com");
            element = PathAndVariable.driver2.findElement(By.xpath(PathAndVariable.prop.getProperty("password")));
            element.sendKeys("testdemo");
            Browser.take_screenshot("signin");
            element = PathAndVariable.driver2.findElement(By.xpath(PathAndVariable.prop.getProperty("signin")));
            element.click();
            res = PathAndVariable.prop.getProperty("offline");
            w.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(res)));
            element = PathAndVariable.driver2.findElement(By.xpath(PathAndVariable.prop.getProperty("offline")));
            element.click();
            Browser.take_screenshot("offline");
            element = PathAndVariable.driver2.findElement(By.xpath(PathAndVariable.prop.getProperty("available")));
            element.click();
            element = PathAndVariable.driver2.findElement(By.xpath(PathAndVariable.prop.getProperty("close_notification")));
            Browser.take_screenshot("available");
            element.click();
        } catch (Exception e) {
            PathAndVariable.error = "The agent was unable to login";
            Browser.generate_error(PathAndVariable.error);
        }
    }

    public static void mouse_hover(String obj) {
        try {
            Actions actions = new Actions(PathAndVariable.driver);
            PathAndVariable.fr = new FileReader(PathAndVariable.xpath_dir);
            PathAndVariable.prop.load(PathAndVariable.fr);
            element = PathAndVariable.driver.findElement(By.xpath(PathAndVariable.prop.getProperty(obj)));
            actions.moveToElement(element).perform();
        } catch (Exception e) {
            PathAndVariable.error = "Mouse hover the element";
            Browser.generate_error(PathAndVariable.error);
        }
    }

    public static void agent_acceptance() {
        try {
            WebElement element = PathAndVariable.driver2.findElement(By.xpath(PathAndVariable.prop.getProperty("accept_call")));
            element.click();
            WebDriverWait w = new WebDriverWait(PathAndVariable.driver2, 120);
            String res = PathAndVariable.prop.getProperty("hang_up");
            w.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(res)));
            Thread.sleep(3000);
            Browser.take_screenshot("hang_up");
            element = PathAndVariable.driver2.findElement(By.xpath(PathAndVariable.prop.getProperty("hang_up")));
            element.click();
            res = PathAndVariable.prop.getProperty("complete_call");
            w.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(res)));
            Thread.sleep(3000);
            Browser.take_screenshot("complete_call");
            element = PathAndVariable.driver2.findElement(By.xpath(PathAndVariable.prop.getProperty("complete_call")));
            element.click();
            Thread.sleep(3000);
            PathAndVariable.driver2.quit();
        } catch (Exception e) {
            PathAndVariable.error = "The agent was unable to accept the transferred call";
            Browser.generate_error(PathAndVariable.error);
        }
    }

    public static void input(String text, String obj) {
        try {
            PathAndVariable.fr = new FileReader(PathAndVariable.xpath_dir);
            PathAndVariable.prop.load(PathAndVariable.fr);
            element = PathAndVariable.driver.findElement(By.xpath(PathAndVariable.prop.getProperty(obj)));
            JavascriptExecutor js = (JavascriptExecutor) PathAndVariable.driver;
            js.executeScript("arguments[0].scrollIntoView(true);", element);
            if (PathAndVariable.driver.findElement(By.xpath(PathAndVariable.prop.getProperty(obj))).isDisplayed()) {
                if (!obj.equals("path")) {
                    element.clear();
                }
                element.sendKeys(text);
                Browser.take_screenshot(obj);
                Browser.generate_logs("Info", "Entering the " + text + " value in input field ", obj);
            }
        } catch (Exception e) {
            PathAndVariable.error = "We were unable to put text " + text + " in element " + obj;
            Browser.generate_error(PathAndVariable.error);
        }
    }

    public static void select_option_from_dropdown(String obj, String text) {
        try {
            Select drdown = new Select(PathAndVariable.driver.findElement(By.xpath(PathAndVariable.prop.getProperty(obj))));
            drdown.selectByVisibleText(text);
            Browser.take_screenshot(obj);
            Browser.generate_logs("Info", "Selecting  " + text + " from dropdown", obj);
        } catch (Exception e) {
            PathAndVariable.error = "Exception while selecting from dropdown " + obj;
            Browser.generate_error(PathAndVariable.error);
        }
    }

    public static String getVariable(String obj) {
        String result = "";
        try {
            result = PathAndVariable.variables.get(obj + "_" + PathAndVariable.random_value);
        } catch (Exception e) {
            PathAndVariable.error = "Exception while getting value of variable " + obj;
            Browser.generate_error(PathAndVariable.error);
        }
        return result;
    }

    public static void select_value_from_dropdown(String obj, String option) {
        try {

            Select drdown = new Select(PathAndVariable.driver.findElement(By.xpath(PathAndVariable.prop.getProperty(obj))));
            drdown.selectByValue(option);
            Browser.take_screenshot(obj);
            Browser.generate_logs("Info", "Selecting  " + option + " from dropdown", obj);
        } catch (Exception e) {
            e.printStackTrace();
            PathAndVariable.error = "Exception while selecting from dropdown " + obj;
            Browser.generate_error(PathAndVariable.error);
        }
    }

    public static void waitForElement(String obj) {
        try {
            PathAndVariable.fr = new FileReader(PathAndVariable.xpath_dir);
            PathAndVariable.prop.load(PathAndVariable.fr);
            WebDriverWait w = new WebDriverWait(PathAndVariable.driver, 120);
            String res = PathAndVariable.prop.getProperty(obj);
            w.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(res)));
            Browser.take_screenshot(obj);
            Browser.generate_logs("Info", "Waiting for the element", obj);
        } catch (Exception e) {
            PathAndVariable.error = "Exception while waiting for object for 120 seconds: " + obj;
            Browser.generate_error(PathAndVariable.error);
        }
    }

    public static void selectCheckbox(String obj) {
        element = PathAndVariable.driver.findElement(By.xpath(PathAndVariable.prop.getProperty(obj)));
        try {
            if (element.getAttribute("aria-selected").equals("true")) {
                Browser.take_screenshot(obj);
                Browser.generate_logs("Info", "Checkbox " + obj + " is already checked", "");
            } else {
                element.click();
                Browser.take_screenshot(obj);
                Browser.generate_logs("Info", "Clicking the checkbox: ", obj);
            }
        } catch (Exception e) {
            PathAndVariable.error = "Exception while selecting the checkbox" + obj;
            Browser.generate_error(PathAndVariable.error);
        }
    }

    public static void verify_existence(String obj) {
        try {
            PathAndVariable.driver.findElement(By.xpath(PathAndVariable.prop.getProperty(obj)));
            Browser.take_screenshot(obj);
            Browser.generate_logs("Info", "Element " + obj + " is present", "");
        } catch (Exception e) {
            PathAndVariable.error = "Exception while Verifying Existence of object " + obj;
            Browser.generate_error(PathAndVariable.error);
        }
    }

    public static void verify_nonexistence(String obj) {
        boolean flag = false;
        try {
            PathAndVariable.driver.findElement(By.xpath(PathAndVariable.prop.getProperty(obj)));
            flag = true;
        } catch (Exception e) {
            Browser.take_screenshot(obj);
            Browser.generate_logs("Info", "Element " + obj + " is not present", "");
        }
        if (flag) {
            PathAndVariable.error = "Existence of object " + obj + " found when not expecting";
            Browser.generate_error(PathAndVariable.error);
        }
    }

    public static void actionClick(String obj, String useronboarding) {
        try {
            element = PathAndVariable.driver.findElement(By.xpath(PathAndVariable.prop.getProperty(obj)));
            Actions action = new Actions(PathAndVariable.driver);
            action.moveToElement(element).click().perform();
            PathAndVariable.driver.findElement(By.xpath(PathAndVariable.prop.getProperty(useronboarding))).click();
            Browser.take_screenshot(obj);
            Browser.generate_logs("Info", "Mouse Click operation performed on element ", obj);
        } catch (Exception e) {
            PathAndVariable.error = "We were unable to select " + useronboarding + " from dropdown " + obj;
            Browser.generate_error(PathAndVariable.error);
        }
    }

    public static String getTextFromAll(String obj) {
        try {
            element = PathAndVariable.driver.findElement(By.xpath(PathAndVariable.prop.getProperty(obj)));
            JavascriptExecutor js = (JavascriptExecutor) PathAndVariable.driver;
            js.executeScript("arguments[0].scrollIntoView(true);", element);
            Browser.take_screenshot(obj);
            Browser.generate_logs("Info", "Getting text from ", obj);
        } catch (Exception e) {
            PathAndVariable.error = "We were unable to get text for object " + obj;
            Browser.generate_error(PathAndVariable.error);
        }
        return element.getText();
    }

    public static void ClearText(String obj) {
        try {
            element = PathAndVariable.driver.findElement(By.xpath(PathAndVariable.prop.getProperty(obj)));
            element.clear();
            Browser.generate_logs("Info", "clear text value", obj);
        } catch (Exception e) {
            PathAndVariable.error = "Exception while clearing a text box " + obj;
            Browser.generate_error(PathAndVariable.error);
        }
    }

    public static void inputAndPressEnter(String text, String obj) {
        try {
            Browser.force_wait(1);
            element = PathAndVariable.driver.findElement(By.xpath(PathAndVariable.prop.getProperty(obj)));
            input(text, obj);
            element.sendKeys(Keys.ENTER);
            Browser.generate_logs("Info", "Press Enter after entering " + text + " in ", obj);
        } catch (Exception e) {
            PathAndVariable.error = "We were unable to put text  " + text + " in element " + obj;
            Browser.generate_error(PathAndVariable.error);
        }
    }

    public static void TabSwitch(String text) {
        String mainwindow = PathAndVariable.driver.getWindowHandle();
        Set<String> s1 = PathAndVariable.driver.getWindowHandles();
        Iterator<String> i1 = s1.iterator();
        try {
            if (text.equalsIgnoreCase("Next")) {
                while (i1.hasNext()) {
                    String ChildWindow = i1.next();
                    if (!mainwindow.equalsIgnoreCase(ChildWindow)) {
                        PathAndVariable.driver.switchTo().window(ChildWindow);
                    }
                }
            } else if (text.equalsIgnoreCase("Previous")) {
                PathAndVariable.driver.switchTo().window(mainwindow);
            }
            Browser.generate_logs("Info", "switching to " + text + " tab ", text);
        } catch (Exception e) {
            PathAndVariable.error = "Exception while switching to next tab";
            Browser.generate_error(PathAndVariable.error);
        }
    }

    public static void AcceptAlerts() {
        try {
            Alert alert = PathAndVariable.driver.switchTo().alert();
            alert.accept();
            Browser.generate_logs("Info", "Accepting to Alert", "");
        } catch (Exception e) {
            PathAndVariable.error = "Exception while accepting alert";
            Browser.generate_error(PathAndVariable.error);
        }
    }

    public static void AcceptAlertsIfPresent() {
        try {
            WebDriverWait wait = new WebDriverWait(PathAndVariable.driver, 3);
            if (wait.until(ExpectedConditions.alertIsPresent()) == null)
                System.out.println("alert was not present");
            else {
                System.out.println("alert was present");
                Alert alert = PathAndVariable.driver.switchTo().alert();
                alert.accept();
                Browser.generate_logs("Info", "Accepting to Alert", "");
            }
        } catch (Exception e) {
        }

    }

    public static void Scroll(String obj, String value) {
        try {
            if (value.equals("false")) {
                element = PathAndVariable.driver.findElement(By.xpath(PathAndVariable.prop.getProperty(obj)));
                ((JavascriptExecutor) PathAndVariable.driver).executeScript("arguments[0].scrollIntoView(false);", element);
            } else if (value.equals("true")) {
                element = PathAndVariable.driver.findElement(By.xpath(PathAndVariable.prop.getProperty(obj)));
                ((JavascriptExecutor) PathAndVariable.driver).executeScript("arguments[0].scrollIntoView(true);", element);
            }
            Browser.generate_logs("Info", "Scroll down to ", obj);
        } catch (Exception e) {
            PathAndVariable.error = "Exception while Scrolling";
            Browser.generate_error(PathAndVariable.error);
        }
    }

    public static void CheckBoxCheck(String obj) {
        try {
            PathAndVariable.selected_tabs.add(obj);
            String is_clicked = PathAndVariable.driver.findElement(By.xpath("//span[text()='" + obj + "']/ancestor::tr"))
                    .getAttribute("aria-selected");
            if (is_clicked.contains("false")) {
                PathAndVariable.driver.findElement(By.xpath("//span[text()='"
                        + obj + "']/ancestor::tr[@aria-selected='false']"));
                Browser.force_wait(1);
                try {
                    WebElement element = PathAndVariable.driver.findElement(By.xpath("//span[text()='" + obj
                            + "']/ancestor::tr//span[@role='checkbox']"));
                    ((JavascriptExecutor) PathAndVariable.driver).executeScript("arguments[0].click();", element);
                } catch (Exception e) {
                    Browser.force_wait(1);
                    JavascriptExecutor js = (JavascriptExecutor) PathAndVariable.driver;
                    js.executeScript("window.scrollBy(0,300)");
                    WebElement element = PathAndVariable.driver.findElement(By.xpath("//span[text()='" + obj
                            + "']/ancestor::tr//span[@role='checkbox']"));
                    ((JavascriptExecutor) PathAndVariable.driver).executeScript("arguments[0].click();", element);
                }
                Browser.generate_logs("Info", "Deselecting CheckBox ", obj);
            }
        } catch (Exception e) {
            PathAndVariable.error = "Exception while checking checkbox";
            Browser.generate_error(PathAndVariable.error);
        }
    }

    public static void UnCheckBoxCheck(String obj) {
        try {
            PathAndVariable.selected_tabs.add(obj);
            String is_clicked = PathAndVariable.driver.findElement(By.xpath("//span[text()='" + obj + "']/ancestor::tr"))
                    .getAttribute("aria-selected");

            if (is_clicked.contains("true")) {
                PathAndVariable.driver.findElement(By.xpath("//span[text()='"
                        + obj + "']/ancestor::tr[@aria-selected='true']"));
                Browser.force_wait(1);
                try {
                    element = PathAndVariable.driver.findElement(By.xpath("//span[text()='" + obj
                            + "']/ancestor::tr//span[@role='checkbox']"));
                    ((JavascriptExecutor) PathAndVariable.driver).executeScript("arguments[0].click();", element);
                } catch (Exception e) {
                    Browser.force_wait(1);
                    JavascriptExecutor js = (JavascriptExecutor) PathAndVariable.driver;
                    js.executeScript("window.scrollBy(0,300)");
                    PathAndVariable.driver.findElement(By.xpath("//span[text()='" + obj
                            + "']/ancestor::tr//span[@role='checkbox']")).click();
                }
                Browser.generate_logs("Info", "Selecting Checkbox ", obj);
            }
        } catch (Exception e) {
            PathAndVariable.error = "Exception while checking checkbox";
            Browser.generate_error(PathAndVariable.error);
        }
    }

    public static void WindowScroll() {
        try {
            Browser.force_wait(2);
            JavascriptExecutor js = (JavascriptExecutor) PathAndVariable.driver;
            js.executeScript("window.scrollBy(0,1000)");
            Browser.generate_logs("Info", "Scroll down", "");
        } catch (Exception e) {
            PathAndVariable.error = "Exception while Scrolling Window";
            Browser.generate_error(PathAndVariable.error);
        }
    }

    public static String SHA1(String text) {
        try {
            MessageDigest md;
            md = MessageDigest.getInstance("SHA-1");
            byte[] sha1hash;
            md.update(text.getBytes(StandardCharsets.ISO_8859_1), 0, text.length());
            sha1hash = md.digest();
            return convertToHex(sha1hash);
        } catch (Exception e) {
            PathAndVariable.error = "Exception while Generating encrypted data";
            Browser.generate_error(PathAndVariable.error);
        }
        return "";
    }

    private static String convertToHex(byte[] data) {
        try {
            StringBuilder buf = new StringBuilder();
            for (byte datum : data) {
                int halfbyte = (datum >>> 4) & 0x0F;
                int two_halfs = 0;
                do {
                    if (halfbyte <= 9)
                        buf.append((char) ('0' + halfbyte));
                    else
                        buf.append((char) ('a' + (halfbyte - 10)));
                    halfbyte = datum & 0x0F;
                } while (two_halfs++ < 1);
            }
            return buf.toString();
        } catch (Exception e) {
            PathAndVariable.error = "Exception while converting to hex data";
            Browser.generate_error(PathAndVariable.error);
        }
        return "";
    }

    public static void switchToFrame(String id) {
        try {
            PathAndVariable.driver.switchTo().frame(PathAndVariable.prop.getProperty(id));
        } catch (Exception e) {
            PathAndVariable.error = "Exception while switching to frame";
            Browser.generate_error(PathAndVariable.error);
        }
    }

    public static void defaultFrame() {
        try {
            PathAndVariable.driver.switchTo().defaultContent();
        } catch (Exception e) {
            PathAndVariable.error = "Exception while switching to default frame";
            Browser.generate_error(PathAndVariable.error);
        }
    }

    public static void doubleClick(String obj) {
        try {
            Actions act = new Actions(PathAndVariable.driver);
            element = PathAndVariable.driver.findElement(By.xpath(PathAndVariable.prop.getProperty(obj)));
            act.doubleClick(element).perform();
            Browser.generate_logs("Info", "Double click performed", obj);
        } catch (Exception e) {
            PathAndVariable.error = "Exception while performing double click " + obj;
            Browser.generate_error(PathAndVariable.error);
        }
    }

    public static void refresh() {
        PathAndVariable.driver.navigate().refresh();
    }

    public static byte[] generateIV(String type, String salt, String name) {
        byte[] iv = new byte[16];
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            if (type != null) {
                byte[] typeBytes = type.getBytes(StandardCharsets.UTF_8);
                md.update(typeBytes);
            }
            if (salt != null) {
                byte[] saltBytes = salt.getBytes(StandardCharsets.UTF_8);
                md.update(saltBytes);
            }
            if (name != null) {
                byte[] nameBytes = name.getBytes(StandardCharsets.UTF_8);
                md.update(nameBytes);
            }
            byte[] sha = md.digest();
            for (int scan = 0; scan < sha.length; scan++) {
                iv[scan % 16] = (byte) (iv[scan % 16] ^ sha[scan]);
            }
        } catch (Exception e) {
            PathAndVariable.error = "Exception while generating Iv Parameter spec";
            Browser.generate_error(PathAndVariable.error);
        }
        return iv;
    }
}