package ru.stqa.training.selenium.tests;

import org.junit.*;

import ru.stqa.training.selenium.driverbase.DriverBaseParams;
import ru.stqa.training.selenium.testapp.FirstDemoApp;
import ru.stqa.training.selenium.testbase.TestBase;



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
        super(DriverBaseParams.CreateDriverBaseParams() );
        setApp(new FirstDemoApp() );
    }

    @Before
    public void initBefore() {
        getApp().initPages(getDrvBase());
    }

    @Test
    public void my01Test() {
        getApp().FirstTest();
    }

    @Test
    public void my02Test() {
        getApp().SecondTest();
    }

    @Test
    public void my03Test() {
        getApp().ThirdTest();
    }

    @Test
    public void my04Test() {
        getApp().FourthTest();
    }

    @Test
    public void my05Test() {
        getApp().FifthTest();
    }

    @Test
    public void my06Test() {
        getApp().SixthTest_myCheckStiker();
    }

}
