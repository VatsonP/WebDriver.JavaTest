package ru.stqa.training.selenium.tests;

import org.junit.Test;
import ru.stqa.training.selenium.driverbase.DriverBaseParams;
import ru.stqa.training.selenium.testapp.UT8CheckProductApp;
import ru.stqa.training.selenium.testbase.TestBase;



public class UT8CheckProductTests extends TestBase {

    private static final long sleepTimeMSec = 1000;

    private UT8CheckProductApp ut8CheckProductApp;

    private UT8CheckProductApp getApp() {
        return ut8CheckProductApp;
    }
    private void setApp(UT8CheckProductApp newUT8CheckProductApp) {
        ut8CheckProductApp =  newUT8CheckProductApp;
    }

    public UT8CheckProductTests()
    {
        super(new DriverBaseParams(3,5));
        setApp(new UT8CheckProductApp(sleepTimeMSec) );
    }


    @Test
    public void TestMyCheckProduct() {
        getApp().initPages(getDrvBase());

        getApp().myCheckProduct();
    }

}
