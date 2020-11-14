package ru.stqa.training.selenium;

import org.junit.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.stqa.training.selenium.driverbase.DriverBase;
import ru.stqa.training.selenium.driverbase.DriverBaseParams;

import java.lang.Thread;
import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class LeftMenuClick extends DriverBase {

    int menuQuantity, submenuQuantity;

    private static final long sleepTimeMenuMsek    = 300;
    private static final long sleepTimeSubmenuMsek = 200;
    private static final long sleepTimeMsek        = 2000;

    public LeftMenuClick() { super (new DriverBaseParams()); }

    @Test
    public void myLeftMenuClick() {
        driver.get("http://" + getCurrentIpStr() + ":8080/litecart/admin/"); //открыть страницу

        if (isElementPresent(driver, By.name("username")))
            driver.findElement(By.name("username")).sendKeys("admin"); //найти поле для ввода логина и ввести "admin"
        if (isElementPresent(driver, By.name("password")))
            driver.findElement(By.name("password")).sendKeys("admin"); //найти поле для ввода пароля и ввести "admin"
        if (isElementPresent(driver, By.name("password")))
            driver.findElement(By.name("login")).click();                          //найти кнопку логина и нажать на нее

        wait.until(titleIs("My Store"));
        //подождать пока не загрузится страница с заголовком "My Store"

        List<WebElement> menuPoints = driver.findElements(By.id("app-"));
        List<WebElement> submenuPoints;
        WebElement menuPoint, submenuPoint;
        // определение списка пунктов меню
        menuQuantity = menuPoints.size(); // сохраняем количество пунктов меню

        for (int i = 0; i < menuQuantity; i++  ) {  //проходим по пунктам меню
            menuPoints = driver.findElements(By.id("app-"));
            menuPoint  = menuPoints.get(i); //выбираем пункт меню
            wait = new WebDriverWait(driver,10);
            menuPoint.click();  // кликаем по меню

            menuPoints = driver.findElements(By.id("app-")); //обновляем список
            menuPoint= menuPoints.get(i); //выбираем пункт меню
            // определение списка пунктов подменю
            submenuPoints = menuPoint.findElements(By.cssSelector("[id^=doc-]"));
            submenuQuantity=submenuPoints.size(); // сохраняем количество пунктов подменю

            try {
                Thread.sleep(sleepTimeMenuMsek);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

            if(submenuQuantity > 0) { //подменю есть
                for (int j=0;j<submenuQuantity;j++) {
                    menuPoints = driver.findElements(By.id("app-"));  //обновляем список
                    menuPoint= menuPoints.get(i); //выбираем пункт меню
                    // определение списка пунктов подменю
                    submenuPoints = menuPoint.findElements(By.cssSelector("[id^=doc-]"));
                    submenuPoint = submenuPoints.get(j); //выбираем пункт подменю
                    clickElement(submenuPoint, getWebDriverType()); //submenuPoint.click(); //кликаем по подменю
                    driver.findElement(By.cssSelector("h1"));  //проверка наличия заголовка
                    try {
                        Thread.sleep(sleepTimeSubmenuMsek);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            } else {   // подменю нет
                driver.findElement(By.cssSelector("h1"));  //проверка наличия заголовка
            }
        }

        try {
            Thread.sleep(sleepTimeMsek);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

}
