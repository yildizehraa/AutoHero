import Common.BaseTest;
import Pages.MainPage;
import Pages.SearchPage;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.Test;

/****************************************************
 * Tarih: 2019-08-05
 * Writer: Zehra YILDIZ
 ****************************************************/

public class Scenario extends BaseTest
{
    SearchPage searchPage = new SearchPage();

    @Severity(SeverityLevel.CRITICAL)
    @Test(enabled = true, description = "Test AUTOHERO Search Functionality - Price Sorted")
    public void TS001_Search_for_AutoHero() throws InterruptedException
    {
        openAutoHero();
        searchPage
                .filterDate();
        searchPage
                .sortDescending();
        searchPage
                .priceVerification();
    }

    @Severity(SeverityLevel.CRITICAL)
    @Test(enabled = true, description = "Test AUTOHERO Search Functionality - Date Verification")
    public void TS002_Search_for_AutoHero() throws InterruptedException
    {
        openAutoHero();
        searchPage
                .filterDate();
        searchPage
                .sortDescending();
        searchPage
                .dateVerification();
    }
}
