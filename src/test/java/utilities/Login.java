package utilities;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.io.FileReader;

public class Login {

    public static String fetch_user() {
        try {
            PathAndVariable.fr = new FileReader(PathAndVariable.prop_dir);
            PathAndVariable.prop.load(PathAndVariable.fr);
        } catch (Exception e) {
            PathAndVariable.error = "Error while reading userid of application";
            Browser.generate_error(PathAndVariable.error);
        }
        Browser.generate_logs("Info", "Getting user name " + PathAndVariable.prop.getProperty("username"), "");
        return PathAndVariable.prop.getProperty("username");
    }

    public void LoginToApplication(String username, String password, String login_btn, String url, String usr, String passwd) {
        try {
            PathAndVariable.driver.get(url);
            Browser.generate_logs("INFO","step-5", "");
            int i;
            for (i = 0; i < 5; i++) {
                try {
                    if (PathAndVariable.driver.findElement(By.xpath(username)).isDisplayed()) {
                        break;
                    }
                } catch (Exception e) {
                    Browser.force_wait(3);

                    PathAndVariable.driver.get(url);
                }
            }
            WebElement user = PathAndVariable.driver.findElement(By.xpath(username));
            WebElement pass = PathAndVariable.driver.findElement(By.xpath(password));
            WebElement login = PathAndVariable.driver.findElement(By.xpath(login_btn));
            user.sendKeys(usr);
            pass.sendKeys(passwd);
            login.click();
            Browser.take_screenshot("Login");
            Browser.generate_logs("Info", "Logged in to application", "");
        } catch (Exception e) {
            PathAndVariable.error = "Error while login to application";
            Browser.generate_error(PathAndVariable.error);
        }
    }
}