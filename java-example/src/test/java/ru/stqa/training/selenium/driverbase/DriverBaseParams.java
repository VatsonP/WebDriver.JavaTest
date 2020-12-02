package ru.stqa.training.selenium.driverbase;


public class DriverBaseParams {

    // переменная времени (в сек) общих неявных ожиданий
    public int drvImplWaitTime;
    // переменная времени (в сек) для явных ожиданий
    public int drvExplWaitTime;
    // переменная времени (в сек) для максимального времени неявного ожидания
    public int drvMaxWaitTime;

    // LocalHost
    private static final String localHostStr = "localhost";
    public String getLocalHostStr() { return localHostStr; } // get LocalHost

    // Local Host Ip
    private String localIpStr;
    public String getLocalIpStr() { return localIpStr; }   // get Local Host Ip

    // Remote Host Ip
    private String remoteIpStr;
    public String getRemoteIpStr() { return remoteIpStr; }  // get Remote Host Ip

    private void setParams(int drvImplWaitTime, int drvExplWaitTime, int drvMaxWaitTime,
                           String localIpStr, String remoteIpStr) {
        this.drvImplWaitTime = drvImplWaitTime;
        this.drvExplWaitTime = drvExplWaitTime;
        this.drvMaxWaitTime = drvMaxWaitTime;
        this.localIpStr     = localIpStr;
        this.remoteIpStr    = remoteIpStr;
    }

    public DriverBaseParams()
    {
        setParams(DriverConst.drvImplWaitTime, DriverConst.drvExplWaitTime, DriverConst.drvMaxWaitTime,
                  DriverConst.localIpStr_Win, DriverConst.remoteIpStr_Ubuntu_20_4);
    }

    public DriverBaseParams(int drvImplWaitTime)
    {
        setParams(drvImplWaitTime, DriverConst.drvExplWaitTime, DriverConst.drvMaxWaitTime,
                  DriverConst.localIpStr_Win, DriverConst.remoteIpStr_Ubuntu_20_4);
    }

    public DriverBaseParams(int drvImplWaitTime, int drvExplWaitTime)
    {
        setParams(drvImplWaitTime, drvExplWaitTime, DriverConst.drvMaxWaitTime,
                  DriverConst.localIpStr_Win, DriverConst.remoteIpStr_Ubuntu_20_4);
    }

    public DriverBaseParams(int drvImplWaitTime, int drvExplWaitTime, int drvMaxWaitTime)
    {
        setParams(drvImplWaitTime, drvExplWaitTime, drvMaxWaitTime,
                  DriverConst.localIpStr_Win, DriverConst.remoteIpStr_Ubuntu_20_4);
    }

    public DriverBaseParams(int drvImplWaitTime, int drvExplWaitTime, int drvMaxWaitTime,
                            String localIpStr)
    {
        setParams(drvImplWaitTime, drvExplWaitTime, drvMaxWaitTime,
                  localIpStr, DriverConst.remoteIpStr_Ubuntu_20_4);
    }

    public DriverBaseParams(int drvImplWaitTime, int drvExplWaitTime, int drvMaxWaitTime,
                            String localIpStr, String remoteIpStr)
    {
        setParams(drvImplWaitTime, drvExplWaitTime, drvMaxWaitTime,
                  localIpStr, remoteIpStr);
    }
}
