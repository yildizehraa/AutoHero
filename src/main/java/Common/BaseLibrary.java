package Common;
import com.codeborne.selenide.*;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.jasypt.util.text.BasicTextEncryptor;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static org.openqa.selenium.support.ui.ExpectedConditions.invisibilityOfElementLocated;

public class BaseLibrary  {


    protected static final Logger log = Logger.getLogger(BaseLibrary.class.getName());
    protected static String winHandleBefore = null;
    protected static String uploadPath = null;
    protected static String downloadPath = null;
    private static String browserName = null;
    private long waitForLoading = 150;
    private int doWaitLoading = 0;
    private boolean doNotWaitLoading = false;
    private static Connection connection;
    private static Statement statement;
    private static ResultSet rs;
    private String ssEnvironment = "";

    public static void killProcess() {

        Runtime rt = Runtime.getRuntime();
        try {
            rt.exec("taskkill /f /im " + "chrome.exe");
            rt.exec("taskkill /f /im " + "chromedriver.exe");
            rt.exec("taskkill /f /im " + "conhost.exe");
            rt.exec("taskkill /f /im " + "firefox.exe");
            rt.exec("taskkill /f /im " + "geckodriver.exe");
            rt.exec("taskkill /f /im " + "iexplore.exe");
            rt.exec("taskkill /f /im " + "iedriver.server");
            rt.exec("taskkill /f /im " + "iedriver.server64");
        } catch (IOException e) {
            System.out.println("Processler Kill Edilememdi!!!");
        }
    }


    public byte[] takeScreenshot() {
        byte[] bytes = new byte[]{};
        try {
            //System.out.println("Screenshot will be taken");
            bytes = ((TakesScreenshot) WebDriverRunner.getWebDriver()).getScreenshotAs(OutputType.BYTES);
            //System.out.println("Screenshot has been taken");
        } catch (WebDriverException e) {
            log.warning("Take screenshot error:" + e.getMessage());
        }
        return bytes;
    }

    public byte[] takeScreenshot(WebDriver driver) {
        byte[] bytes = new byte[]{};
        try {
            bytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        } catch (WebDriverException e) {
            log.warning("Error takeScreenshot:" + e.getMessage());
        }
        return bytes;
    }

    private void waitForJS() {
        try {
            new WebDriverWait(WebDriverRunner.getWebDriver(), Configuration.timeout / 1000, 50).
                    until((ExpectedCondition<Boolean>) driver -> {
                        String readyState = executeJavaScript("return document.readyState");
//                        System.out.println("Internal ready state:" + readyState);
//                        return readyState.equals("complete") || readyState.equals("interactive");
                        return !readyState.equals("loading");

                    });
//            System.out.println("Loading: Ok");
        } catch (Exception e) {
            System.out.println("Loading window error: " + e.getMessage());
        }
        /*try {
            Wait().until(ExpectedConditions.and(
                    (ExpectedCondition<Boolean>) driver -> {
                        try {
                            return (Boolean) executeJavaScript("return document.readyState").equals("complete");
                        } catch (Exception e) {
                            return true;
                        }
                    },
                    (ExpectedCondition<Boolean>) driver -> {
                        try {
                            return (Boolean) executeJavaScript("return jQuery.active == 0");
                        } catch (Exception e) {
                            return true;
                        }
                    }
            ));
        } catch (Exception e) {
            System.out.println("WaitForJS error: " + e.getMessage());
        }*/
    }

