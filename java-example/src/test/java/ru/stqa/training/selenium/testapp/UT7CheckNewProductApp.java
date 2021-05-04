package ru.stqa.training.selenium.testapp;

import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.Select;
import ru.stqa.training.selenium.driverbase.DriverBase;
import ru.stqa.training.selenium.pages.PageParams;
import ru.stqa.training.selenium.pages.AdminPanelLoginPage;
import ru.stqa.training.selenium.pages.AdminNewProdPage;

import java.util.Calendar;


public class UT7CheckNewProductApp {

    private PageParams          pageParams;
    private AdminPanelLoginPage adminPanelLoginPage;
    private AdminNewProdPage    adminNewProdPage;

    String Name, ProdName, validFrom, validTo, prefix;

    private long sleepTimeMsec;

    public UT7CheckNewProductApp(long _sleepTimeMSec)
    {
        sleepTimeMsec = _sleepTimeMSec;
    }

    public void initPages(DriverBase drvBase) {

        pageParams = new PageParams(drvBase);

        adminPanelLoginPage = new AdminPanelLoginPage(pageParams);
        adminNewProdPage = new AdminNewProdPage(pageParams);
    }

    private void LoginAs(String usrText, String passText)
    {
        if (adminPanelLoginPage.open().isOnThisPage())
        {
            adminPanelLoginPage.enterUsername(usrText).enterPassword(passText).submitLogin();
        }

        adminNewProdPage.waitUntilMyStore(); //подождать пока не загрузится страница с заголовком "My Store"
    }


    public void myCheckNewProduct() {

        //открыть страницу и выполнить коннект под пользователем + ждем страницу "My Store"
        LoginAs("admin", "admin");

        // читаем текущее время - добавляем его к фамилии и имеем уникальный e-mail и пароль каждый раз
        AdminNewProdPage.CurDateTime curDateTime = adminNewProdPage.createCurDateTime(Calendar.getInstance());

        Name      = "Donald McDown";
        prefix    = adminNewProdPage.GetProdPrefix(curDateTime);
        ProdName  = Name + " " + prefix;

        validFrom = adminNewProdPage.GetProdValidFrom(curDateTime);
        validTo   = adminNewProdPage.GetProdValidTo(curDateTime);

        adminNewProdPage.Css_catalog_ElementClick();// открыть каталог

		adminNewProdPage.LinkText_ElementClick("Add New Product");// открываем форму регистрации нового продукта

        //wait = new WebDriverWait(driver,10);

    	adminNewProdPage.Name_ElementClick("status"); // устанавливаем статус Enabled

		adminNewProdPage.Name_ElementClear("name[en]"); // очистка

        adminNewProdPage.Name_ElementSendKeys("name[en]", ProdName); // вводим название товара
        adminNewProdPage.Name_ElementSendKeys("code", (prefix + Keys.TAB));// вводим код товара

  		adminNewProdPage.XPath_categories_RubberDucks_ElementClick(); // устанавливаем категорию Rubber Ducks

		adminNewProdPage.XPath_categories_Unisex_ElementClick();      // Устанавливаем группу Unisex

        adminNewProdPage.Name_ElementSendKeys("quantity", "1"); // устанавливаем количество 1
        adminNewProdPage.Name_ElementSendKeys("date_valid_from", validFrom); // устанавливаем дату начала годности
        adminNewProdPage.Name_ElementSendKeys("date_valid_to",   validTo);   // устанавливаем дату конца годности

        pageParams.ThreadSleep(sleepTimeMsec);

        adminNewProdPage.LinkText_ElementClick("Information");// переходим на вкладку Information

        //wait = new WebDriverWait(driver,10);

		// select the drop down list and create select element object
        Select selectElementId = new Select(adminNewProdPage.Name_manufacturerId_Element);
		selectElementId.selectByVisibleText("ACME Corp."); // выбираем корпорацию

        adminNewProdPage.Name_ElementSendKeys("keywords", "Duck"); // Ввводим ключевое слово
        adminNewProdPage.Name_ElementSendKeys("short_description[en]", "Duck"); // задаем краткое описание
        adminNewProdPage.Name_ElementSendKeys("description[en]", (ProdName + " is cool!")); // задаем описание
        adminNewProdPage.Name_ElementSendKeys("head_title[en]" , ProdName);                 // задаем заголовок
        adminNewProdPage.Name_ElementSendKeys("meta_description[en]", "666666666");	        // задаем метаописание

        pageParams.ThreadSleep(sleepTimeMsec);

        adminNewProdPage.LinkText_ElementClick("Data"); // переходим на вкладку Data

        //wait = new WebDriverWait(driver,10);

        adminNewProdPage.Name_ElementSendKeys("sku", prefix);			    // заполняем поле SKU
        adminNewProdPage.Name_ElementSendKeys("gtin", prefix);				// заполняем поле GTIN
        adminNewProdPage.Name_ElementSendKeys("taric", prefix);             // заполняем поле TARIC
        adminNewProdPage.Name_ElementSendKeys("weight", "1");				// задаем вес
        adminNewProdPage.Name_ElementSendKeys("dim_x", "10");
        adminNewProdPage.Name_ElementSendKeys("dim_y", "11");
        adminNewProdPage.Name_ElementSendKeys("dim_z", "12");               // задаем размеры
        adminNewProdPage.Name_ElementSendKeys("attributes[en]", "None");	// задаем атрибуты

        pageParams.ThreadSleep(sleepTimeMsec);

        adminNewProdPage.LinkText_ElementClick("Prices");   // переходим на вкладку Prices

        // даем время на загрузку  // Для задания явных ожиданий
        //wait = new WebDriverWait(driver, TimeSpan.FromSeconds(driverBaseParams.drvExplWaitTime));

        adminNewProdPage.Name_ElementSendKeys("purchase_price", "13");      // задаем цену

        // select the drop down list and create select element object
        Select selectElementCurr = new Select(adminNewProdPage.Name_currencyCode_Element);//
        selectElementCurr.selectByVisibleText("Euros");// select by text - выбираем валюту

        adminNewProdPage.Name_ElementSendKeys("gross_prices[USD]", "20"); // задаем цену в долларах

        pageParams.ThreadSleep(sleepTimeMsec);

        adminNewProdPage.Name_ElementClick("save"); // сохраняем продукт

        // даем время на загрузку  // Для задания явных ожиданий
        //wait = new WebDriverWait(driver, TimeSpan.FromSeconds(driverBaseParams.drvExplWaitTime));

        // Проверяем наличие такого элемента на странице
        adminNewProdPage.LinkText_FindElement(ProdName);
    }
}




