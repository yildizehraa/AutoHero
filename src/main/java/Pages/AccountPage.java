package Pages;

import Common.BaseTest;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.WebDriverRunner;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import static com.codeborne.selenide.Selenide.$$;

public class SearchPage extends BaseTest
{

    static String year="//span[text()='Erstzulassung ab']";
    static String selectYear="yearRange.min";
    static String selection="//option[text()='2015']";
    static String sort="sort";
    static String descendingPrice ="//option[text()='Höchster Preis']";
    static String priceList="//div[@class='price___1A8DG']//div[@class='totalPrice___3yfNv']";
    static  String yearList="//ul//li[@class='specItem___2gMHn'][1]";
    static String nextPage="//ul[@class='pagination']//li//span[@aria-label='Next']";
    static String lastIndex="//ul[@class='pagination']//li[last()-2]/a";
    static String adPopUp="//span[text()='Close']";



    static String loginVerify="//p[text()='KUNDEN-LOGIN']";
    static String registerTab="//div[@id='tab-1']";
    static String nameTextbox = "//input[@id='name']";
    static String lastNameTextbox = "//input[@id='lastName']";
    static String emailTextbox = "//input[@id='email']";
    static String registerButton = "//button[text()='Konto Erstellen']";
    String name = "Zehra";
    String lastName = "Ates";
    String emailAddress = "zehraayildiz@gmail.com";

    @Step("Check for the Login Page")
    public void isPageLoaded()
    {
        WebDriver driver =  WebDriverRunner.getWebDriver();
        if(driver.findElement(By.xpath(loginVerify)).isDisplayed()) {
            Assert.assertEquals("", "", "Lyken Account page is loaded successfully !");
            System.out.println("IF BASARILI");
        }
        else
            Assert.fail("Lyken Account page is not loaded.");

    }
    @Step("Check for the Login Page")
    public void registrationTab()
    {
        WebDriver driver =  WebDriverRunner.getWebDriver();
        driver.findElement(By.xpath(registerTab)).click();
    }

    @Step("Fill the name field")
    public void fillName()
    {
        WebDriver driver =  WebDriverRunner.getWebDriver();
        driver.findElement(By.xpath(nameTextbox)).sendKeys(name);
    }

    @Step("Fill the last name field")
    public void fillLastName()
    {
        WebDriver driver =  WebDriverRunner.getWebDriver();
        driver.findElement(By.xpath(nameTextbox)).sendKeys(lastName);
    }

    @Step("Fill the last e-mail field")
    public void fillEmailAddress()
    {
        WebDriver driver =  WebDriverRunner.getWebDriver();
        driver.findElement(By.xpath(nameTextbox)).sendKeys(emailAddress);
    }

    @Step("Click on to register button")
    public void sendRegistrationForm()
    {
        WebDriver driver =  WebDriverRunner.getWebDriver();
        driver.findElement(By.xpath(registerTab)).click();
    }
}
