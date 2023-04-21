package ru.stqa.training.selenium.driverbase;

import org.junit.runner.Description;
import org.openqa.selenium.*;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;


public abstract class DriverBase implements DriverDefault {

    // Для возможности распараллеливания тестов - инициализация хранилища драйверов tlDriver
    // + см. опцию maxParallelForks в файле конфигурации build.gradle в корне проекта
    protected static ThreadLocal<EventFiringWebDriver> tlDriver;// = new ThreadLocal<>();
    // Для логгирования процесса
    protected static EventFiringWebDriver driver; //WebDriver
    public static WebDriver getWebDriver() {
        return driver;
    }

    protected static LogListener          logListener;

    protected static WebDriverWait wait;
    public static WebDriverWait getWebDriverWait() {
        return wait;
    }

    private static String currentIpStr;
    // Remote WinServer2019 with Docker "192.168.0.91"
    // Remote Ubuntu 20.4   with Docker "192.168.203.128"
    protected static void setCurrentIpStr(String value) { currentIpStr = value; }
    public static String getCurrentIpStr() {
        return currentIpStr;
    }

    protected DriverBaseParams driverBaseParams;

    public DriverBase(DriverBaseParams driverBaseParams)
    {
        this.driverBaseParams = driverBaseParams;
    }

    private static WebDriverType webDriverType;

    public static WebDriverType getWebDriverType() {
        return webDriverType;
    }
    protected void setWebDriverType() {
        //SET default value for webDriverType
        webDriverType = WebDriverType.Chrome;
    }
    protected void setWebDriverType(WebDriverType newWebDriverType) {
        webDriverType = newWebDriverType;
    }
    private static TestRunType testRunType;

    protected static TestRunType getTestRunType() { return testRunType; }
    protected void setTestRunType() {
        //SET default value for testRunType
        testRunType = TestRunType.Local;
    }
    protected void setTestRunType(TestRunType newTestRunType) {
        testRunType = newTestRunType;
    }

    protected String defineCurrentIpStr(TestRunType tRunType) {
        if (tRunType == TestRunType.Local)
            return driverBaseParams.getLocalHostStr();
        else
        if (tRunType == TestRunType.Remote)
            return driverBaseParams.getLocalIpStr();
        else
            return driverBaseParams.getLocalIpStr();
    }


    private static String               currentTestName;

    protected static String getCurrentTestName() {
        return currentTestName;
    }
    protected void setCurrentTestName(String newCurrentTestName) {
        if (newCurrentTestName.indexOf('[') > 0)
            currentTestName = newCurrentTestName.substring(0, newCurrentTestName.indexOf('['));
        else
            currentTestName = newCurrentTestName;
    }

    protected abstract void initLogListenerAndWait(WebDriver webDrv);

    public abstract void watcherStarting (Description description);
    public abstract void watcherFinished (Description description);

    //BeforeClass
    public static void startBeforeClass() {
        tlDriver = new ThreadLocal<>();
    }

    //Before
    public abstract void startBefore() throws MalformedURLException;

    //After
    public abstract void stopAfter();

    //AfterClass
    public static void stopAfterClass() {
        driver.quit();
        tlDriver.remove();

        driver = null;
        tlDriver = null;
    }


    //------------------------------------------------------------------------------------------------

    public String getFullDateStrForBrowserDateControl(int yyyy, int mm, int dd)
    {
        return getFullDateStrForBrowserDateControl(yyyy, mm, dd, getWebDriverType());
    }

    public void clickElement(WebElement element)
    {
        clickElement(element, getWebDriverType());
    }

    public void findElmAndClick(WebDriver webDriver, By locator)
    {
        clickElement(findElm(webDriver, locator));
    }

    public void findElmAndClear(WebDriver webDriver, By locator)
    {
        findElm(webDriver, locator).clear();
    }

    public void findElmAndSendKeys(WebDriver webDriver, By locator, String keyText) {
        findElm(webDriver, locator).sendKeys(keyText);
    }

}
