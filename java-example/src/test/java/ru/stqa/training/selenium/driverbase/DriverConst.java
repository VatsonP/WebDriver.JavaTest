package ru.stqa.training.selenium.driverbase;

import ru.stqa.training.selenium.driverbase.DriverDefault;

public abstract class DriverConst {
    // константа времени (в сек) общих неявных ожиданий
    public static final int drvImplWaitTime = 5;
    // константа времени (в сек) для явных ожиданий
    public static final int drvExplWaitTime = 10;
    // константа времени (в сек) для максимального времени неявного ожидания
    public  static final int drvMaxWaitTime  = 15;

    public static final String localHostStr = "localhost";
    // Local Windows Ip
    public  static final String localIpStr_Win = "192.168.0.101";
    // Remote WinServer2019 with Docker "192.168.0.91"
    public  static final String remoteIpStr_WinServer2019 = "192.168.0.91";
    // Remote Ubuntu 20.4   with Docker "192.168.203.128"
    public  static final String remoteIpStr_Ubuntu_20_4 = "192.168.0.96";

    public static final String chromeDriverExeName = "chromedriver.exe";
    public static final String firefoxDriverExeName = "geckodriver.exe";
    public static final String ieDriverExeName = "IEDriverServer.exe";
    public static final String selenoidDriverExeName = "selenoid.exe";
    public static final String selenoidBatFilePathName = "C:\\Tools\\selenoid.bat";
    public static final String selenoidBatFileWorkingDirectory = "C:\\Tools\\";
}
