package ru.stqa.training.selenium.testapp;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import ru.stqa.training.selenium.driverbase.DriverBase;

import ru.stqa.training.selenium.pages.AdminPanelLoginPage;
import ru.stqa.training.selenium.pages.PageParams;

import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleContains;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;


public class FirstDemoApp {

    private static final long sleepTimeMsek        = 1000;

    private PageParams pageParams;
    private AdminPanelLoginPage adminPanelLoginPage;

    public void initPages(DriverBase drvBase) {

        pageParams = new PageParams(drvBase);

        adminPanelLoginPage = new AdminPanelLoginPage(pageParams);
    }

    private void LoginAs(String usrText, String passText) {
        if (adminPanelLoginPage.open().isOnThisPage()) {
            adminPanelLoginPage.enterUsername(usrText).enterPassword(passText).submitLogin();
        }
    }

    public void FirstTest() {
        PageParams.getWebDriver().get("http://www.google.com");
        PageParams.getWebDriver().findElement(By.name("q")).sendKeys("webdriver");
        PageParams.getWebDriver().findElement(By.name("q")).sendKeys(Keys.RETURN);

        PageParams.getWebDriverWait().until(titleContains("webdriver"));

        pageParams.ThreadSleep(sleepTimeMsek);
    }

    public void SecondTest()
    {
        //XAMPP litecart - "http://" + currentIpStr + ":8080/litecart/en/"
        PageParams.getWebDriver().get("http://" + PageParams.getCurrentIpStr() + ":8080/litecart/en/");
        WebElement form1 = PageParams.getWebDriver().findElement(By.cssSelector("div[class='input-wrapper'] [type='search']"));
        form1.sendKeys("duck");
        form1.sendKeys(Keys.RETURN);
        PageParams.getWebDriverWait().until(titleContains("duck"));

        pageParams.ThreadSleep(sleepTimeMsek);
    }

    public void ThirdTest()
    {
        PageParams.getWebDriver().get("http://www.google.com");
        String searchingElementName = "q-q-q";

        //Пример исключения InvalidSelectorException - eсли передан неправильный локатор
        //DriverBase.isElementPresent(driver, By.cssSelector("1 2 3 notValidCss"), true, 1);
        //Пример исключения NoSuchElementException - eсли элемент отсутствует в DOM на момент вызова
        //DriverBase.isElementPresent(driver, By.name(searchingElementName), true, 1);

        if (pageParams.isElementPresent(PageParams.getWebDriver(), By.name(searchingElementName)) ) {

            PageParams.getWebDriver().findElement(By.name(searchingElementName)).sendKeys("webdriver");
            PageParams.getWebDriver().findElement(By.name(searchingElementName)).sendKeys(Keys.RETURN);
        }

        PageParams.getWebDriverWait().until(titleContains("webdriver"));

        pageParams.ThreadSleep(sleepTimeMsek);
    }

    public void FourthTest()
    {
        //XAMPP litecart - "http://" + currentIpStr + ":8080/litecart/en/"
        PageParams.getWebDriver().get("http://" + PageParams.getCurrentIpStr() + ":8080/litecart/en/");
        WebElement Element1 = PageParams.getWebDriver().findElement(By.cssSelector("div[class='input-wrapper'] [type='search']"));
        Element1.sendKeys("duck");

        // использование функции GetAttribute()
        pageParams.checkAndPrintAttributeByName(Element1, "value");
        pageParams.checkAndPrintAttributeByName(Element1, "placeholder");
        // атрибуты(свойства) типа boolean возвращают true (при их наличии) и null (при их отсутствии)
        pageParams.checkAndPrintAttributeByName(Element1, "spellcheck");
        pageParams.checkAndPrintAttributeByName(Element1, "draggable");
        pageParams.checkAndPrintAttributeByName(Element1, "a-a-a-a-a");

        Element1.sendKeys(Keys.RETURN);
        PageParams.getWebDriverWait().until(titleContains("duck"));

        try {
            Thread.sleep(sleepTimeMsek);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        pageParams.ThreadSleep(sleepTimeMsek);
    }

    public void FifthTest() {
        //XAMPP litecart admin - simple login test
        PageParams.getWebDriver().get("http://" + PageParams.getCurrentIpStr() + ":8080/litecart/admin/"); //открыть страницу

        LoginAs("admin", "admin");

        PageParams.getWebDriverWait().until(titleIs("My Store"));
        //подождать пока не загрузится страница с заголовком "My Store"

        pageParams.ThreadSleep(sleepTimeMsek);
    }

    public void SixthTest_myCheckStiker() {
        int prodQuantity, prodStickerQuantity;
        int allProdWithStickerQuantity = 0;
        WebElement productUnit;
        List<WebElement> prodList, stickerList;

        //XAMPP litecart - "http://" + currentIpStr + ":8080/litecart/en/"
        PageParams.getWebDriver().get("http://" + PageParams.getCurrentIpStr() + ":8080/litecart/en/");

        PageParams.getWebDriver().get("http://" + PageParams.getCurrentIpStr() + ":8080/litecart/en/rubber-ducks-c-1/"); //открыть страницу магазина с товарами

        prodList = PageParams.getWebDriver().findElements(By.cssSelector("li.product"));
        // определение списка товаров на главной странице
        prodQuantity = prodList.size(); // сохраняем количество товаров

        System.out.println("prodQuantity: " + prodQuantity);

        for (int i=0; i<prodQuantity; i++  ) {  //проходим по списку товаров
            prodList = PageParams.getWebDriver().findElements(By.cssSelector("li.product"));
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


