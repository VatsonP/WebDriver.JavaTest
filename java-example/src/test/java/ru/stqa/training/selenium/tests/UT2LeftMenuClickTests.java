package ru.stqa.training.selenium.tests;

import org.junit.*;
import ru.stqa.training.selenium.driverbase.DriverBaseParams;
import ru.stqa.training.selenium.testbase.TestBase;
import ru.stqa.training.selenium.testapp.UT2LeftMenuClickApp;



public class UT2LeftMenuClickTests extends TestBase {

    private final long sleepTimeMenuMSec = 100;
    private final long sleepTimeSubmenuMSec = 200;
    private final long sleepTimeMSec = 300;

    private UT2LeftMenuClickApp ut2LeftMenuClickApp;

    private UT2LeftMenuClickApp getApp() {
        return ut2LeftMenuClickApp;
    }
    private void setApp(UT2LeftMenuClickApp newUT2LeftMenuClickApp) {
        ut2LeftMenuClickApp =  newUT2LeftMenuClickApp;
    }

    public UT2LeftMenuClickTests()
    {
        super(DriverBaseParams.CreateDriverBaseParams(3, 5));

        setApp(new UT2LeftMenuClickApp(sleepTimeMenuMSec,
                                       sleepTimeSubmenuMSec,
                                       sleepTimeMSec
                                      )
              );
    }


    @Test
    public void TestMyLeftMenuClick() {
        getApp().initPages(getDrvBase());

        getApp().MyLeftMenuClick();
    }

}
