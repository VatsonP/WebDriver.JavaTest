package ru.stqa.training.selenium.pages;

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
}
