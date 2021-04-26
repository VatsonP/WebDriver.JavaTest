package ru.stqa.training.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;

import java.util.List;
import java.util.Set;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleContains;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class AdminLeftMenuPage extends Page {

    public AdminLeftMenuPage(PageParams pageParams) {
        super(pageParams);
        PageFactory.initElements(PageParams.getWebDriver(), this);
    }

    public void open() {
        PageParams.getWebDriver().get("http://" + PageParams.getCurrentIpStr() + ":8080/litecart/admin/"); //открыть страницу
    }

    @FindBy(id="app-")
    public List<WebElement> id_app_Elements;

    @FindBy(css="h1")
    public WebElement css_h1_Element;

    public List<WebElement> getCss_menu_id_doc_Elements(WebElement menuPoint){
        return menuPoint.findElements(By.cssSelector("[id^=doc-]"));
    }

    //-- for UT5CheckCountriesApp ----------------------------------------------
    @FindBy(css="[name=geo_zones_form] .row")
    public List<WebElement> Css_geo_zones_row_Elements;

    @FindBy(css="[id=table-zones] tr")
    public List<WebElement> Css_id_table_zones_tr_Elements;

    public AdminLeftMenuPage OpenGeoZones()
    {
        // открываем страницу просмотра географических зон
        PageParams.getWebDriver().get("http://" + PageParams.getCurrentIpStr() + ":8080/litecart/admin/?app=geo_zones&doc=geo_zones");

        return this;
    }

    public WebElement Row_Css_a_Element(WebElement row)
    {
        return row.findElement(By.cssSelector("a"));
    }

    public WebElement CountryRow_Css_td_nth_child6_Element(WebElement countryRow)
    {
        return countryRow.findElement(By.cssSelector("td:nth-child(6)"));
    }

    public WebElement ZoneRow_Css_td_nth_child3_Element(WebElement zoneRow)
    {
        return zoneRow.findElement(By.cssSelector("td:nth-child(3)"));
    }

    public WebElement ZoneRow_Css_id_table_zones_td_nth_child3_Element(WebElement zoneRow)
    {
        return zoneRow.findElement(By.cssSelector("[id=table-zones] tr td:nth-child(3) [selected=selected]"));
    }

    //-- for UT3CheckNewTabsApp ----------------------------------------------
    @FindBy(css="[name=countries_form] .row")
    public List<WebElement> Css_countries_row_Elements;

    @FindBy(css="form .fa-external-link")
    public List<WebElement> Css_form_fa_external_link_Elements;

    public WebElement getCss_a_Elements(WebElement countryRow)
    {
        return countryRow.findElement(By.cssSelector("a"));
    }

    public AdminLeftMenuPage waitUntilEditCountry()
    {
        // открываем страницу выбранной страны
        PageParams.getWebDriverWait().until(titleContains("Edit Country"));
        // ждем загрузки страницы
        return this;
    }

    public String getCurrentWindowHandle()
    {
        return PageParams.getWebDriver().getWindowHandle();
    }

    public Set<String> getWindowHandles()
    {
        return PageParams.getWebDriver().getWindowHandles();
    }

    private ExpectedCondition<String> anyWindowOtherThan(Set<String> oldWindows) {
        return new ExpectedCondition<String>() {
            public String apply(WebDriver driver) {
                Set<String> handles=driver.getWindowHandles();
                handles.removeAll(oldWindows);
                return handles.size()>0 ? handles.iterator().next():null;
            }
        };
    }

    public String waitUntilEditCountry(Set<String> existingWindows)
    {
        return PageParams.getWebDriverWait().until(anyWindowOtherThan(existingWindows)); // ждем загрузки окна
    }

    public void SwitchToWindow(String strWindow)
    {
        PageParams.getWebDriver().switchTo().window(strWindow);   // переключаемся в новое окно
    }

    public void CloseCurWindow()
    {
        PageParams.getWebDriver().close(); // закрываем окно
    }
   
    public AdminLeftMenuPage waitUntilMyStore()
    {
        PageParams.getWebDriverWait().until(titleIs("My Store"));
        //подождать пока не загрузится страница с заголовком "My Store"
        return this;
    }

    public AdminLeftMenuPage openCountries()
    {
        //XAMPP litecart admin page - "http://" + CurrentIpStr + ":8080/litecart/admin/?app=countries&doc=countries"
        PageParams.getWebDriver().get("http://" + PageParams.getCurrentIpStr() + ":8080/litecart/admin/?app=countries&doc=countries");
        //открыть страницу со списком стран

        return this;
    }
    public AdminLeftMenuPage waitUntilCountries()
    {
        PageParams.getWebDriverWait().until(titleContains("Countries"));
        // ждем загрузки страницы
        return this;
    }
}

