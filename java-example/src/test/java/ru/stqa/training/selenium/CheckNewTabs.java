package ru.stqa.training.selenium;


import org.junit.*;
import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.lang.Thread;

import org.openqa.selenium.support.ui.ExpectedCondition;
import ru.stqa.training.selenium.driverbase.DriverBase;
import ru.stqa.training.selenium.driverbase.DriverBaseParams;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleContains;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;


public class CheckNewTabs extends DriverBase {

    private int countryQuantity, linksQuantity; // количество стран в списке
    private int randomIndex;
    private WebElement countryRow;  // строка по стране
    private List<WebElement> countryRows, listLinks;  // список стран, список внешних ссылок
    private String originalWindow, newWindow;
    private Set<String> existingWindows;

    private static final long sleepTimeMsek        = 2000;

    public CheckNewTabs() { super (new DriverBaseParams()); }


    public ExpectedCondition<String> anyWindowOtherThan(Set<String> oldWindows) {
        return new ExpectedCondition<String>() {
            public String apply(WebDriver driver) {
                Set<String> handles=driver.getWindowHandles();
                handles.removeAll(oldWindows);
                return handles.size()>0 ? handles.iterator().next():null;
            }
        };
    }

    @Test
    public void myCheckNewTabs() {

        driver.get("http://" + getCurrentIpStr() + ":8080/litecart/admin/"); //открыть страницу

        if (isElementPresent(driver, By.name("username")))
            driver.findElement(By.name("username")).sendKeys("admin"); //найти поле для ввода логина и ввести "admin"
        if (isElementPresent(driver, By.name("password")))
            driver.findElement(By.name("password")).sendKeys("admin"); //найти поле для ввода пароля и ввести "admin"
        if (isElementPresent(driver, By.name("password")))
            driver.findElement(By.name("login")).click();                          //найти кнопку логина и нажать на нее

        wait.until(titleIs("My Store"));
        //подождать пока не загрузится страница с заголовком "My Store"

        //открыть страницу со списком стран
        driver.get("http://" + getCurrentIpStr() + ":8080/litecart/admin/?app=countries&doc=countries");
        wait.until(titleContains("Countries")); // ждем загрузки страницы

        //определение списка строк в таблице стран
        countryRows = driver.findElements(By.cssSelector("[name=countries_form] .row"));

        // сохраняем количество строк, т.е. стран в списке
        countryQuantity=countryRows.size();

        final Random random = new Random();

        // выбираем номер случайно страны из списка
        randomIndex = random.nextInt(countryQuantity-1);

        countryRow=countryRows.get(randomIndex);  // выбираем случайную страну
        countryRow.findElement(By.cssSelector("a")).click();
        // открываем страницу выбранной страны
        wait.until(titleContains("Edit Country"));  // ждем загрузки страницы

        listLinks = driver.findElements(By.cssSelector("form .fa-external-link"));
        // получаем список внешних ссылок

        linksQuantity = listLinks.size();  // определяем количество ссылок

        for (int i=0; i<linksQuantity; i++) {

            originalWindow=driver.getWindowHandle();
            // сохранили идентификатор текущего окна

            existingWindows=driver.getWindowHandles();
            // сохранили идентификаторы уже открытых окон

            listLinks.get(i).click(); // кликаем по ссылке из найденного списка
            newWindow=wait.until(anyWindowOtherThan(existingWindows));
            // получаем идентификатор нового окна

            driver.switchTo().window(newWindow);  // переключаемся в новое окно

            try {
                Thread.sleep(sleepTimeMsek);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

            driver.close();  // закрываем окно

            driver.switchTo().window(originalWindow); // вернулись в исходное окно
        }
    }

}
