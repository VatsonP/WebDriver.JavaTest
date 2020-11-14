package ru.stqa.training.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class AdminLeftMenuPage extends Page {

    public AdminLeftMenuPage(PageParams pageParams) {
        super(pageParams);
        PageFactory.initElements(pageParams.getWebDriver(), this);
    }

    public void open() {
        pageParams.getWebDriver().get("http://" + pageParams.getCurrentIpStr() + ":8080/litecart/admin/"); //открыть страницу
    }

    @FindBy(id="app-")
    public List<WebElement> id_app_Elements;

    @FindBy(css="h1")
    public WebElement css_h1_Element;

    public List<WebElement> getCss_menu_id_doc_Elements(WebElement menuPoint){
        return menuPoint.findElements(By.cssSelector("[id^=doc-]"));
    }

}

