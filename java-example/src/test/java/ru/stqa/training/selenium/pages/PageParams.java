package ru.stqa.training.selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.stqa.training.selenium.driverbase.DriverBase;

public class PageParams {

    private DriverBase drvBase;
    public DriverBase getDriverBase() { return drvBase;}

    public PageParams(DriverBase drvBase) {
        this.drvBase      = drvBase;
    }

    public static WebDriver getWebDriver() { return DriverBase.getWebDriver(); }

    public static WebDriverWait getWebDriverWait() {
        return DriverBase.getWebDriverWait();
    }

    public static String getCurrentIpStr() { return DriverBase.getCurrentIpStr(); }

    public void ThreadSleep(long MSec) {
        getDriverBase().ThreadSleep(MSec);
    }
}
