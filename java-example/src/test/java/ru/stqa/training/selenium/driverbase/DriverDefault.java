package ru.stqa.training.selenium.driverbase;

import javafx.util.converter.DateTimeStringConverter;
import org.apache.commons.io.FileUtils;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.InvalidSelectorException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.OutputType;

import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.logging.Logs;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;


public interface DriverDefault {

    enum WebDriverType { IE, Chrome, Firefox }
    enum TestRunType   { Local, RemoteWin, RemoteUbuntu }

    class LogListener extends AbstractWebDriverEventListener {

        private LogWriter lw;
        private EventFiringWebDriver driver;

        public LogListener(LogWriter logWriter)
        {
            lw = logWriter;
        }

        public void setDriver(EventFiringWebDriver driver) {
            this.driver = driver;
            this.driver.register(this);
        }

        @Override
        public void beforeFindBy(By by, WebElement element, WebDriver driver) {
            lw.LogWrite("beforeFindBy", by.toString()+ " processing");
        }

        @Override
        public void afterFindBy(By by, WebElement element, WebDriver driver) {
            lw.LogWrite("afterFindBy", by.toString() + " is found");
        }

        @Override
        public void onException(Throwable throwable, WebDriver driver) {
            lw.LogWrite("onException", "Error : " + throwable.toString());
        }
    }

    default void saveBrowserLog(TestRunType testRunType, WebDriverType webDriverType,
                                Logs wdLogs, Capabilities wdCapabilities, String currentTestName) {
            System.out.println("testRunType = " + testRunType.toString());
            System.out.println("driverType  = " + webDriverType.toString());

            // for write log file with Browser logging
            Path baseRelativePath = Paths.get("", "Log");
            Path baseFullPath = baseRelativePath.toAbsolutePath();

            createBaseLogDir(baseFullPath);

            LogWriter lw = new LogWriter(wdLogs, baseFullPath, currentTestName);
            lw.LogWrite("currentTestName", currentTestName);
            lw.LogWrite("Capabilities", wdCapabilities.toString());
            lw.LogWrite("testRunType", testRunType.toString());
            lw.LogWrite("driverType", webDriverType.toString());

            lw.saveCurLogs(webDriverType, LogType.BROWSER);

            lw.FinalLogWrite();
    }

    default void createBaseLogDir(Path baseFullPath) {
        if (!Files.exists(baseFullPath))
            try {
                Files.createDirectories(baseFullPath);
            } catch (Exception ex) {
                System.out.println("Ошибка при попытке создания базовой дирректории лога: " + ex);
            }
    }

    default Capabilities getDriverCapabilities(WebDriver webDriver)
    {
        return ((HasCapabilities) webDriver).getCapabilities();
    }

    default void printDriverCapabilities(WebDriver webDriver)
    {
        System.out.println( getDriverCapabilities(webDriver) );
    }


    default void checkAndPrintAttributeByName(WebElement element, String attributeName) {
        if (isAttributePresent(element, attributeName))
        {
            System.out.print("element('name')= " + element.getAttribute("name") + ", ");
            System.out.println("element.GetAttribute('" + attributeName + "')= " + element.getAttribute(attributeName));
        }
        else
        {
            System.out.println("Attribute '" + attributeName + "' is absent");
        }

    }

    default void takeScreenshot(WebDriver webDrv)
    {

        takeScreenshot(webDrv, "ScreenShot");
    }


    default void takeScreenshot(WebDriver webDrv, String fileNameWithoutExt)
    {
        Date date = new Date();
        DateTimeStringConverter dtsc = new DateTimeStringConverter(null, "yyyy-MM-dd_HHmmss");
        String fileNameUniq = fileNameWithoutExt + "_" + dtsc.toString(date);

        // for write ScreenShot file in screenshot Folder
        Path baseRelativePath = Paths.get("", "screenshot");
        Path baseFullPath = baseRelativePath.toAbsolutePath();

        createBaseLogDir(baseFullPath);

        takeScreenshot(webDrv, fileNameUniq, baseFullPath.toString());
    }

    default void takeScreenshot(WebDriver webDrv, String fileNameWithoutExt, String filePath)
    {
        try
        {
            File scrFile = ((TakesScreenshot)webDrv).getScreenshotAs(OutputType.FILE);
            // Now you can do whatever you need to do with it, for example copy somewhere
            FileUtils.copyFile(scrFile, new File(filePath, fileNameWithoutExt + ".png"));
        }
        catch(WebDriverException ex) {
            System.out.println("Failed to capture screenshot: " + ex.getMessage());
        }
        catch(IOException ex) {
            System.out.println("Failed to save screenshot file: " + ex.getMessage());
        }
    }


    default String getFullDateStrForBrowserDateControl(int yyyy, int mm, int dd, WebDriverType driverType)
    {
        if (driverType == WebDriverType.Chrome) {
            return PaddingLeft(dd) + "." +
                   PaddingLeft(mm) + "." +
                   PaddingLeft(yyyy, 4);
        }
        else
        {
            return PaddingLeft(yyyy, 4) + "-" +
                   PaddingLeft(mm) + "-" +
                   PaddingLeft(dd);
        }
    }

