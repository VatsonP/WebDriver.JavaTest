package ru.stqa.training.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class RegistrationPage extends Page {

    private static final long sleepTimeMsek = 6000;

    public RegistrationPage(PageParams pageParams) {
        super(pageParams);
        PageFactory.initElements(pageParams.getWebDriver(), this);
    }

    public void open() {
        pageParams.getWebDriver().get("http://" + pageParams.getCurrentIpStr() +":8080/litecart/en/create_account");
    }

    @FindBy(name="tax_id")
    public WebElement tax_idInput;

    @FindBy(name="company")
    public WebElement companyInput;

    @FindBy(name="firstname")
    public WebElement firstnameInput;

    @FindBy(name="lastname")
    public WebElement lastnameInput;

    @FindBy(name="address1")
    public WebElement address1Input;

    @FindBy(name="postcode")
    public WebElement postcodeInput;

    @FindBy(name="city")
    public WebElement cityInput;

    @FindBy(name="email")
    public WebElement emailInput;

    @FindBy(name="phone")
    public WebElement phoneInput;

    @FindBy(name="password")
    public WebElement passwordInput;

    @FindBy(name="confirmed_password")
    public WebElement confirmedPasswordInput;

    @FindBy(name="create_account")
    public WebElement createAccountButton;

    public void selectCountry(String country) {
        pageParams.getWebDriver().findElement(By.cssSelector("[id ^= select2-country_code]")).click();
        pageParams.getWebDriver().findElement(By.cssSelector(
                String.format(".select2-results__option[id $= %s]", country))).click();
    }

    public void selectZone(String zone) {
        pageParams.getWebDriverWait().until((WebDriver d) -> d.findElement(
                By.cssSelector(String.format("select[name=zone_code] option[value=%s]", zone))));
        new Select(pageParams.getWebDriver().findElement(By.name("select[name=zone_code]"))).selectByValue(zone);
    }

    public void DriverSleep()
    {
        try {
            Thread.sleep(sleepTimeMsek);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

}

