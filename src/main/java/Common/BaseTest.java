package Common;
import Pages.MainPage;
import com.codeborne.selenide.*;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.testng.*;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import java.util.HashMap;
import java.util.Map;
import static com.codeborne.selenide.Selenide.$$;

/****************************************************
 * Tarih: 2019-08-05
 * Writer: Zehra YILDIZ
 ****************************************************/

public class BaseTest extends  BaseLibrary{

    static final int timeout = 200;
    static final int loadingTimeout = 200;
    static String driverPath="";
    protected static Map<String, String> parentFeatureMap = new HashMap();

    public static  void setDriverPath()
    {
        driverPath=System.getProperty("user.dir")+"/drivers/chromedriver_75.exe";
        System.setProperty("webdriver.chrome.driver", driverPath);
        WebDriver driver = new ChromeDriver();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setCapability(CapabilityType.VERSION, Configuration.browserVersion);
        WebDriverRunner.setWebDriver(driver);
        chromeOptions.addArguments("disable-infobars");
        driver.manage().window().maximize();
        goToUrl();
    }


    @Step("Open Auto Hero !")
    public void openAutoHero() {
        setDriverPath();
    }

    @Step("Go to Url Auto Hero !")
    public static void goToUrl() {
        WebDriver driver =  WebDriverRunner.getWebDriver();
        driver.get("https://www.autohero.com/de/search/");
    }

    @Step("{name} : {description}")
    public void step(String name, String description) {
    }

}


