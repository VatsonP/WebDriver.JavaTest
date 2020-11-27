package ru.stqa.training.selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Page {

    protected static PageParams pageParams;

    public Page(PageParams pageParams) {
        this.pageParams  = pageParams;
    }

}
