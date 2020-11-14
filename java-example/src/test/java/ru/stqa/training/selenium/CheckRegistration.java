package ru.stqa.training.selenium;

import org.junit.*;
import org.openqa.selenium.By;
import ru.stqa.training.selenium.driverbase.DriverBase;
import ru.stqa.training.selenium.driverbase.DriverBaseParams;

import java.lang.Thread;

import java.util.Calendar;


public class CheckRegistration extends DriverBase {

    String FirstName, LastName, eMailName, testString, taxId;

    private static final long sleepTimeMsek = 5000;

    public CheckRegistration() {
        super (new DriverBaseParams());
    }

    @Test
    public void myCheckRegistration() {
        driver.get("http://" + getCurrentIpStr() + ":8080/litecart/en/"); //открыть главную страницу магазина

        // находим ссылку регистрации пользователя и щелкаем по ней
        findElm(driver, By.cssSelector("[href*=create_account]")).click();

        // читаем текущее время - добавляем его к фамилии и имеем уникальный e-mail и пароль каждый раз
        Calendar calendar = Calendar.getInstance();
        int yyyy=calendar.get(calendar.YEAR);
        int mm=calendar.get(calendar.MONTH);
        int dd=calendar.get(calendar.DAY_OF_MONTH);
        int h=calendar.get(calendar.HOUR_OF_DAY);
        int m=calendar.get(calendar.MINUTE);
        int s=calendar.get(calendar.SECOND);

        FirstName="Ivan";
        LastName="Tankist";
        eMailName=FirstName + PaddingLeft(h) + PaddingLeft(m) + PaddingLeft(s);

        taxId = PaddingLeft(yyyy, 4) + "-" + PaddingLeft(mm) + "-" + PaddingLeft(dd) + "_" +
                PaddingLeft(h) + PaddingLeft(m) + PaddingLeft(s);

        // заполняем форму - только обязательные поля
        findElmAndSendKeys(driver, By.name("tax_id"), taxId);
        findElmAndSendKeys(driver, By.name("company"), "MMM");
        findElmAndSendKeys(driver, By.name("firstname"), FirstName);
        findElmAndSendKeys(driver, By.name("lastname"), LastName);
        findElmAndSendKeys(driver, By.name("address1"), "3 Buiders st. 13");
        findElmAndSendKeys(driver, By.name("postcode"), "66666");
        findElmAndSendKeys(driver, By.name("city"), "Kyiv");
        findElmAndSendKeys(driver, By.name("email"), eMailName+"@mail.com");
        findElmAndSendKeys(driver, By.name("phone"), "223-322");
        findElmAndSendKeys(driver, By.name("password"), eMailName);
        findElmAndSendKeys(driver, By.name("confirmed_password"), eMailName);

        takeScreenshot(driver, "ScreenOne");

        try {
            Thread.sleep(sleepTimeMsek);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        // нажимаем на кнопку Create Account
        findElm(driver, By.name("create_account")).click();


        // Проверяем что мы залогинились - на странице должен быть соответствующий раздел
        // который имеет заголовок Account
        testString = findElm(driver, By.cssSelector("[id=box-account] .title")).getText();

        Assert.assertTrue(testString.compareTo("Account")==0);


        // Отлогиниваемся
        findElm(driver, By.cssSelector("[href*=logout]")).click();


        // Проверяем что мы отлогинились - на странице должен быть соответствующий раздел
        // который имеет заголовок Login
        testString = findElm(driver, By.cssSelector("[id=box-account-login] .title")).getText();

        Assert.assertTrue(testString.compareTo("Login")==0);


         // Логинимся под созданным пользователем
        findElmAndSendKeys(driver, By.name("email"), eMailName+"@mail.com");
        findElmAndSendKeys(driver, By.name("password"), eMailName);
        findElm(driver, By.name("login")).click();

        // Проверяем что мы залогинились - на странице должен быть соответствующий раздел
        // который имеет заголовок Account
        testString = findElm(driver, By.cssSelector("[id=box-account] .title")).getText();
        Assert.assertTrue(testString.compareTo("Account")==0);

        takeScreenshot(driver, "ScreenTwo");

        try {
            Thread.sleep(sleepTimeMsek);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        // Отлогиниваемся
        findElm(driver, By.cssSelector("[href*=logout]")).click();

        // Проверяем что мы отлогинились - на странице должен быть соответствующий раздел
        // который имеет заголовок Login
        testString = findElm(driver, By.cssSelector("[id=box-account-login] .title")).getText();
        Assert.assertTrue(testString.compareTo("Login")==0);

    }

}
