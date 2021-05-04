package ru.stqa.training.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.stqa.training.selenium.driverbase.DriverBase;

public class PageParams {

    private DriverBase drvBase;
    private DriverBase getDriverBase() { return drvBase;}

    public static WebDriver getWebDriver() { return DriverBase.getWebDriver(); }

    public static WebDriverWait getWebDriverWait() {
        return DriverBase.getWebDriverWait();
    }

    public static String getCurrentIpStr() { return DriverBase.getCurrentIpStr(); }


    public PageParams(DriverBase drvBase) {
        this.drvBase      = drvBase;
    }


    public void ThreadSleep(long MSec) {
        getDriverBase().ThreadSleep(MSec);
    }

    public void takeScreenshot(String fileNameWithoutExt) {
        getDriverBase().takeScreenshot(getWebDriver(), fileNameWithoutExt);
    }

    public void clickElement(WebElement element) {
        getDriverBase().clickElement(element);
    }

    public void findElmAndClick(By locator) {
        getDriverBase().findElmAndClick(getWebDriver(), locator);
    }

    public void findElmAndClear(By locator) {
        getDriverBase().findElmAndClear(getWebDriver(), locator);
    }

    public void findElmAndSendKeys(By locator, String keyText) {
        getDriverBase().findElmAndSendKeys(getWebDriver(), locator, keyText);
    }

    public boolean isElementPresent (By locator){
         return  getDriverBase().isElementPresent(getWebDriver(), locator);
    }

    public void checkAndPrintAttributeByName(WebElement element, String attributeName) {
        getDriverBase().checkAndPrintAttributeByName (element, attributeName);
    }

    public String getFullDateStrForBrowserDateControl(int yyyy, int mm, int dd) {
        return getDriverBase().getFullDateStrForBrowserDateControl(yyyy, mm, dd);
    }
}
