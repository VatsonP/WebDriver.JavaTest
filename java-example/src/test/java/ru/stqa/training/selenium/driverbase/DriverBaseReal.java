package ru.stqa.training.selenium.driverbase;

import javafx.util.converter.DateTimeStringConverter;

import org.junit.runner.Description;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.*;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.TimeUnit;


public class DriverBaseReal extends DriverBase {

    public DriverBaseReal(DriverBaseParams driverBaseParams)
    {
        super (driverBaseParams);
    }

    protected void initLogListenerAndWait() {
        // for write log file on error
        Path baseRelativePath = Paths.get("","Log");
        Path   baseFullPath     = baseRelativePath.toAbsolutePath();

        createBaseLogDir(baseFullPath);

        LogWriter logWriter   = new LogWriter(baseFullPath, getCurrentTestName());
        logWriter.LogWrite("currentTestName", getCurrentTestName());
        logListener = new LogListener(logWriter);

        // Для установки общих неявных ожиданий
        driver.manage().timeouts().implicitlyWait(driverBaseParams.getImplWaitTime(), TimeUnit.SECONDS);
        // Для задания явных ожиданий
        wait = new WebDriverWait(driver, driverBaseParams.getExplWaitTime());
    }

    public void watcherStarting (Description description)
    {
        //Заполняем переменную с именем текущего выполняющегося теста
        setCurrentTestName(description.getMethodName());
        System.out.println("Starting test: " + getCurrentTestName());
    }

    public void watcherFinished (Description description) {
        System.out.println("Finish test: " + getCurrentTestName());
    }

    //Before
    public void startBefore() throws MalformedURLException {

        TestRunType   tRunType =  TestRunType.Local;
        WebDriverType wbType   = WebDriverType.Chrome;

        setTestRunType(tRunType);
        setWebDriverType(wbType);

        setCurrentIpStr( defineCurrentIpStr(getTestRunType()) );

        if(tlDriver.get() != null) {
            driver = tlDriver.get();

            if(logListener != null) {
                driver.unregister(logListener);
            }

            initLogListenerAndWait();
            //Регистрируем наблюдатель
            driver.register(logListener);

            driver.manage().deleteAllCookies();
            return;
        }

        // Создаем обертку класса WebDriver для последующего сохранения  логов
        if (getTestRunType() == TestRunType.Local) {
            driver = new EventFiringWebDriver(newDriverSetOptions(getWebDriverType()));
        }
        else
        if (getTestRunType() == TestRunType.Remote) {
            //URL
            String uriString = "http://" + driverBaseParams.getRemoteIpStr() + ":4444/wd/hub/";
            // Создаем обертку класса WebDriver для последующего сохранения  логов
            driver = new EventFiringWebDriver( newRemoteWebDriverSetOptions(new URL(uriString), getWebDriverType()) );
        }

        tlDriver.set(driver);

        initLogListenerAndWait();
        //Регистрируем наблюдатель
        driver.register(logListener);

        // Функционал из @After stop() ... был закоментирован и заменен на этот - addShutdownHook(...),
        // для выполнения только один раз в конце всех тестов
        //Runtime.getRuntime().addShutdownHook( new Thread(() -> {driver.quit(); driver = null;} ));
    }

    //After
    public void stopAfter() {
        saveBrowserLog(getTestRunType(), getWebDriverType(), driver, getCurrentTestName());
    }


    //------------------------------------------------------------------------------------------------

    protected WebDriver newDriverSetOptions(WebDriverType driverType)
    {
        WebDriver    webDriver;

        setWebDriverType(driverType);

        if (driverType == WebDriverType.IE) {
            webDriver = new InternetExplorerDriver(getIEOptions());
        }
        else
            if (driverType == WebDriverType.Chrome) {
                webDriver = new ChromeDriver(
                                new ChromeDriverService.Builder()
                                    .usingDriverExecutable(new File ("C:\\Tools\\chromedriver.exe")).build(),
                                getChromeOptions());
                //webDriver.get("chrome://settings/clearBrowserData");
                //webDriver.findElement(By.xpath("//settings-ui")).sendKeys(Keys.RETURN);
            }
            else
                if (driverType == WebDriverType.Firefox) {
                    webDriver = new FirefoxDriver(getFirefoxOptions());
                }
                else {
                    webDriver = new InternetExplorerDriver(getIEOptions());
                    setWebDriverType(WebDriverType.IE);
                }

        webDriver.manage().deleteAllCookies();
        printDriverCapabilities(webDriver);
        return webDriver;
    }

    private InternetExplorerOptions getIEOptions()
    {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("unexpectedAlertBehavior", "dismiss");
        //установка опций для игнорировния отличия масштаба от 100%
        caps.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
        //установка опций для игнорировния отличия настройки защищенного режима в разных зонах (не надежная работа)
        //caps.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
        InternetExplorerOptions ieOptions = new InternetExplorerOptions(caps);
        ieOptions.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.DISMISS);

