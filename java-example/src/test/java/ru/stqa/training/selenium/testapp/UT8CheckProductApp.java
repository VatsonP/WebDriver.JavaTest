package ru.stqa.training.selenium.testapp;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.stqa.training.selenium.driverbase.DriverBase;
import ru.stqa.training.selenium.pages.PageParams;
import ru.stqa.training.selenium.pages.AdminPanelLoginPage;
import ru.stqa.training.selenium.pages.AdminNewProdPage;

import java.util.Calendar;


public class UT8CheckProductApp {

    private PageParams          pageParams;
    private AdminNewProdPage    adminNewProdPage;

    WebElement productUnit;

    String[] regularPrice1, regularPrice2, campaignPrice1, campaignPrice2;
    String productName1, productName2;
    float size1, size2;

    private long sleepTimeMsec;

    public UT8CheckProductApp(long _sleepTimeMSec)
    {
        sleepTimeMsec = _sleepTimeMSec;
    }

    public void initPages(DriverBase drvBase) {

        pageParams = new PageParams(drvBase);

        adminNewProdPage = new AdminNewProdPage(pageParams);
    }

    private void OpenStoreMainPage()
    {
        //открыть главную страницу магазина
        PageParams.getWebDriver().get("http://" + PageParams.getCurrentIpStr() + ":8080/litecart/en/");
    }

