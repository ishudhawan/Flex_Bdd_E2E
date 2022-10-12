package utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.util.HashMap;

public class Driver {

    public static void open_Browser() {
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
            if (System.getProperty("runMode").equals ("jenkins")){
                PathAndVariable.driver = new RemoteWebDriver(new URL("http://outside:notS2-sekwrL@vqe-grid-selenium.stage-us1.twilio.com:4444/wd/hub"), options);
            }
            else{
                PathAndVariable.driver = new ChromeDriver(options);
            }
            PathAndVariable.driver.manage().window().maximize();
            Browser.generate_logs("Info", "ChromeDriver instance created " + PathAndVariable.driver, "");
            Browser.take_screenshot("Browser");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
