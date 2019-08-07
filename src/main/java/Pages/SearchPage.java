package Pages;

import Common.BaseTest;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import static com.codeborne.selenide.Selenide.$$;

public class SearchPage extends BaseTest {
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



    @Step("Filter for 2015")
    public void filterDate() {
        WebDriver driver =  WebDriverRunner.getWebDriver();
        driver.findElement(By.xpath(year)).click();
        driver.findElement(By.name(selectYear)).click();
        driver.findElement(By.xpath(selection)).click();


    }

    @Step("Sort cars by Price Descending")
    public void sortDescending() {
        WebDriver driver =  WebDriverRunner.getWebDriver();
        driver.findElement(By.name(sort)).click();
        driver.findElement(By.xpath(descendingPrice)).click();

    }

    @Step("Verify of cars are sorted by price descending")
    public static void priceVerification() throws InterruptedException {

        ElementsCollection elementList;
        WebDriver driver =  WebDriverRunner.getWebDriver();
        int price1;
        int temp = 0;
        boolean flag=false;

        String li= driver.findElement(By.xpath(lastIndex)).getText();
        int index= Integer.parseInt(li);
        Thread.sleep(3000);

        for(int i=1;i<index;i++)
        {
            elementList = $$(By.xpath(priceList));
            for (int j = 0; j < elementList.size(); j++) {
                price1 = Integer.parseInt(elementList.get(j).text().split(" ")[0].replace(".", ""));
                System.out.println(price1);
                Thread.sleep(3000);
                if (j != 0) {
                    if (price1 > temp)
                        flag=true;
                }
                temp = price1;

            }
            driver.findElement(By.xpath(nextPage)).click();
            Thread.sleep(3000);
        }

        if(flag==false)
            Assert.assertEquals("", "", "Sorting is successfull !");
        else
            Assert.fail("Listed year is less than 2015. Wrong sorted !");

    }

    @Step("Verify of  cars are sorted by year")
    public static void dateVerification() throws InterruptedException {
        ElementsCollection elementList;
        WebDriver driver =  WebDriverRunner.getWebDriver();
        int tempYear;
        boolean flag=false;
        Thread.sleep(5000);

        String li= driver.findElement(By.xpath(lastIndex)).getText();
        int index= Integer.parseInt(li);
        for(int i=1;i<=index;i++)
        {
            elementList = $$(By.xpath(yearList));
            for (int j = 1; j < elementList.size(); j++) {
                System.out.println(elementList.get(j).text());
                tempYear = Integer.parseInt(elementList.get(j).text().split("/")[1]);
                System.out.println(tempYear);
                if (tempYear < 2015)
                    flag = true;
            }
            if(i!=index)
                driver.findElement(By.xpath(nextPage)).click();
            Thread.sleep(3000);
            advertisementPopUp();
        }

        if(flag==false)
            Assert.assertEquals("", "", "Sorting is successfull !");
        else
            Assert.fail("Listed year is less than 2015. Wrong sorted !");

    }

    @Step("Advertisement Pop-Up")
    public static void advertisementPopUp() {
        WebDriver driver =  WebDriverRunner.getWebDriver();
        int size= driver.findElements(By.xpath(adPopUp)).size();
        if(size!=0)
        {
            if (!(driver.findElement(By.xpath(adPopUp)).isDisplayed()))
            {
                driver.findElement(By.xpath(adPopUp)).click();
            }
        }
    }

}
