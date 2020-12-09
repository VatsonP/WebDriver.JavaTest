package ru.stqa.training.selenium.testapp;

import org.openqa.selenium.WebElement;
import ru.stqa.training.selenium.driverbase.DriverBase;
import ru.stqa.training.selenium.pages.PageParams;
import ru.stqa.training.selenium.pages.AdminPanelLoginPage;
import ru.stqa.training.selenium.pages.AdminLeftMenuPage;

import java.util.List;
import java.util.Random;
import java.util.Set;


public class UT3CheckNewTabsApp {

    private int countryQuantity, linksQuantity; // количество стран в списке
    private int randomIndex;
    private WebElement countryRow;  // строка по стране
    private List<WebElement> countryRows, listLinks;  // список стран, список внешних ссылок
    private String originalWindow, newWindow;
    private Set<String> existingWindows;

    private static final long sleepTimeMsek        = 1000;

    private PageParams pageParams;
    private AdminPanelLoginPage adminPanelLoginPage;
    private AdminLeftMenuPage   adminLeftMenuPage;

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


    public void myCheckNewTabs() {

        LoginAs("admin", "admin");//открыть страницу и выполнить коннект под пользователем

        //открыть страницу со списком стран
        adminLeftMenuPage.OpenCountries();
        adminLeftMenuPage.waitUntilCountries(); // ждем загрузки страницы

        //определение списка строк в таблице стран
        countryRows = adminLeftMenuPage.Css_countries_row_Elements;

        // сохраняем количество строк, т.е. стран в списке
        countryQuantity=countryRows.size();

        final Random random = new Random();

        // выбираем номер случайно страны из списка
        randomIndex = random.nextInt(countryQuantity-1);

        countryRow=countryRows.get(randomIndex);  // выбираем случайную страну
        adminLeftMenuPage.getCss_a_Elements(countryRow).click();

        // открываем страницу выбранной страны
        adminLeftMenuPage.waitUntilEditCountry();// ждем загрузки страницы

        listLinks = adminLeftMenuPage.Css_form_fa_external_link_Elements;
        // получаем список внешних ссылок

        linksQuantity = listLinks.size();  // определяем количество ссылок

        for (int i=0; i<linksQuantity; i++) {

            originalWindow= adminLeftMenuPage.getCurrentWindowHandle();
            // сохранили идентификатор текущего окна
            existingWindows= adminLeftMenuPage.getWindowHandles();
            // сохранили идентификаторы уже открытых окон

            listLinks.get(i).click(); // кликаем по ссылке из найденного списка

            newWindow= adminLeftMenuPage.waitUntilEditCountry(existingWindows);
            // получаем идентификатор нового окна

            adminLeftMenuPage.SwitchToWindow(newWindow);  // переключаемся в новое окно

            pageParams.ThreadSleep(sleepTimeMsek);

            adminLeftMenuPage.CloseCurWindow();  // закрываем окно

            adminLeftMenuPage.SwitchToWindow(originalWindow); // вернулись в исходное окно
        }

    }
}