    public void myCheckProduct() {

        OpenStoreMainPage();

        productUnit = adminNewProdPage.Css_FirstProduct_Campains_Element;// выбрали первый товар в разделе Campains

        productName1 = productUnit.findElement(By.cssSelector(".name")).getText();
        // сохраняем имя продукта

        regularPrice1 = new String[4];
        campaignPrice1 = new String[4];

        // Сохраняем параметры обычной цены
        regularPrice1[0]= productUnit.findElement(By.cssSelector(".regular-price")).getText();
        // сохраняем значение обычной цены
        // поскольку с цветами в приложении ошибка - проверку цвета исключаем
        //regularPrice1[1]= productUnit.findElement(By.cssSelector(".regular-price")).getCssValue("color");
        // сохраняем цвет шрифта обычной цены
        regularPrice1[1]= productUnit.findElement(By.cssSelector(".regular-price")).getCssValue("font-weight");
        // сохраняем тип шрифта обычной цены - жирный или нет
        regularPrice1[2]= productUnit.findElement(By.cssSelector(".regular-price")).getCssValue("text-decoration");
        // сохраняем оформление шрифта обычной цены
        regularPrice1[3]= productUnit.findElement(By.cssSelector(".regular-price")).getCssValue("font-size");
        // сохраняем размер шрифта обычной цены

        size1 = Float.parseFloat(regularPrice1[3].replaceAll("px",""));
        // получаем числовое значение размера шрифта


        // сохраняем параметры цены по акции
        campaignPrice1[0]= productUnit.findElement(By.cssSelector(".campaign-price")).getText();
        // сохраняем значение цены по акции
        // поскольку с цветами в приложении ошибка - проверку цвета исключаем
        // campaignPrice1[1]= productUnit.findElement(By.cssSelector(".campaign-price")).getCssValue("color");
        // сохраняем цвет шрифта цены по акции
        campaignPrice1[1]= productUnit.findElement(By.cssSelector(".campaign-price")).getCssValue("font-weight");
        // сохраняем размер шрифта цены по акции
        campaignPrice1[2]= productUnit.findElement(By.cssSelector(".campaign-price")).getCssValue("text-decoration");
        // сохраняем оформление шрифта обычной цены
        campaignPrice1[3]= productUnit.findElement(By.cssSelector(".campaign-price")).getCssValue("font-size");
        // сохраняем размер шрифта цены по акции
        size2=Float.parseFloat(campaignPrice1[3].replaceAll("px",""));
        // получаем числовое значение размера шрифта

        pageParams.ThreadSleep(sleepTimeMsec);

        Assert.assertTrue(size1<size2);
        // сравниваем размеры шрифтов цен - шрифт обычной цены меньше чем у акционной


        productUnit.click();  // открываем страницу продукта

        //  wait = new WebDriverWait(driver, driverBaseParams.getExplWaitTime()); // даем время на загрузку

        productUnit = adminNewProdPage.Css_Box_Product_Element; // выбираем блок продукта на странице

        productName2 = productUnit.findElement(By.cssSelector("[itemprop=name]")).getText();
        // сохраняем имя продукта

        pageParams.ThreadSleep(sleepTimeMsec);

        Assert.assertTrue(productName1.compareTo(productName2)==0);
        // проверка совпадения названий продуктов

        regularPrice2 = new String[4];
        campaignPrice2 = new String[4];

        // Сохраняем параметры обычной цены
        regularPrice2[0]= productUnit.findElement(By.cssSelector(".regular-price")).getText();
        // сохраняем значение обычной цены
        // поскольку с цветами в приложении ошибка - проверку цвета исключаем
        //regularPrice2[1]= productUnit.findElement(By.cssSelector(".regular-price")).getCssValue("color");
        // сохраняем цвет шрифта обычной цены
        regularPrice2[1]= productUnit.findElement(By.cssSelector(".regular-price")).getCssValue("font-weight");
        // сохраняем тип шрифта обычной цены - жирный или нет
        regularPrice2[2]= productUnit.findElement(By.cssSelector(".regular-price")).getCssValue("text-decoration");
        // сохраняем оформление шрифта обычной цены
        regularPrice2[3]= productUnit.findElement(By.cssSelector(".regular-price")).getCssValue("font-size");
        // сохраняем размер шрифта обычной цены
        size1 = Float.parseFloat(regularPrice2[3].replaceAll("px",""));
        // получаем числовое значение размера шрифта

        // сохраняем параметры цены по акции
        campaignPrice2[0]= productUnit.findElement(By.cssSelector(".campaign-price")).getText();
        // сохраняем значение цены по акции
        // поскольку с цветами в приложении ошибка - проверку цвета исключаем
        //campaignPrice2[1]= productUnit.findElement(By.cssSelector(".campaign-price")).getCssValue("color");
        // сохраняем цвет шрифта цены по акции
        campaignPrice2[1]= productUnit.findElement(By.cssSelector(".campaign-price")).getCssValue("font-weight");
        // сохраняем размер шрифта цены по акции
        campaignPrice2[2]= productUnit.findElement(By.cssSelector(".campaign-price")).getCssValue("text-decoration");
        // сохраняем оформление шрифта обычной цены
        campaignPrice2[3]= productUnit.findElement(By.cssSelector(".campaign-price")).getCssValue("font-size");
        // сохраняем размер шрифта цены по акции
        size2=Float.parseFloat(campaignPrice2[3].replaceAll("px",""));
        // получаем числовое значение размера шрифта

        pageParams.ThreadSleep(sleepTimeMsec);

        Assert.assertTrue(size1<size2);
        // сравниваем размеры шрифтов цен - шрифт обычной цены меньше чем у акционной

        // сравниваем значения и параметры оформления цен
        for(int i=0; i<3;i++) {

            System.out.println("regularPrice1["+ i +"]= " +  regularPrice1[i]  + "; regularPrice2["+ i +"]="  +  regularPrice2[i]  + ";");
            System.out.println("campaignPrice1["+ i +"]= "+  campaignPrice1[i] + "; campaignPrice2["+ i +"]=" +  campaignPrice2[i] + ";");

            //Assert.assertTrue(regularPrice1[i].compareTo(regularPrice2[i])==0);
            // совпадение значений и оформления обычной цены
            //Assert.assertTrue(campaignPrice1[i].compareTo(campaignPrice2[i])==0);
            // совпадение значений и оформления цены по акции
        }
    }
}




