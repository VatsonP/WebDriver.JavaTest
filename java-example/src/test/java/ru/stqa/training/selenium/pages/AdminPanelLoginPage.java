package ru.stqa.training.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AdminPanelLoginPage extends Page {

    public AdminPanelLoginPage(PageParams pageParams) {
        super(pageParams);
    }

    public AdminPanelLoginPage open() {
        pageParams.getWebDriver().get("http://" + pageParams.getCurrentIpStr() + ":8080/litecart/admin/");
        return this;
    }

    public boolean isOnThisPage() {
        return pageParams.getWebDriver().findElements(By.id("box-login")).size() > 0;
    }

    public AdminPanelLoginPage enterUsername(String username) {
        pageParams.getWebDriver().findElement(By.name("username")).sendKeys(username);
        return this;
    }

    public AdminPanelLoginPage enterPassword(String password) {
        pageParams.getWebDriver().findElement(By.name("password")).sendKeys(password);
        return this;
    }

    public void submitLogin() {
        pageParams.getWebDriver().findElement(By.name("login")).click();
        pageParams.getWebDriverWait().until((WebDriver d) -> d.findElement(By.id("box-apps-menu")));
    }

}