        return ieOptions;
    }

    private ChromeOptions getChromeOptions()
    {
        ChromeOptions chromeOptions = new ChromeOptions();
        //--Указан путь к последней версии Chrome в portable варианте
        chromeOptions.setBinary("C:\\Program Files (x86)\\Google\\Chrome\\Application\\Chrome.exe");
        //--Задаем setCapability
        chromeOptions.setCapability("unexpectedAlertBehavior", "dismiss");
        //--Задаем setCapability
        chromeOptions.setCapability("acceptInsecureCerts", false);
        chromeOptions.addArguments("--lang=ru");
        //--Задаем опции коммандной строки соотв. браузера
        //options.addArguments("start-fullscreen");
        //Use custom profile(also called user data directory)
        chromeOptions.addArguments("user-data-dir=c:\\Users\\AdminVadim\\AppData\\Local\\Google\\Chrome\\User Data");

        return chromeOptions;
    }

    private FirefoxOptions getFirefoxOptions()
    {
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.setBinary("C:\\Program Files\\Mozilla Firefox\\firefox.exe");
        //--Задаем setCapability
        firefoxOptions.setCapability("acceptInsecureCerts", false);
        //--Задаем опции коммандной строки соотв. браузера
        firefoxOptions.addArguments("-private-window");
        //установка профиля пользователя для запуска браузера
        //File ffProfileDir = new File("C:\\Users\\AdminVadim\\AppData\\Local\\Mozilla\\Firefox\\Profiles\\ltebh6bi.default");
        //FirefoxProfile ffProfile = new FirefoxProfile(ffProfileDir);
        //firefoxOptions.setProfile(ffProfile);

        return firefoxOptions;
    }

    protected WebDriver newRemoteWebDriverSetOptions(URL remoteAddress, WebDriverType driverType)
    {
        WebDriver    webDriver;

        setWebDriverType(driverType);

        if (driverType == WebDriverType.IE) {
            webDriver = new RemoteWebDriver(remoteAddress, getRemoteIEOptions());
        }
        else
        if (driverType == WebDriverType.Chrome) {
            webDriver = new RemoteWebDriver(remoteAddress, getRemoteChromeOptions());
            //webDriver.get("chrome://settings/clearBrowserData");
            //webDriver.findElement(By.xpath("//settings-ui")).sendKeys(Keys.RETURN);
        }
        else
        if (driverType == WebDriverType.Firefox) {
            webDriver = new RemoteWebDriver(remoteAddress, getRemoteFirefoxOptions());
        }
        else {
            webDriver = new RemoteWebDriver(remoteAddress, getRemoteIEOptions());
            setWebDriverType(WebDriverType.IE);
        }

        webDriver.manage().deleteAllCookies();
        printDriverCapabilities(webDriver);
        return webDriver;
    }

    private InternetExplorerOptions getRemoteIEOptions()
    {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("unexpectedAlertBehavior", "dimiss");
        //установка опций для игнорировния отличия масштаба от 100%
        caps.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
        //установка опций для игнорировния отличия настройки защищенного режима в разных зонах (не надежная работа)
        //caps.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
        InternetExplorerOptions ieOptions = new InternetExplorerOptions(caps);
        ieOptions.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.DISMISS);

        return ieOptions;
    }

    private ChromeOptions getRemoteChromeOptions()
    {
        ChromeOptions chromeOptions = new ChromeOptions();

        chromeOptions.setCapability("browserName", "chrome");
        chromeOptions.setCapability("browserVersion", "85.0");

        Date date = new Date();
        DateTimeStringConverter dtConverter = new DateTimeStringConverter(null, "yyyyMMdd.HHmmss");
        String runName = getClass().getName();
        String fileName = runName + "." + dtConverter.toString(date) + ".mp4";

        Map<String, Object> map = new HashMap<>();
        map.put("name", runName);
        map.put("videoName", fileName);
        map.put("enableVNC", true);
        map.put("enableVideo", true);
        map.put("videoScreenSize", "1280x720");
        map.put("screenResolution", "1920x1080x24");
        chromeOptions.setCapability("selenoid:options", map);

        //--Указан путь к последней версии Chrome в portable варианте
        //chromeOptions.setBinary("C:\\Program Files (x86)\\Google\\Chrome\\Application\\Chrome.exe");
        //--Задаем setCapability
        chromeOptions.setCapability("unexpectedAlertBehavior", "dimiss");
        //--Задаем setCapability
        chromeOptions.setCapability("acceptInsecureCerts", false);
        chromeOptions.addArguments("--lang=ru");
        //--Задаем опции коммандной строки соотв. браузера
        //options.addArguments("start-fullscreen");
        //Use custom profile(also called user data directory)
        //chromeOptions.addArguments("user-data-dir=c:\\Users\\AdminVadim\\AppData\\Local\\Google\\Chrome\\User Data");

        return chromeOptions;
    }

    private FirefoxOptions getRemoteFirefoxOptions()
    {
        FirefoxOptions firefoxOptions = new FirefoxOptions();

        firefoxOptions.setCapability("browserName", "chrome");
        firefoxOptions.setCapability("browserVersion", "85.0");

        Date date = new Date();
        DateTimeStringConverter dtsc = new DateTimeStringConverter(null, "yyyyMMdd.HHmm");
        String runName = getClass().getName();
        String fileName = runName + "." + dtsc.toString(date) + ".mp4";

        Map<String, Object> map = new HashMap<>();
        map.put("name", runName);
        map.put("videoName", fileName);
        map.put("enableVNC", true);
        map.put("enableVideo", true);
        map.put("videoScreenSize", "1280x720");
        map.put("screenResolution", "1920x1080x24");
        firefoxOptions.setCapability("selenoid:options", map);

        //firefoxOptions.setBinary("C:\\Program Files\\Mozilla Firefox\\firefox.exe");
        //--Задаем setCapability
        firefoxOptions.setCapability("acceptInsecureCerts", false);
        //--Задаем опции коммандной строки соотв. браузера
        //firefoxOptions.addArguments("-private-window");
        //установка профиля пользователя для запуска браузера
        //File ffProfileDir = new File("C:\\Users\\AdminVadim\\AppData\\Local\\Mozilla\\Firefox\\Profiles\\ltebh6bi.default");
        //FirefoxProfile ffProfile = new FirefoxProfile(ffProfileDir);
        //firefoxOptions.setProfile(ffProfile);

        return firefoxOptions;
    }

}
