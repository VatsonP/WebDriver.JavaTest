package ru.stqa.training.selenium.tests;


import com.tngtech.java.junit.dataprovider.DataProvider;
import ru.stqa.training.selenium.model.Customer;

import java.util.Calendar;

public class DataProviders {

    @DataProvider
    public static Object[][] validCustomers() {
        String FirstName, LastName, eMailName, testString, taxId;
        // читаем текущее время - добавляем его к фамилии и имеем уникальный e-mail и пароль каждый раз
        Calendar calendar = Calendar.getInstance();
        int yyyy=calendar.get(calendar.YEAR);
        int mm=calendar.get(calendar.MONTH);
        int dd=calendar.get(calendar.DAY_OF_MONTH);
        int h=calendar.get(calendar.HOUR_OF_DAY);
        int m=calendar.get(calendar.MINUTE);
        int s=calendar.get(calendar.SECOND);

        FirstName="Adam";
        LastName="Smith";
        eMailName=FirstName + PaddingLeft(h) + PaddingLeft(m) + PaddingLeft(s) + "@gmail.com";

        taxId = PaddingLeft(yyyy, 4) + "-" + PaddingLeft(mm) + "-" + PaddingLeft(dd) + "_" +
                PaddingLeft(h) + PaddingLeft(m) + PaddingLeft(s);


        return new Object[][] {
                { Customer.newEntity()
                        .withTax_id(taxId).withCompany("MMM")
                        .withFirstname(FirstName).withLastname(LastName)
                        .withAddress1("Hidden Place").withPostcode("12345").withCity("Kyiv")
                        .withCountry("UA").withZone("")
                        .withEmail(eMailName).withPhone("+0123456789")
                        .withPassword("qwerty").withConfirmed_password("qwerty").build() },
                // ... //
        };
    }

    private static String PaddingLeft(int intS)
    {
        return PaddingLeft(intS, 2);
    }

    private static String PaddingLeft(int intS, int totalWidth)
    {
        return PaddingLeft(intS, totalWidth, '0');
    }

    private static String PaddingLeft(int intS, int totalWidth, char paddingChar)
    {
        return String.format("%" + paddingChar + totalWidth + "d", intS);
    }

}

