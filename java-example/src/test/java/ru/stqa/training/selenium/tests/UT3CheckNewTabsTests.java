package ru.stqa.training.selenium.tests;

import org.junit.*;
import ru.stqa.training.selenium.driverbase.DriverBaseParams;
import ru.stqa.training.selenium.testbase.TestBase;
import ru.stqa.training.selenium.testapp.UT3CheckNewTabsApp;



public class UT3CheckNewTabsTests extends TestBase {

    private UT3CheckNewTabsApp ut3CheckNewTabsApp;

    private UT3CheckNewTabsApp getApp() {
        return ut3CheckNewTabsApp;
    }
    private void setApp(UT3CheckNewTabsApp newUT3CheckNewTabsApp) {
        ut3CheckNewTabsApp =  newUT3CheckNewTabsApp;
    }

    public UT3CheckNewTabsTests()
    {
        super(DriverBaseParams.CreateDriverBaseParams());
        setApp(new UT3CheckNewTabsApp() );
    }


    @Test
    public void TestMyCheckNewTabs() {
        getApp().initPages(getDrvBase());

        getApp().myCheckNewTabs();
    }

}
