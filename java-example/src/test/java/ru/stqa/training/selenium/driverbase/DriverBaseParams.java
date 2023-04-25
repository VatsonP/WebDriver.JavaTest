package ru.stqa.training.selenium.driverbase;


public class DriverBaseParams {

    // initial const value of TestRunType
    public static final DriverDefault.TestRunType defTestRunType = DriverDefault.TestRunType.RemoteUbuntu;
    // initial const value of WebDriverType
    public static final DriverDefault.WebDriverType defWebDriverType = DriverDefault.WebDriverType.Firefox;

    // переменная времени (в сек) общих неявных ожиданий
    private int drvImplWaitTime = DriverConst.drvImplWaitTime;
    public  int getImplWaitTime() { return drvImplWaitTime; }

    // переменная времени (в сек) для явных ожиданий
    private int drvExplWaitTime = DriverConst.drvExplWaitTime;
    public  int getExplWaitTime() { return drvExplWaitTime; }

    // переменная времени (в сек) для максимального времени неявного ожидания
    private int drvMaxWaitTime = DriverConst.drvMaxWaitTime;
    public  int getMaxWaitTime() { return drvMaxWaitTime; }

    // LocalHost
    private static final String localHostStr = DriverConst.localHostStr;
    public String getLocalHostStr() { return localHostStr; } // get LocalHost

    // Local Host Ip
    private String localIpStr = DriverConst.localIpStr_Win;
    public String getLocalIpStr() { return localIpStr; }   // get Local Host Ip

    // Remote Host Ip
    private String remoteIpStr = DriverConst.remoteIpStr_Ubuntu_20_4;
    public String getRemoteIpStr() { return remoteIpStr; }  // get Remote Host Ip

    private DriverBaseParams(int drvImplWaitTime, int drvExplWaitTime, int drvMaxWaitTime,
                            String localIpStr, String remoteIpStr)
    {
        this.drvImplWaitTime = drvImplWaitTime;
        this.drvExplWaitTime = drvExplWaitTime;
        this.drvMaxWaitTime = drvMaxWaitTime;
        this.localIpStr     = localIpStr;
        this.remoteIpStr    = remoteIpStr;
    }

    public static DriverBaseParams CreateDriverBaseParams() {
        int drvImplWaitTime = DriverConst.drvImplWaitTime;
        int drvExplWaitTime = DriverConst.drvExplWaitTime;
        int drvMaxWaitTime = DriverConst.drvMaxWaitTime;

        return CreateDriverBaseParams(drvImplWaitTime, drvExplWaitTime, drvMaxWaitTime);
    }

    public static DriverBaseParams CreateDriverBaseParams(int drvImplWaitTime)
    {
        int drvExplWaitTime = DriverConst.drvExplWaitTime;
        int drvMaxWaitTime = DriverConst.drvMaxWaitTime;

        return CreateDriverBaseParams(drvImplWaitTime, drvExplWaitTime, drvMaxWaitTime);
    }

    public static DriverBaseParams CreateDriverBaseParams(int drvImplWaitTime, int drvExplWaitTime)
    {
        int drvMaxWaitTime = DriverConst.drvMaxWaitTime;

        return CreateDriverBaseParams(drvImplWaitTime, drvExplWaitTime, drvMaxWaitTime);
    }

    public static DriverBaseParams CreateDriverBaseParams(int drvImplWaitTime, int drvExplWaitTime, int drvMaxWaitTime)
    {

        String      remoteIpStr = "";

        switch (defTestRunType)
        {
            case RemoteWin:
                remoteIpStr = DriverConst.remoteIpStr_WinServer2019;
                break;

            case RemoteUbuntu:
                remoteIpStr = DriverConst.remoteIpStr_Ubuntu_20_4;
                break;

            default:
                remoteIpStr = DriverConst.remoteIpStr_Ubuntu_20_4;
                break;
        }

        return new DriverBaseParams(drvImplWaitTime, drvExplWaitTime, drvMaxWaitTime, DriverConst.localIpStr_Win, remoteIpStr);
    }


}
