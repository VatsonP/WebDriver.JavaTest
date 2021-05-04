package ru.stqa.training.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.Calendar;
import java.util.List;
import java.util.Set;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;


public class AdminNewProdPage extends Page {

    public AdminNewProdPage(PageParams pageParams) {
        super(pageParams);
        PageFactory.initElements(PageParams.getWebDriver(), this);
    }

    public void open() {
        PageParams.getWebDriver().get("http://" + PageParams.getCurrentIpStr() + ":8080/litecart/admin/"); //открыть страницу
    }


    public CurDateTime createCurDateTime(Calendar newCalendar) {
        return new CurDateTime(newCalendar);
    }

    public class CurDateTime {
        public Calendar calendar;
        public int yyyy;
        public int mm;
        public int dd;
        public int h;
        public int m;
        public int s;

        public CurDateTime(Calendar newCalendar)
        {
            calendar = newCalendar;

            yyyy = calendar.get(calendar.YEAR);
            mm   = calendar.get(calendar.MONTH);
            dd   = calendar.get(calendar.DAY_OF_MONTH);
            h    = calendar.get(calendar.HOUR_OF_DAY);
            m    = calendar.get(calendar.MINUTE);
            s    = calendar.get(calendar.SECOND);
        }
    }

    public String GetProdPrefix(CurDateTime curDateTime)
        { 
            return PaddingLeft(curDateTime.h) +
                   PaddingLeft(curDateTime.m) +
                   PaddingLeft(curDateTime.s);
        }

    public String GetProdValidFrom(CurDateTime curDateTime)
        { 
            return pageParams.getFullDateStrForBrowserDateControl(curDateTime.yyyy, curDateTime.mm, curDateTime.dd);
        }

    public String GetProdValidTo(CurDateTime curDateTime)
        { 
            return pageParams.getFullDateStrForBrowserDateControl(curDateTime.yyyy + 2, curDateTime.mm, curDateTime.dd);
        }

    public void Css_catalog_ElementClick() {
        pageParams.findElmAndClick(By.cssSelector("[href*=catalog]"));
    }

    public void LinkText_ElementClick(String text) {
        pageParams.findElmAndClick(By.linkText(text));
    }

    public void Name_ElementClick(String text) {
        pageParams.findElmAndClick(By.name(text));
    }

    public void Name_ElementClear(String text) {
        pageParams.findElmAndClear(By.name(text));
    }

    public void Name_ElementSendKeys(String text, String keyText) {
        pageParams.findElmAndSendKeys(By.name(text), keyText);
    }

    public void XPath_categories_RubberDucks_ElementClick() {
        pageParams.findElmAndClick(By.xpath("(//input[@name='categories[]'])[2]"));
    }
    public void XPath_categories_Unisex_ElementClick() {
        pageParams.findElmAndClick(By.xpath("(//input[@name='product_groups[]'])[3]"));
    }

    public void LinkText_FindElement(String text) {
        pageParams.getWebDriver().findElement(By.linkText(text));
    }

    @FindBy(name="manufacturer_id")
    public WebElement Name_manufacturerId_Element;

    @FindBy(name="purchase_price_currency_code")
    public WebElement Name_currencyCode_Element;

    //-------------------------------------------------------------------------

    @FindBy(css="[id=box-campaigns] li.product")
    public WebElement Css_FirstProduct_Campains_Element;

    @FindBy(css="[id=box-product]")
    public WebElement Css_Box_Product_Element;

    //-------------------------------------------------------------------------

    public AdminNewProdPage waitUntilMyStore()
    {
        PageParams.getWebDriverWait().until(titleIs("My Store"));
        //подождать пока не загрузится страница с заголовком "My Store"
        return this;
    }

    private static String PaddingLeft(int intS)
    {
        return PaddingLeft(intS, 2);
    }

    private static String PaddingLeft(int intS, int totalWidth)
    {
        return PaddingLeft(intS, totalWidth, '0');
    }

    private static String PaddingLeft(int intS, int totalWidth, char paddingChar)
    {
        return String.format("%" + paddingChar + totalWidth + "d", intS);
    }

}
