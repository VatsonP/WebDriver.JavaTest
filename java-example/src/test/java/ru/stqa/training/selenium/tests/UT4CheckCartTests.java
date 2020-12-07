package ru.stqa.training.selenium.tests;

import org.junit.Test;
import org.openqa.selenium.WebElement;
import ru.stqa.training.selenium.driverbase.DriverBaseParams;
import ru.stqa.training.selenium.testapp.UT4CheckCartApp;
import ru.stqa.training.selenium.testbase.TestBase;



public class UT4CheckCartTests extends TestBase {


    private UT4CheckCartApp ut4CheckCartApp;

    private UT4CheckCartApp getApp() {
        return ut4CheckCartApp;
    }
    private void setApp(UT4CheckCartApp newUT4CheckCartApp) {
        ut4CheckCartApp =  newUT4CheckCartApp;
    }

    public UT4CheckCartTests()
    {
        super(new DriverBaseParams());
        setApp(new UT4CheckCartApp() );
    }


    @Test
    public void TestMyLeftMenuClick() {
        getApp().initPages(getDrvBase());

        getApp().myCheckCart();
    }

}
