package ru.stqa.training.selenium.tests;

import org.junit.Test;
import ru.stqa.training.selenium.driverbase.DriverBaseParams;
import ru.stqa.training.selenium.testapp.UT7CheckNewProductApp;
import ru.stqa.training.selenium.testbase.TestBase;



public class UT7CheckNewProductTests extends TestBase {

    private static final long sleepTimeMSec = 500;

    private UT7CheckNewProductApp ut7CheckNewProductApp;

    private UT7CheckNewProductApp getApp() {
        return ut7CheckNewProductApp;
    }
    private void setApp(UT7CheckNewProductApp newUT7CheckNewProductApp) {
        ut7CheckNewProductApp =  newUT7CheckNewProductApp;
    }

    public UT7CheckNewProductTests()
    {
        super(DriverBaseParams.CreateDriverBaseParams());
        setApp(new UT7CheckNewProductApp(sleepTimeMSec) );
    }

    @Test
    public void TestMyCheckNewProduct() {
        getApp().initPages(getDrvBase());

        getApp().myCheckNewProduct();
    }

}
