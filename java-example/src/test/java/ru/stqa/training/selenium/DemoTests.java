package ru.stqa.training.selenium;

import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
//import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.stqa.training.selenium.driverbase.DriverBase;
import ru.stqa.training.selenium.driverbase.DriverBaseParams;

import java.lang.Thread;
import java.util.List;


import static org.openqa.selenium.support.ui.ExpectedConditions.titleContains;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;


public class DemoTests extends DriverBase {

    private static final long sleepTimeMsek = 2000;

    public DemoTests() {
        super (new DriverBaseParams());
    }

    @Test
    public void FirstTest() {
        driver.get("http://www.google.com");
        driver.findElement(By.name("q")).sendKeys("webdriver");
        driver.findElement(By.name("q")).sendKeys(Keys.RETURN);
        wait.until(titleContains("webdriver"));

        try {
            Thread.sleep(sleepTimeMsek);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void SecondTest()
    {
        //XAMPP litecart - "http://" + currentIpStr + ":8080/litecart/en/"
        driver.get("http://" + getCurrentIpStr() + ":8080/litecart/en/");
        WebElement form1 = driver.findElement(By.cssSelector("div[class='input-wrapper'] [type='search']"));
        form1.sendKeys("duck");
        form1.sendKeys(Keys.RETURN);
        wait.until(titleContains("duck"));

        try {
            Thread.sleep(sleepTimeMsek);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void ThirdTest()
    {
        driver.get("http://www.google.com");
        String searchingElementName = "q-q-q";

        //Пример исключения InvalidSelectorException - eсли передан неправильный локатор
        //DriverBase.isElementPresent(driver, By.cssSelector("1 2 3 notValidCss"), true, 1);
        //Пример исключения NoSuchElementException - eсли элемент отсутствует в DOM на момент вызова
        //DriverBase.isElementPresent(driver, By.name(searchingElementName), true, 1);

        if (isElementPresent(driver, By.name(searchingElementName)) ) {

            driver.findElement(By.name(searchingElementName)).sendKeys("webdriver");
            driver.findElement(By.name(searchingElementName)).sendKeys(Keys.RETURN);
        }

        wait.until(titleContains("webdriver"));

        try {
            Thread.sleep(sleepTimeMsek);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void FourthTest()
    {
        //XAMPP litecart - "http://" + currentIpStr + ":8080/litecart/en/"
        driver.get("http://" + getCurrentIpStr() + ":8080/litecart/en/");
        WebElement Element1 = driver.findElement(By.cssSelector("div[class='input-wrapper'] [type='search']"));
        Element1.sendKeys("duck");

        // использование функции GetAttribute()
        checkAndPrintAttributeByName(Element1, "value");
        checkAndPrintAttributeByName(Element1, "placeholder");
        // атрибуты(свойства) типа boolean возвращают true (при их наличии) и null (при их отсутствии)
        checkAndPrintAttributeByName(Element1, "spellcheck");
        checkAndPrintAttributeByName(Element1, "draggable");
        checkAndPrintAttributeByName(Element1, "a-a-a-a-a");

        Element1.sendKeys(Keys.RETURN);
        wait.until(titleContains("duck"));

        try {
            Thread.sleep(sleepTimeMsek);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void FifthTest() {
        //XAMPP litecart admin - simple login test
        driver.get("http://" + getCurrentIpStr() + ":8080/litecart/admin/"); //открыть страницу

        if (isElementPresent(driver, By.name("username")))
            driver.findElement(By.name("username")).sendKeys("admin"); //найти поле для ввода логина и ввести "admin"
        if (isElementPresent(driver, By.name("password")))
            driver.findElement(By.name("password")).sendKeys("admin"); //найти поле для ввода пароля и ввести "admin"
        if (isElementPresent(driver, By.name("password")))
            driver.findElement(By.name("login")).click();                          //найти кнопку логина и нажать на нее

        wait.until(titleIs("My Store"));
        //подождать пока не загрузится страница с заголовком "My Store"

        try {
            Thread.sleep(sleepTimeMsek);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void SeventhTest_myCheckStiker() {
        int prodQuantity, prodStickerQuantity;
        int allProdWithStickerQuantity = 0;
        WebElement productUnit;
        List<WebElement> prodList, stickerList;

        //XAMPP litecart - "http://" + currentIpStr + ":8080/litecart/en/"
        driver.get("http://" + getCurrentIpStr() + ":8080/litecart/en/");

        driver.get("http://" + getCurrentIpStr() + ":8080/litecart/en/rubber-ducks-c-1/"); //открыть страницу магазина с товарами
        wait = new WebDriverWait(driver,10);

        prodList = driver.findElements(By.cssSelector("li.product"));
        // определение списка товаров на главной странице
        prodQuantity = prodList.size(); // сохраняем количество товаров

        System.out.println("prodQuantity: " + prodQuantity);

        for (int i=0; i<prodQuantity; i++  ) {  //проходим по списку товаров
            prodList = driver.findElements(By.cssSelector("li.product"));
            productUnit = prodList.get(i);

            //определение списка стикеров (полосок) у товара

            stickerList = productUnit.findElements(By.cssSelector("div.sticker"));
            //определение количества стикеров у товара
            prodStickerQuantity = stickerList.size();

            System.out.println("prodNum (i): " + i);
            System.out.println("prodStickerQuantity: " + prodStickerQuantity);

            //проверка что у товара не более одного стикера
            Assert.assertTrue(prodStickerQuantity <= 1);

            if (prodStickerQuantity == 1)
                allProdWithStickerQuantity = allProdWithStickerQuantity + 1;
        }

        System.out.println("---------------------------------");
        System.out.println("allProdWithStickerQuantity: " + allProdWithStickerQuantity);
    }
}
