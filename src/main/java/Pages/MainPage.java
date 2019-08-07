package Pages;

import Common.BaseTest;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;


public class MainPage extends BaseTest {

    @Step("Open Auto Hero !")
    public void openAutoHero() {

        Configuration.baseUrl = "https://www.autohero.com/de/search/";
        WebDriverRunner.clearBrowserCache();

    }


}
