package ru.stqa.training.selenium.driverbase;

import org.junit.runner.Description;
import org.openqa.selenium.*;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


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
    // Remote WinServer2019 with Docker
    // Remote Ubuntu 20.4   with Docker
    protected static void setCurrentIpStr(String value) { currentIpStr = value; }
    public static String getCurrentIpStr() {
        return currentIpStr;
    }

    protected static Process selenoidProcess;

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
        webDriverType = driverBaseParams.defWebDriverType;
    }

    private static TestRunType testRunType;

    protected static TestRunType getTestRunType() { return testRunType; }
    protected void setTestRunType() {
        //SET default value for testRunType
        testRunType = driverBaseParams.defTestRunType;
    }
    protected void setTestRunType(TestRunType newTestRunType) {
        testRunType = newTestRunType;
    }

    protected String defineCurrentIpStr(TestRunType tRunType) {
        if (tRunType == TestRunType.Local)
            return driverBaseParams.getLocalHostStr();
        else
        if ((tRunType == TestRunType.RemoteWin) || ( tRunType == TestRunType.RemoteUbuntu))
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

    protected abstract void initLogListener(WebDriver webDrv);

    protected abstract void initWebDriverWait(WebDriver webDrv);

    public abstract void watcherStarting (Description description);
    public abstract void watcherFinished (Description description);

    //BeforeClass
    public static void start1BeforeClass() {
        tlDriver = new ThreadLocal<>();
    }

    //Before
    public abstract void startBefore() throws MalformedURLException;

    //After
    public abstract void stopAfter();

    //AfterClass
    public static void stop1AfterClass() {
        driver.quit();
        tlDriver.remove();

        driver = null;
        tlDriver = null;
    }

    public static void stop2AfterClass() {
        // Terminate the Selenium webDriver Windows process
        TerminateLocalSeleniumBrowsersServer(getTestRunType(), getWebDriverType());
        // Terminate the selenoidProcess and the associated selenoid.exe Windows process
       TerminateLocalSelenoidServerForIE(getTestRunType(), getWebDriverType());
    }

    private static void TerminateLocalSeleniumBrowsersServer(TestRunType testRunType, WebDriverType driverType)
    {
        List<String> processesToKill = new ArrayList<>();

        switch (driverType) {
            case IE:
                processesToKill.add(DriverConst.ieDriverExeName);
                break;

            case Chrome:
                if (testRunType == TestRunType.Local) {
                    processesToKill.add(DriverConst.chromeDriverExeName);
                }
                break;

            case Firefox:
                if (testRunType == TestRunType.Local) {
                    processesToKill.add(DriverConst.firefoxDriverExeName);
                }
                break;
        }
        // Terminate the associated Selenium webDriver Windows process from memory
        try {
            killProcesses(processesToKill);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void TerminateLocalSelenoidServerForIE(TestRunType testRunType, WebDriverType driverType)
    {
        if ((testRunType != TestRunType.Local) &
                (driverType == WebDriverType.IE) &
                (selenoidProcess != null)
        )
        {
            // Stop the selenoid process
            if (selenoidProcess.isAlive()) {
                selenoidProcess.destroy();
            }
            // Terminate the selenoid associated Windows process from memory
            List<String> processesToKill = new ArrayList<>();
            processesToKill.add(DriverConst.selenoidDriverExeName);
            try {
                killProcesses(processesToKill);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void killProcesses(List<String> processNames) throws IOException, InterruptedException {
        for (String processName : processNames) {
            ProcessBuilder builder = new ProcessBuilder("taskkill", "/F", "/IM", processName);
            builder.start().waitFor();
        }
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
