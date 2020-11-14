package ru.stqa.training.selenium.tests;

import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import ru.stqa.training.selenium.driverbase.DriverBaseParams;
import ru.stqa.training.selenium.testbase.TestBase;
import ru.stqa.training.selenium.model.Customer;
import ru.stqa.training.selenium.testapp.UT1CustomerRegistrationApp;

import java.util.Set;


import static org.junit.Assert.assertTrue;


@RunWith(DataProviderRunner.class)
public class UT1CustomerRegistrationTests extends TestBase {

    private UT1CustomerRegistrationApp utT1CustomerRegistrationApp;

    protected UT1CustomerRegistrationApp getApp() {
        return utT1CustomerRegistrationApp;
    }
    private void setApp(UT1CustomerRegistrationApp newUTT1CustomerRegistrationApp) {
        utT1CustomerRegistrationApp =  newUTT1CustomerRegistrationApp;
    }

    public UT1CustomerRegistrationTests()
    {
        super(new DriverBaseParams());
        setApp(new UT1CustomerRegistrationApp());
    }


    @Test
    @UseDataProvider(value = "validCustomers", location = DataProviders.class)
    public void canRegisterCustomer(Customer customer) {
        getApp().initPages(getDrvBase());

        Set<String> oldIds = getApp().getCustomerIds();

        getApp().registerNewCustomer(customer);

        Set<String> newIds = getApp().getCustomerIds();

        assertTrue(newIds.containsAll(oldIds));
        assertTrue(newIds.size() == oldIds.size() + 1);
    }

}
