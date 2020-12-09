package ru.stqa.training.selenium.tests;

import org.junit.*;
import ru.stqa.training.selenium.driverbase.DriverBase;
import ru.stqa.training.selenium.driverbase.DriverBaseParams;
import ru.stqa.training.selenium.testapp.FirstDemoApp;
import ru.stqa.training.selenium.testbase.TestBase;

import java.lang.Thread;
import java.util.List;


import static org.openqa.selenium.support.ui.ExpectedConditions.titleContains;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;


public class FirstDemoTests extends TestBase {

    private static final long sleepTimeMsek = 2000;

    private FirstDemoApp firstDemoApp;

    private FirstDemoApp getApp() {
        return firstDemoApp;
    }
    private void setApp(FirstDemoApp newFirstDemoApp) {
        firstDemoApp =  newFirstDemoApp;
    }

    public FirstDemoTests()
    {
        super(new DriverBaseParams());
        setApp(new FirstDemoApp() );
    }


    @Test
    public void myFirstDemo() {
        getApp().initPages(getDrvBase());

        getApp().FirstTest();

        getApp().SecondTest();

        getApp().ThirdTest();

        getApp().FourthTest();

        getApp().FifthTest();

        getApp().SixthTest_myCheckStiker();
    }

}
