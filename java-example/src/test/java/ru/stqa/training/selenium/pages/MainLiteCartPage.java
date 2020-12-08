package ru.stqa.training.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleContains;
import static org.openqa.selenium.support.ui.ExpectedConditions.textToBePresentInElement;
import static org.openqa.selenium.support.ui.ExpectedConditions.stalenessOf;



public class MainLiteCartPage extends Page {

    public MainLiteCartPage(PageParams pageParams) {
        super(pageParams);
        PageFactory.initElements(PageParams.getWebDriver(), this);
    }

    public MainLiteCartPage open() {
        PageParams.getWebDriver().get("http://" + PageParams.getCurrentIpStr() + ":8080/litecart/en/");
        //открыть главную страницу магазина
        return this;
    }

    public MainLiteCartPage waitUntilMainPage()
    {
        PageParams.getWebDriverWait().until(titleContains("Online Store"));
        // ждем загрузки главной страницы
        return this;
    }

    @FindBy(css="li.product")
    public List<WebElement> Css_li_product_Elements;

    @FindBy(css="li.shortcut")
    public List<WebElement> Css_li_shortcut_Elements;


    public String getCss_div_name_Text(WebElement productUnit){
        return productUnit.findElement(By.cssSelector("div.name")).getText();
    }

    public void waitUntilProdNameStr(String prodNameStr)
    {
        PageParams.getWebDriverWait().until(titleContains(prodNameStr)); // ждем загрузки страницы продукта
    }

    public WebElement getPresenceOfElementLocatedById_cart() {
        return PageParams.getWebDriverWait().until(presenceOfElementLocated(By.id("cart"))); // нашли корзину
    }

    public void waitUntil_Checkout()
    {
        PageParams.getWebDriverWait().until(titleContains("Checkout"));// ожидаем открытия страницы корзины
    }

    public void waitUntil_textToBePresentInElement_Css_span_quantity(WebElement cart, int ii) {
        PageParams.getWebDriverWait().until(textToBePresentInElement(cart.findElement(By.cssSelector("span.quantity")),
                                                                     Integer.toString(ii))
                                           );
    }

    public WebElement getPresenceOfElementLocatedById_order_conf_wrapper() {
        return PageParams.getWebDriverWait().until(presenceOfElementLocated(By.id("order_confirmation-wrapper")));
        // находим таблицу товаров в корзине
    }

    public void waitUntilStalenessOfProdTable(WebElement prodTable)
    {
        PageParams.getWebDriverWait().until(stalenessOf(prodTable));
    }


    @FindBy(name="options[Size]")
    public WebElement  byName_optionsSize;

    @FindBy(name="add_cart_product")
    public WebElement  byName_add_cart_product;

    @FindBy(id="cart")
    public WebElement  byId_cart;

    @FindBy(name="remove_cart_item")
    public WebElement  byName_remove_cart_item;
}

