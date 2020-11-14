package ru.stqa.training.selenium;


import org.junit.*;
import org.openqa.selenium.By;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.stqa.training.selenium.driverbase.DriverBase;
import ru.stqa.training.selenium.driverbase.DriverBaseParams;

import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;


public class CheckCountries extends DriverBase {

    private int countryQuantity, zoneQuantity, geoZoneQuantity; // количество стран в списке, зон в списке
    private int[] zones;  // массив количества зон для списка стран
    private int a, az;
    private WebElement countryRow, zoneRow, geoZoneRow;  // строка по стране и по зоне
    private List<WebElement> countryRows, zoneRows, geoZoneRows;  // список стран, список зон
    private String[] countryName, zoneName;  // имена стран, имена зон

    public CheckCountries() { super (new DriverBaseParams()); }

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

    @Test
    public void myCheckCountries() {
        driver.get("http://" + getCurrentIpStr() + ":8080/litecart/admin/"); //открыть страницу
        driver.findElement(By.name("username")).sendKeys("admin");
        //найти поле для ввода логина и ввести "admin"
        driver.findElement(By.name("password")).sendKeys("admin");
        //найти поле для ввода пароля и ввести "admin"
        driver.findElement(By.name("login")).click();
        //найти кнопку логина и нажать на нее
        wait.until(titleIs("My Store"));
        //подождать пока не загрузится страница с заголовком "My Store"

        //открыть страницу со списком стран
        driver.get("http://" + getCurrentIpStr() + ":8080/litecart/admin/?app=countries&doc=countries");


        //определение списка строк в таблице стран
        countryRows = driver.findElements(By.cssSelector("[name=countries_form] .row"));

        // сохраняем количество строк, т.е. стран в списке
        countryQuantity=countryRows.size();
        // массив строк с названиями стран
        countryName = new String[countryQuantity];
        zones = new int[countryQuantity];

        for (int i=0;i<countryQuantity;i++) {
            // создание массива со списком названий стран
            countryRow=countryRows.get(i);
            countryName[i] = countryRow.findElement(By.cssSelector("a")).getText();
            zones[i]=Integer.parseInt(countryRow.findElement(By.cssSelector("td:nth-child(6)")).getText());
        }

        a = testAlphabet(countryName,countryQuantity);

        Assert.assertTrue(a==1);
        // проверка алфавитного порядка - если он нарушен, тест провалится

        for (int i=0;i<countryQuantity;i++) {
            // проверка стран с ненулевым количеством
            if (zones[i]>0) {
                // опять определяем таблицу стран
                countryRows = driver.findElements(By.cssSelector("[name=countries_form] .row"));
                // получаем строку с заданным индексом
                countryRow=countryRows.get(i);
                // находим ссылку с названием страны и кликаем по ней
                countryRow.findElement(By.cssSelector("a")).click();
                // даем время на загрузку
                wait = new WebDriverWait(driver,10);

                // получаем список строк в таблице зон
                zoneRows = driver.findElements(By.cssSelector("[id=table-zones] tr"));
                // количество строк с учетом строки заголовка и служебной снизу
                zoneQuantity = zoneRows.size() - 2;
                // массив с названиями зон
                zoneName = new String[zoneQuantity];

                for (int j=1;j<=zoneQuantity;j++) {
                    // создание массива со списком названий зон
                    zoneRow=zoneRows.get(j);
                    zoneName[j-1]=zoneRow.findElement(By.cssSelector("td:nth-child(3)")).getText();
                }
                az = testAlphabet(zoneName,zoneQuantity);
                Assert.assertTrue(az==1);
                // проверка алфавитного порядка перечня зон

                /* опять возвращаемся на страницу со списком стран, поскольку
                 при проверке списка зон мы зашли на страницу отдельной страны,
                 и если не вернуться к списку стран, то мы не сможем проверить список
                 зон у другой страны, у которой число зон больше 0 */
                driver.get("http://" + getCurrentIpStr() + ":8080/litecart/admin/?app=countries&doc=countries");
            }
        }

        // открываем страницу просмотра географических зон
        driver.get("http://" + getCurrentIpStr() + ":8080/litecart/admin/?app=geo_zones&doc=geo_zones");

        // создаем список строк в таблице зон
        geoZoneRows = driver.findElements(By.cssSelector("[name=geo_zones_form] .row"));
        geoZoneQuantity = geoZoneRows.size(); // количество строк в списке

        for (int i=0; i<geoZoneQuantity; i++) {
            geoZoneRows = driver.findElements(By.cssSelector("[name=geo_zones_form] .row"));
            geoZoneRow = geoZoneRows.get(i);  // получаем строку из списка
            // находим ссылку с названием страны и кликаем по ней
            geoZoneRow.findElement(By.cssSelector("a")).click();
            // даем время на загрузку
            wait = new WebDriverWait(driver,10);

            // получаем список строк в таблице зон
            zoneRows = driver.findElements(By.cssSelector("[id=table-zones] tr"));
            // количество строк с учетом строки заголовка и служебной снизу
            zoneQuantity = zoneRows.size() - 2;
            // массив с названиями зон
            zoneName = new String[zoneQuantity];

            for (int j=1;j<=zoneQuantity;j++) {
                // создание массива со списком названий зон
                zoneRow=zoneRows.get(j);
                zoneName[j-1] = zoneRow.findElement(
                        By.cssSelector("[id=table-zones] tr td:nth-child(3) [selected=selected]")).
                        getAttribute("textContent");
            }
            az = testAlphabet(zoneName,zoneQuantity);
            Assert.assertTrue(az==1);
            // возврат на страницу просмотра географических зон
            driver.get("http://" + getCurrentIpStr() + ":8080/litecart/admin/?app=geo_zones&doc=geo_zones");
        }
    }

}
