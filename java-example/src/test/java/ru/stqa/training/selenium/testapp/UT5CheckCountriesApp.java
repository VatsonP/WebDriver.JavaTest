package ru.stqa.training.selenium.testapp;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.stqa.training.selenium.driverbase.DriverBase;
import ru.stqa.training.selenium.pages.PageParams;
import ru.stqa.training.selenium.pages.AdminPanelLoginPage;
import ru.stqa.training.selenium.pages.AdminLeftMenuPage;

import java.util.List;


public class UT5CheckCountriesApp {

    private PageParams          pageParams;
    private AdminPanelLoginPage adminPanelLoginPage;
    private AdminLeftMenuPage   adminLeftMenuPage;

    // количество стран в списке, зон в списке
    private int countryQuantity, zoneQuantity, geoZoneQuantity;
    // массив количества зон для списка стран
    private int[] zones;
    private int a, az;
    // строка по стране и по зоне
    private WebElement countryRow, zoneRow, geoZoneRow;
    // список стран, список зон
    private List<WebElement> countryRows, zoneRows, geoZoneRows;
    // имена стран, имена зон
    private String[] countryName, zoneName;


    // функция проверки, что строки в массиве идут по алфавиту
    public static int testAlphabet (String[] testArr, int arrSize) {
        int isAlphab=1;  // начальное знчение признака - алфавитный порядок
        for (int i=1; i<arrSize;i++) { // перебираем строковый массив
            int k; // переменная для результата сравнения строк
            k=testArr[i-1].compareToIgnoreCase(testArr[i]);
            if(k>=0) isAlphab=-1; // алфавитный порядок нарушен - меняем признак
        }
        return isAlphab;
    }

    public void initPages(DriverBase drvBase) {

        pageParams = new PageParams(drvBase);

        adminPanelLoginPage = new AdminPanelLoginPage(pageParams);
        adminLeftMenuPage   = new AdminLeftMenuPage(pageParams);
    }

    private void LoginAs(String usrText, String passText)
    {
        if (adminPanelLoginPage.open().isOnThisPage())
        {
            adminPanelLoginPage.enterUsername(usrText).enterPassword(passText).submitLogin();
        }

        adminLeftMenuPage.waitUntilMyStore(); //подождать пока не загрузится страница с заголовком "My Store"
    }


    public void MyCheckCountries() {

        //открыть страницу и выполнить коннект под пользователем + ждем страницу "My Store"
        LoginAs("admin", "admin");

        //открыть страницу со списком стран
        adminLeftMenuPage.openCountries().waitUntilCountries(); // и ждем загрузки страницы

        //определение списка строк в таблице стран
        countryRows = adminLeftMenuPage.Css_countries_row_Elements;

        // сохраняем количество строк, т.е. стран в списке
        countryQuantity = countryRows.size();
        // массив строк с названиями стран
        countryName = new String[countryQuantity];
        zones = new int[countryQuantity];

        for (int i=0; i<countryQuantity; i++) {
            // создание массива со списком названий стран
            countryRow = countryRows.get(i);
            countryName[i] = countryRow.findElement(By.cssSelector("a")).getText();
            zones[i] = Integer.parseInt(adminLeftMenuPage.CountryRow_Css_td_nth_child6_Element(countryRow).getText());
        }

        a = testAlphabet(countryName,countryQuantity);

        Assert.assertTrue(a==1); // проверка алфавитного порядка - если он нарушен, тест провалится

        for (int i=0; i<countryQuantity; i++) {
            // проверка стран с ненулевым количеством
            if (zones[i] > 0) {
                // опять определяем таблицу стран
                countryRows = adminLeftMenuPage.Css_countries_row_Elements;
                // получаем строку с заданным индексом
                countryRow = countryRows.get(i);
                // находим ссылку с названием страны и кликаем по ней
                adminLeftMenuPage.Row_Css_a_Element(countryRow).click();

                // даем время на загрузку
                //wait = new WebDriverWait(driver,10);

                // получаем список строк в таблице зон
                zoneRows = adminLeftMenuPage.Css_id_table_zones_tr_Elements;
                // количество строк с учетом строки заголовка и служебной снизу
                zoneQuantity = zoneRows.size() - 2;
                // массив с названиями зон
                zoneName = new String[zoneQuantity];

                for (int j=1; j<=zoneQuantity; j++) {
                    // создание массива со списком названий зон
                    zoneRow = zoneRows.get(j);
                    zoneName[j-1] = adminLeftMenuPage.ZoneRow_Css_td_nth_child3_Element(zoneRow).getText();
                }

                az = testAlphabet(zoneName,zoneQuantity);
                Assert.assertTrue(az==1);
                // проверка алфавитного порядка перечня зон

                // опять возвращаемся на страницу со списком стран, поскольку
                // при проверке списка зон мы зашли на страницу отдельной страны,
                // и если не вернуться к списку стран, то мы не сможем проверить список
                // зон у другой страны, у которой число зон больше 0
                adminLeftMenuPage.openCountries().waitUntilCountries(); // и ждем загрузки страницы
            }
        }

        // открываем страницу просмотра географических зон
        adminLeftMenuPage.OpenGeoZones();

        // создаем список строк в таблице зон
        geoZoneRows = adminLeftMenuPage.Css_geo_zones_row_Elements;
        geoZoneQuantity = geoZoneRows.size(); // количество строк в списке

        for (int i=0; i<geoZoneQuantity; i++) {
            geoZoneRows = adminLeftMenuPage.Css_geo_zones_row_Elements;
            geoZoneRow = geoZoneRows.get(i);  // получаем строку из списка
            // находим ссылку с названием страны и кликаем по ней
            geoZoneRow.findElement(By.cssSelector("a")).click();
            // даем время на загрузку
            //wait = new WebDriverWait(driver,10);

            // получаем список строк в таблице зон
            zoneRows = adminLeftMenuPage.Css_id_table_zones_tr_Elements;
            // количество строк с учетом строки заголовка и служебной снизу
            zoneQuantity = zoneRows.size() - 2;
            // массив с названиями зон
            zoneName = new String[zoneQuantity];

            for (int j = 1; j <= zoneQuantity; j++) {
                // создание массива со списком названий зон
                zoneRow = zoneRows.get(j);
                zoneName[j - 1] = adminLeftMenuPage.ZoneRow_Css_id_table_zones_td_nth_child3_Element(zoneRow)
                        .getAttribute("textContent");
            }

            az = testAlphabet(zoneName, zoneQuantity);
            Assert.assertTrue(az == 1);

            // возврат на страницу просмотра географических зон
            adminLeftMenuPage.OpenGeoZones();
        }
    }

}


