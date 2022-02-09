package Common;
import cucumber.api.java.en.Given;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import java.util.HashMap;
import java.util.Map;

/****************************************************
 * Tarih: 2022-02-06
 * Writer: Zehra Ates
 ****************************************************/

public class BaseTest
{

    static String driverPath="";

    public static  void setDriverPath()
    {
        driverPath=System.getProperty("user.dir")+"/drivers/chromedriver.exe";
        System.setProperty("webdriver.chrome.driver", driverPath);
        WebDriver driver = new ChromeDriver();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setCapability(CapabilityType.VERSION, Configuration.browserVersion);
        WebDriverRunner.setWebDriver(driver);
        chromeOptions.addArguments("disable-infobars");
        driver.manage().window().maximize();
        goToUrl();
    }

    @Given("^Open Lykon Login Page$")
    public void openLykon()
    {
        setDriverPath();
    }

    @Step("Go to Url Lykon Login Page !")
    public static void goToUrl() {
        WebDriver driver =  WebDriverRunner.getWebDriver();
        driver.get("https://account.lykon.de/login");
    }


}


