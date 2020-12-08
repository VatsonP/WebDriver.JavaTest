package ru.stqa.training.selenium.testapp;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import ru.stqa.training.selenium.driverbase.DriverBase;
import ru.stqa.training.selenium.pages.MainLiteCartPage;
import ru.stqa.training.selenium.pages.PageParams;

import java.util.List;


public class UT4CheckCartApp {

    private List<WebElement> prodList;
    private WebElement productUnit, Cart, prodTable;
    private int i, j, k, k1, p;
    private String[] prodName;
    private static final int  prodCartCount = 3;

    private static final long sleepTimeMsek = 300;

    private PageParams pageParams;
    private MainLiteCartPage    mainLiteCartPage;

    public void initPages(DriverBase drvBase) {

        pageParams = new PageParams(drvBase);

        mainLiteCartPage   = new MainLiteCartPage(pageParams);
    }


    public void myCheckCart() {
        prodName = new String[prodCartCount];

        for (i=0; i<prodCartCount; i++) {

            mainLiteCartPage.open().waitUntilMainPage();
            //подождать пока не загрузится главная страница с заголовком "Online Store"

            try {
                Thread.sleep(sleepTimeMsek);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

            prodList = mainLiteCartPage.Css_li_product_Elements; // определение списка товаров на главной странице

            p=1; j=0;
            while(p>0) {
                k=1; k1=1;
                productUnit = prodList.get(j);    // выбираем конкретный продукт
                prodName[i] = mainLiteCartPage.getCss_div_name_Text(productUnit); // получаем имя продукта

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
            mainLiteCartPage.waitUntilProdNameStr(prodName[i]);

            Cart = mainLiteCartPage.getPresenceOfElementLocatedById_cart(); // нашли корзину

            k = prodName[i].compareToIgnoreCase("Yellow Duck");
            // Проверяем, что выбранный товар не Yellow Duck - требует доп. обработки
            if (k==0) {  // Обработка Yellow Duck - выбираем размер
                new Select(mainLiteCartPage.byName_optionsSize).selectByVisibleText("Small");
            }

            pageParams.ThreadSleep(sleepTimeMsek);

            mainLiteCartPage.byName_add_cart_product.click();  // добавляем продукт в корзину

            mainLiteCartPage.waitUntil_textToBePresentInElement_Css_span_quantity(Cart, (i+1));
            // ждем изменения количества
        }

        mainLiteCartPage.open().waitUntilMainPage();
        //подождать пока не загрузится главная страница с заголовком "Online Store"

        mainLiteCartPage.byId_cart.click(); // открываем корзину
        mainLiteCartPage.waitUntil_Checkout(); // ожидаем открытия страницы корзины

        for(int n=1; n<=prodCartCount; n++) {
            prodTable = mainLiteCartPage.getPresenceOfElementLocatedById_order_conf_wrapper();
            // находим таблицу товаров в корзине

            pageParams.ThreadSleep(sleepTimeMsek);

            prodList = mainLiteCartPage.Css_li_shortcut_Elements;
            if(prodList.size()>0) {
            /*
              Поскольку изначально картинки продуктов на экране сменяются, мы просто определяем
              список маленьких изображений продуктов и щелкаем по нему.
              При этом изображение продукта и все связанные с ним служебные кнопки фиксируются.
            */
                prodList.get(0).click();
            }

            mainLiteCartPage.byName_remove_cart_item.click();          // кликнуть по кнопке удаления товара Remove
            mainLiteCartPage.waitUntilStalenessOfProdTable(prodTable); // ожидаем обновления таблицы со списком товаров
        }

        mainLiteCartPage.open().waitUntilMainPage();
        //подождать пока не загрузится главная страница с заголовком "Online Store"
    }
}