    default String PaddingLeft(int intS)
    {
        return PaddingLeft(intS, 2);
    }

    default String PaddingLeft(int intS, int totalWidth)
    {
        return PaddingLeft(intS, totalWidth, '0');
    }

    default String PaddingLeft(int intS, int totalWidth, char paddingChar)
    {
        return String.format("%" + paddingChar + totalWidth + "d", intS);
    }


    default WebElement findElm(WebDriver webDriver, By locator)
    {
        return webDriver.findElement(locator);
    }

    default void findElmAndSendKeys(WebDriver webDriver, By locator, String keyToSend)
    {
        findElm(webDriver, locator).sendKeys(keyToSend);
    }


    default void clickElement(WebElement element, WebDriverType driverType)
    {
        if (driverType == WebDriverType.IE) {
            element.sendKeys(Keys.RETURN);
        }
        else {
            element.click();
        }
    }

    default void findElmAndClick(WebDriver webDriver, By locator, WebDriverType driverType)
    {
        clickElement(findElm(webDriver, locator), driverType);
    }


    default boolean isAttributePresent(WebElement element, String attribute) {
        boolean result = false;
        try {
            String value = element.getAttribute(attribute);
            if (value != null){
                result = true;
            }
        } catch (Exception e) {}

        return result;
    }

    default boolean isElementPresent (WebDriver webDriver, By locator) {
        return isElementPresent ( webDriver, locator, false);
    }

    default boolean isElementPresent (WebDriver webDriver, By locator, boolean isWait) {
        return isElementPresent ( webDriver, locator, isWait, DriverConst.drvExplWaitTime);
    }

    default boolean isElementPresent (WebDriver webDriver, By locator, boolean isWait,
                                     int localExplWaitTime)
    {
        try
        {
            if (isWait) {

                WebDriverWait localWait = new WebDriverWait(webDriver,localExplWaitTime);

                // Задаем явное ожидание - вариант с применением лямбда выражеия с передачей ф-ции в качестве параметра
                WebElement element = localWait.until(d -> d.findElement(locator) );

                // АЛЬТЕРНАТИВНЫЙ Вариант - с использованием presenceOfElementLocated(locator)
                //WebElement element = localWait.until(presenceOfElementLocated(locator));
            }
            else
            {
                // работает неявное ожидание, если оно задано
                webDriver.findElement(locator);
            }

            return true;
        }
        // Если передан неправильный локатор
        catch (InvalidSelectorException ex)
        {
            System.out.println("isElementPresent(): InvalidSelectorException - " + ex.getMessage());
            throw ex;
        }
        //Если элемент отсутствует в DOM на момент вызова
        catch (NoSuchElementException ex)
        {
            System.out.println("isElementPresent(): NoSuchElementException - " + ex.getMessage());
            return false;
        }
        //Если элемент доступен в DOM на момент поиска, но спустя время, в момент его вызова, DOM может измениться
        catch (StaleElementReferenceException ex)
        {
            System.out.println("isElementPresent(): StaleElementReferenceException - " + ex.getMessage());
            return false;
        }
        //Если элемент был найдем в DOM, но не видим на странице
        catch (ElementNotVisibleException ex)
        {
            System.out.println("isElementPresent(): ElementNotVisibleException - " + ex.getMessage());
            return false;
        }
    }


    default boolean areElementsPresent(WebDriver webDriver, By locator)
    {
        return areElementsPresent ( webDriver, locator, DriverConst.drvImplWaitTime);
    }

    default boolean areElementsPresent(WebDriver webDriver, By locator, int localImplWaitTime)
    {
        try {
            // Для установки MAX общих неявных ожиданий drvImplWaitTime
            webDriver.manage().timeouts().implicitlyWait(DriverConst.drvMaxWaitTime, TimeUnit.SECONDS);

            return webDriver.findElements(locator).size() > 0;
        }
        finally
        {
            // Для возврата опции общих неявных ожиданий localImplWaitTime
            webDriver.manage().timeouts().implicitlyWait(localImplWaitTime, TimeUnit.SECONDS);
        }
    }


    default boolean areElementsNOTPresent (WebDriver webDriver, By locator) {
        return areElementsNOTPresent ( webDriver, locator, DriverConst.drvImplWaitTime);
    }

    default boolean areElementsNOTPresent (WebDriver webDriver, By locator, int localImplWaitTime) {
        try {
            // Для установки общих неявных ожиданий = 0
            webDriver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);

            return webDriver.findElements(locator).size() == 0;
        }
        finally
        {
            // Для возврата опции общих неявных ожиданий localImplWaitTime
            webDriver.manage().timeouts().implicitlyWait(localImplWaitTime, TimeUnit.SECONDS);
        }
    }

    default void ThreadSleep(long MSec) {
        try {
            Thread.sleep(MSec);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

}
