package ru.stqa.training.selenium.testapp;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import ru.stqa.training.selenium.driverbase.DriverBase;
import ru.stqa.training.selenium.pages.AdminLeftMenuPage;
import ru.stqa.training.selenium.pages.AdminPanelLoginPage;
import ru.stqa.training.selenium.pages.PageParams;

import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;


public class UT4CheckCartApp {

    private List<WebElement> prodList;
    private WebElement productUnit, Cart, prodTable;
    private int i, j, k, k1, p;
    private String[] prodName;
    private static final int  prodCartCount = 3;

    private static final long sleepTimeMsek = 500;

    private PageParams pageParams;
    private AdminPanelLoginPage adminPanelLoginPage;
    private AdminLeftMenuPage   adminLeftMenuPage;

    public void initPages(DriverBase drvBase) {

        pageParams = new PageParams(drvBase);

        adminPanelLoginPage = new AdminPanelLoginPage(pageParams);
        adminLeftMenuPage   = new AdminLeftMenuPage(pageParams);
    }

    private void LoginAs(String usrText, String passText) {
        if (adminPanelLoginPage.open().isOnThisPage()) {
            adminPanelLoginPage.enterUsername(usrText).enterPassword(passText).submitLogin();
        }

        adminLeftMenuPage.waitUntilMyStore(); //подождать пока не загрузится страница с заголовком "My Store"
    }


    public void myCheckCart() {
        prodName = new String[prodCartCount];

        for (i=0; i<prodCartCount; i++) {
            PageParams.getWebDriver().get("http://" + PageParams.getCurrentIpStr() + ":8080/litecart/en/"); //открыть главную страницу магазина
            PageParams.getWebDriverWait().until(titleContains("Online Store"));

            try {
                Thread.sleep(sleepTimeMsek);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

            prodList = PageParams.getWebDriver().findElements(By.cssSelector("li.product"));
            // определение списка товаров на главной странице

            p=1; j=0;
            while(p>0) {
                k=1; k1=1;
                productUnit = prodList.get(j);    // выбираем конкретный продукт
                prodName[i]=productUnit.findElement(By.cssSelector("div.name")).getText();
                // получаем имя продукта

                if(i==1) { // для 2-го товара
                    // проверяем, что выбранный товар не совпадает с предыдущим
                    k=prodName[i].compareToIgnoreCase(prodName[i-1]);
                }

                if (i==2) { // для 3-го товара
                    // проверяем, что выбранный товар не совпадает с предыдущими
                    k=prodName[i].compareToIgnoreCase(prodName[i-1]);
                    k1=prodName[i].compareToIgnoreCase(prodName[i-2]);
                }

                if((k==0)||(k1==0)) { j++; } // переходим на следующий продукт в списке
                else { p=0; }  // подходящий продукт найден - прерываем цикл
            }

            productUnit.click(); //щелкаем по странице продукта
            PageParams.getWebDriverWait().until(titleContains(prodName[i]));


            Cart = PageParams.getWebDriverWait().until(presenceOfElementLocated(By.id("cart"))); // нашли корзину
            k=prodName[i].compareToIgnoreCase("Yellow Duck");
            // Проверяем, что выбранный товар не Yellow Duck - требует доп. обработки
            if (k==0) {  // Обработка Yellow Duck - выбираем размер
                new Select(PageParams.getWebDriver().findElement(By.name("options[Size]"))).selectByVisibleText("Small");
            }

            try {
                Thread.sleep(sleepTimeMsek);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

            PageParams.getWebDriver().findElement(By.name("add_cart_product")).click();
            // добавляем продукт в корзину

            PageParams.getWebDriverWait().until(textToBePresentInElement(
                    Cart.findElement(By.cssSelector("span.quantity")),
                    Integer.toString(i+1)));
            // ждем изменения количества
        }

        PageParams.getWebDriver().get("http://" + PageParams.getCurrentIpStr() + ":8080/litecart/en/"); //открыть главную страницу магазина

        PageParams.getWebDriver().findElement(By.id("cart")).click(); // открываем корзину
        PageParams.getWebDriverWait().until(titleContains("Checkout")); // ожидаем открытия страницы корзины

        for(int n=1; n<=prodCartCount; n++) {
            prodTable = PageParams.getWebDriverWait().until(presenceOfElementLocated(By.id("order_confirmation-wrapper")));
            // находим таблицу товаров в корзине

            try {
                Thread.sleep(sleepTimeMsek);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

            prodList = PageParams.getWebDriver().findElements(By.cssSelector("li.shortcut"));
            if(prodList.size()>0) {
            /*
              Поскольку изначально картинки продуктов на экране сменяются, мы просто определяем
              список маленьких изображений продуктов и щелкаем по нему.
              При этом изображение продукта и все связанные с ним служебные кнопки фиксируются.
            */
                prodList.get(0).click();
            }

            PageParams.getWebDriver().findElement(By.name("remove_cart_item")).click();
            // кликнуть по кнопке удаления товара Remove
            PageParams.getWebDriverWait().until(stalenessOf(prodTable));  // ожидаем обновления таблицы со списком товаров

        }

        PageParams.getWebDriver().get("http://" + PageParams.getCurrentIpStr() + ":8080/litecart/en/");
        PageParams.getWebDriverWait().until(titleContains("Online Store"));
    }
}