    private void waitForJSreadyState() {
        Wait().until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(@NotNull WebDriver driver) {
                return executeJavaScript("return document.readyState").equals("complete");
            }
        });
    }



    private long getWaitForLoading() {
        return waitForLoading;
    }

    public void setWaitForLoading(long seconds) {
        this.waitForLoading = waitForLoading;
    }

    public void waitForLoadingJS2(WebDriver driver, long timeoutSec) {
        new WebDriverWait(driver, timeoutSec, 10).until(driver1 -> {
            JavascriptExecutor js = (JavascriptExecutor) driver1;
            boolean isJsFinished = false;
            try {
                isJsFinished = (boolean) js.executeScript("return (document.readyState == \"complete\" || document.readyState == \"interactive\")");
            } catch (Exception e) {
                isJsFinished = true;
                //System.out.println("Load: isJsFinished error: " + e.getMessage());
            }

            boolean isAjaxFinished = false;
            try {
                isAjaxFinished = (boolean) js.executeScript("var result = true; try { result = (typeof jQuery != 'undefined') ? jQuery.active == 0 : true } catch (e) {}; return result;");
            } catch (Exception e) {
                isAjaxFinished = true;
            }

            boolean isLoaderHidden = false;
            try {
                isLoaderHidden = (boolean) js.executeScript("return document.querySelectorAll('div[style*='visibility: visible'] img[alt='loading']').length == 0");
            } catch (Exception e) {
                isLoaderHidden = true;
                System.out.println("Load: isLoaderHidden error: " + e.getMessage());
            }

            return isJsFinished && isLoaderHidden && isAjaxFinished;
        });
    }


    public void waitForLoadingJS(WebDriver driver, long timeoutSec) {
        AtomicInteger isJsFinished = new AtomicInteger();
        AtomicInteger isAjaxFinished = new AtomicInteger();
        final int jsCompleteCheck = 3;
        new WebDriverWait(driver, timeoutSec, 10).until(d -> {
            JavascriptExecutor js = (JavascriptExecutor) d;
            try {
                if ((boolean) js.executeScript("return (document.readyState == \"complete\" || document.readyState == \"interactive\")"))
                    isJsFinished.set(jsCompleteCheck);
                //Allure.addAttachment("Şu kadar süre loading beklendi: " + timeoutSec, "");
            } catch (Exception e) {
                isJsFinished.getAndIncrement();
                //System.out.println("Load: isJsFinished error: " + e.getMessage());
            }

            try {
                if ((boolean) js.executeScript("var result = true; try { result = (typeof jQuery != 'undefined') ? jQuery.active == 0 : true } catch (e) {}; return result;"))
                    isAjaxFinished.set(jsCompleteCheck);
            } catch (Exception e) {
                isAjaxFinished.getAndIncrement();
            }

            boolean loaderVisible = false;
            loaderVisible = (boolean) js.executeScript("return document.querySelectorAll(\"div[style*='visibility: visible'] img[alt='loading']\").length > 0");
            if (loaderVisible) {
                isJsFinished.set(0);
                isAjaxFinished.set(0);
            }

            return isJsFinished.get() >= jsCompleteCheck && isAjaxFinished.get() >= jsCompleteCheck && !loaderVisible;
        });
    }

    public void waitForLoadingJS(WebDriver driver) {
//        long timeout = Configuration.timeout / 1000;
        long timeout = getWaitForLoading();
        waitForLoadingJS(driver, timeout);
    }



    public String getNumberFromText(By by) {
        String x = WebDriverRunner.getWebDriver().findElement(by).getText();
        Pattern y = Pattern.compile("\\d+");
        Matcher m = y.matcher(x);
        m.find();
        String number = m.group();
//        System.out.println(number);

        return number;
    }

    public String getNumberFromText(String text) {
        Pattern y = Pattern.compile("\\d+");
        Matcher m = y.matcher(text);
        m.find();
        return m.group();
    }

    // Store the current window handle
    public String windowHandleBefore() {
        winHandleBefore = WebDriverRunner.getWebDriver().getWindowHandle();
        return winHandleBefore;
    }

    // Perform the click operation that opens new window
    // Switch to new window opened
    public void switchToNewWindow() throws InterruptedException {
        Thread.sleep(6000);
        for (String winHandle : WebDriverRunner.getWebDriver().getWindowHandles()) {
            WebDriverRunner.getWebDriver().switchTo().window(winHandle);
        }
    }


    public void closeNewWindow() {
        WebDriverRunner.getWebDriver().close();
    }


    public int getRandomNumber(int startIndex, int endIndex) {
        return (new Random().nextInt((endIndex - startIndex) + 1) + startIndex);
    }

    public String myip() {
        WebDriver driver = WebDriverRunner.getWebDriver();
        driver.get("http://www.whatismyip.com/");
        String myIP = driver.findElement(By.cssSelector("ul[class='list-group text-center'] h3")).getText();
        String[] ipString = myIP.split(":");
        myIP = ipString[1].trim();
        //System.out.println(myIP);
        return myIP;
    }


    @Step("\"{0}\" : \"{1}\"")
    public static void addReportValue(String reportDescription, String reportValue) {

    }

    @Step("\"{0}\" : \"{1}\"")
    public static void addReportValue2(String reportDescription, String reportValue) {
        Assert.assertEquals("Başarılı", reportValue);
    }

    @Step("ActualResult = \"{0}\", ExpectedResult = \"{1}\"")
    public static void checkSOAPResult(ArrayList<String> actualResult, String expectedResult) {
        boolean flag = false;

        for (String text : actualResult) {
            System.out.println(text);
            if (text.equals(expectedResult)) {
                flag = true;
                break;
            } else flag = false;
        }
        Assert.assertEquals(flag, true, "Actual result ile expected result aynı olmalı...");
    }

    public static String decrypt(String text) {
        BasicTextEncryptor encryptor = new BasicTextEncryptor();
        encryptor.setPassword("cryptorPassword");
        String textDecrypt = encryptor.decrypt(text);
        return textDecrypt;
    }

    public static String encrypt(String text) {
        BasicTextEncryptor encryptor = new BasicTextEncryptor();
        encryptor.setPassword("cryptorPassword");
        String textEncrypt = encryptor.encrypt(text);
        return textEncrypt;
    }



}
