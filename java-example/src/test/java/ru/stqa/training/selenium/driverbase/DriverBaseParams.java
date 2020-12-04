package ru.stqa.training.selenium.driverbase;


public class DriverBaseParams {

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


    public DriverBaseParams() {
    }

    public DriverBaseParams(int drvImplWaitTime)
    {
        this.drvImplWaitTime = drvImplWaitTime;
    }

    public DriverBaseParams(int drvImplWaitTime, int drvExplWaitTime)
    {
        this.drvImplWaitTime = drvImplWaitTime;
        this.drvExplWaitTime = drvExplWaitTime;
    }

    public DriverBaseParams(int drvImplWaitTime, int drvExplWaitTime, int drvMaxWaitTime)
    {
        this.drvImplWaitTime = drvImplWaitTime;
        this.drvExplWaitTime = drvExplWaitTime;
        this.drvMaxWaitTime = drvMaxWaitTime;
    }

    public DriverBaseParams(int drvImplWaitTime, int drvExplWaitTime, int drvMaxWaitTime,
                            String localIpStr)
    {
        this.drvImplWaitTime = drvImplWaitTime;
        this.drvExplWaitTime = drvExplWaitTime;
        this.drvMaxWaitTime = drvMaxWaitTime;
        this.localIpStr     = localIpStr;
    }

    public DriverBaseParams(int drvImplWaitTime, int drvExplWaitTime, int drvMaxWaitTime,
                            String localIpStr, String remoteIpStr)
    {
        this.drvImplWaitTime = drvImplWaitTime;
        this.drvExplWaitTime = drvExplWaitTime;
        this.drvMaxWaitTime = drvMaxWaitTime;
        this.localIpStr     = localIpStr;
        this.remoteIpStr    = remoteIpStr;
    }
}
