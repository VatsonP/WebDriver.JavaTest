package ru.stqa.training.selenium.testapp;

import org.openqa.selenium.WebElement;
import ru.stqa.training.selenium.driverbase.DriverBase;
import ru.stqa.training.selenium.pages.PageParams;
import ru.stqa.training.selenium.pages.AdminPanelLoginPage;
import ru.stqa.training.selenium.pages.AdminLeftMenuPage;

import java.util.List;


import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;


public class UT2LeftMenuClickApp {

    private long sleepTimeMenuMSec;
    private long sleepTimeSubmenuMSec;
    private long sleepTimeMSec;

    private List<WebElement> menuPoints;
    private List<WebElement> submenuPoints;
    private WebElement       menuPoint, submenuPoint;
    private int menuQuantity, submenuQuantity;

    private boolean isDisplayed_h1 = false;

    private PageParams pageParams;
    private AdminPanelLoginPage adminPanelLoginPage;
    private AdminLeftMenuPage adminLeftMenuPage;

    public UT2LeftMenuClickApp(long sleepTimeMenuMSec, long sleepTimeSubmenuMSec, long sleepTimeMSec)
    {
        this.sleepTimeMenuMSec = sleepTimeMenuMSec;
        this.sleepTimeSubmenuMSec = sleepTimeSubmenuMSec;
        this.sleepTimeMSec = sleepTimeMSec;

    }

    public void initPages(DriverBase drvBase) {

        pageParams = new PageParams(drvBase);

        adminPanelLoginPage = new AdminPanelLoginPage(pageParams);
        adminLeftMenuPage   = new AdminLeftMenuPage(pageParams);
    }

    private void LoginAs(String usrText, String passText) {
        if (adminPanelLoginPage.open().isOnThisPage()) {
            adminPanelLoginPage.enterUsername(usrText).enterPassword(passText).submitLogin();
        }

        adminLeftMenuPage.waitUntilMyStore(); //подождать пока не загрузится страница с заголовком "My Store"
    }

    public void MyLeftMenuClick() {

        LoginAs("admin", "admin");//открыть страницу и выполнить коннект под пользователем

        menuPoints = adminLeftMenuPage.id_app_Elements; //pageParams.getWebDriver().findElements(By.id("app-"));

        // определение списка пунктов меню
        menuQuantity = menuPoints.size(); // сохраняем количество пунктов меню

        for (int i = 0; i < menuQuantity; i++  ) {  //проходим по пунктам меню
            menuPoints = adminLeftMenuPage.id_app_Elements; //pageParams.getWebDriver().findElements(By.id("app-"));
            menuPoint  = menuPoints.get(i); //выбираем пункт меню

            menuPoint.click();  // кликаем по меню

            menuPoints = adminLeftMenuPage.id_app_Elements; //pageParams.getWebDriver().findElements(By.id("app-")); //обновляем список
            menuPoint = menuPoints.get(i); //выбираем пункт меню
            // определение списка пунктов подменю
            submenuPoints = adminLeftMenuPage.getCss_menu_id_doc_Elements(menuPoint); //menuPoint.findElements(By.cssSelector("[id^=doc-]"));
            submenuQuantity = submenuPoints.size(); // сохраняем количество пунктов подменю

            pageParams.ThreadSleep(sleepTimeMenuMSec);

            if(submenuQuantity > 0) { //подменю есть
                for (int j=0;j<submenuQuantity;j++) {
                    menuPoints = adminLeftMenuPage.id_app_Elements; //pageParams.getWebDriver().findElements(By.id("app-"));  //обновляем список
                    menuPoint = menuPoints.get(i); //выбираем пункт меню
                    // определение списка пунктов подменю
                    submenuPoints = adminLeftMenuPage.getCss_menu_id_doc_Elements(menuPoint);//menuPoint.findElements(By.cssSelector("[id^=doc-]"));
                    submenuPoint = submenuPoints.get(j); //выбираем пункт подменю

                    pageParams.clickElement(submenuPoint);  //кликаем по подменю

                    isDisplayed_h1 = adminLeftMenuPage.css_h1_Element.isDisplayed(); //pageParams.getWebDriver().findElement(By.cssSelector("h1"));  //проверка наличия заголовка

                    pageParams.ThreadSleep(sleepTimeMenuMSec);
                }
            } else {   // подменю нет
                isDisplayed_h1 = adminLeftMenuPage.css_h1_Element.isDisplayed(); //pageParams.getWebDriver().findElement(By.cssSelector("h1"));  //проверка наличия заголовка
            }
        }

        pageParams.ThreadSleep(sleepTimeMenuMSec);
    }
}


