package ru.stqa.training.selenium.driverbase;


public class DriverBaseParams {
    // переменная времени (в сек) общих неявных ожиданий
    public int drvImplWaitTime;
    // переменная времени (в сек) для явных ожиданий
    public int drvExplWaitTime;
    // переменная времени (в сек) для максимального времени неявного ожидания
    public int drvMaxWaitTime;


    private void setParams(int drvImplWaitTime, int drvExplWaitTime, int drvMaxWaitTime) {
        this.drvImplWaitTime = drvImplWaitTime;
        this.drvExplWaitTime = drvExplWaitTime;
        this.drvMaxWaitTime = drvMaxWaitTime;
    }

    public DriverBaseParams()
    {
        setParams(DriverConst.drvImplWaitTime, DriverConst.drvExplWaitTime, DriverConst.drvMaxWaitTime);
    }

    public DriverBaseParams(int drvImplWaitTime)
    {
        setParams(drvImplWaitTime, DriverConst.drvExplWaitTime, DriverConst.drvMaxWaitTime);
    }

    public DriverBaseParams(int drvImplWaitTime, int drvExplWaitTime)
    {
        setParams(drvImplWaitTime, drvExplWaitTime, DriverConst.drvMaxWaitTime);
    }

    public DriverBaseParams(int drvImplWaitTime, int drvExplWaitTime, int drvMaxWaitTime)
    {
        setParams(drvImplWaitTime, drvExplWaitTime, drvMaxWaitTime);
    }


}
