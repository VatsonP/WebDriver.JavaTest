package ru.stqa.training.selenium.testbase;

import org.junit.*;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import ru.stqa.training.selenium.driverbase.DriverBase;
import ru.stqa.training.selenium.driverbase.DriverBaseReal;
import ru.stqa.training.selenium.driverbase.DriverBaseParams;

import java.net.MalformedURLException;


public abstract class TestBase
{
    private   DriverBase drvBase;
    protected DriverBase getDrvBase() { return drvBase; }

    public TestBase(DriverBaseParams driverBaseParams) {
        drvBase = new DriverBaseReal(driverBaseParams);
    }

    @Rule
    public TestRule watcher = getWatcher();

    public TestRule getWatcher() {
        return new TestWatcher() {
            protected void starting(Description description) {
                watcherStarting(description);
            }
            protected void finished(Description description) {
                watcherFinished(description);
            }
        };
    }

    protected void watcherStarting (Description description) { drvBase.watcherStarting(description); }
    protected void watcherFinished (Description description) {
        drvBase.watcherFinished(description);
    }


    @BeforeClass
    public static void startBeforeClass() {
        DriverBase.startBeforeClass();
    }

    @Before
    public void startBefore() throws MalformedURLException {
        drvBase.startBefore();
    }

    @After
    public void stopAfter() {
        drvBase.stopAfter();
    }

    @AfterClass
    public static void stopAfterClass() {
        DriverBase.stopAfterClass();
    }

}