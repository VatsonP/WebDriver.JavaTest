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
        super(new DriverBaseParams());
        setApp(new FirstDemoApp() );
    }


    @Test
    public void my1stFirstDemo() {
        getApp().initPages(getDrvBase());

        getApp().FirstTest();
    }

    @Test
    public void my2ndTest() {
        getApp().initPages(getDrvBase());

        getApp().SecondTest();
    }

    @Test
    public void my3rdTest() {
        getApp().initPages(getDrvBase());

        getApp().ThirdTest();
    }

    @Test
    public void my4thTest() {
        getApp().initPages(getDrvBase());

        getApp().FourthTest();
    }

    @Test
    public void my5thTest() {
        getApp().initPages(getDrvBase());

        getApp().FifthTest();
    }

    @Test
    public void my6thTest() {
        getApp().initPages(getDrvBase());

        getApp().SixthTest_myCheckStiker();
    }

}
