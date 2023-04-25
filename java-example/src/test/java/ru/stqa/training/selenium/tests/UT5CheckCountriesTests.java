package ru.stqa.training.selenium.tests;

import org.junit.Test;
import ru.stqa.training.selenium.driverbase.DriverBaseParams;
import ru.stqa.training.selenium.testapp.UT5CheckCountriesApp;
import ru.stqa.training.selenium.testbase.TestBase;



public class UT5CheckCountriesTests extends TestBase {

    private UT5CheckCountriesApp ut5CheckCountriesApp;

    private UT5CheckCountriesApp getApp() {
        return ut5CheckCountriesApp;
    }
    private void setApp(UT5CheckCountriesApp newUT5CheckCountriesApp) {
        ut5CheckCountriesApp =  newUT5CheckCountriesApp;
    }

    public UT5CheckCountriesTests()
    {
        super(DriverBaseParams.CreateDriverBaseParams());
        setApp(new UT5CheckCountriesApp() );
    }

    @Test
    public void TestMyCheckCountries() {
        getApp().initPages(getDrvBase());

        getApp().MyCheckCountries();
    }

}
