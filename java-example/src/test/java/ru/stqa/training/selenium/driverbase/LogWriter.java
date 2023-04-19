package ru.stqa.training.selenium.driverbase;

import javafx.util.converter.DateTimeStringConverter;

import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.Logs;

import java.io.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;


public class LogWriter
{
    private Logs           DriverLogs;
    private File           CurLogFolder;
    private String         CurrentTestName;
    private String         CurrentFileName;
    private FileWriter     fw;
    private String         rn      = "\r\n";

    private void Constructor (Logs driverLogs, Path curLogPath, String currentTestName, String currentFileName){
        DriverLogs      = driverLogs;
        CurrentTestName = currentTestName;
        CurrentFileName = currentFileName;

        File    baseLogFolder = new File(curLogPath.toString());
        File    curLogFolder  = new File(baseLogFolder, CurrentTestName);

        try {
            Files.createDirectories(curLogFolder.toPath());
        }
        catch (Exception ex) {
            System.out.println("Ошибка при попытке создания под-дирректории лога: " + ex);
        }
        CurLogFolder    = curLogFolder;
    }

    public LogWriter(Logs driverLogs, Path curLogPath, String currentTestName) {
        Constructor(driverLogs, curLogPath, currentTestName, currentTestName);
    }

    public LogWriter(Logs driverLogs, Path curLogPath, String currentTestName, String currentFileName)
    {
        Constructor(driverLogs, curLogPath, currentTestName, currentFileName);
    }

    public void LogWrite(String eventName, String logMessage)
    {
        try {

            File fileInDirectory = new File(CurLogFolder, CurrentFileName + ".txt");
            fw = new FileWriter(fileInDirectory, true); //опция добавление новых строк в конец файла
        }
        catch (Exception ex) {
            System.out.println("Ошибка при попытке создания файла лога: " + ex);
        }

        try
        {
            Log(eventName, logMessage);
            fw.flush();
            fw.close();
        }
        catch (IOException ex)
        {
            System.out.println("Ошибка при работе с файлом лога: " + ex);
        }
    }

    public void Log(String eventName, String logMessage)
    {
        Date date = new Date();
        DateTimeStringConverter dtConverter = new DateTimeStringConverter(null, "HH:mm:ss yyyy-MM-dd.");

        try
        {
            fw.append(rn);
            fw.write("Log Entry : ");
            fw.write(dtConverter.toString(date));
            fw.write(System.lineSeparator()); //new line
            fw.write("event: " + eventName);
            fw.write(System.lineSeparator()); //new line
            fw.write("     : " + logMessage);
            fw.write(System.lineSeparator()); //new line
            fw.write("-------------------------------------------------------");
            fw.write(System.lineSeparator()); //new line
        }
        catch(IOException ex) {
            System.out.println("Ошибка ввода-вывода: " + ex);
        }
    }

    public void FinalLogWrite()
    {
        LogWrite("================================================",
                "================================================");
    }

    public void saveCurLogs(DriverDefault.WebDriverType webDriverType, String logType)
    {
        if (webDriverType == DriverDefault.WebDriverType.Chrome)
            DriverLogs.get(logType).forEach(l -> LogWrite("BrowserLogWrite", l.getMessage()));
    }

    public void saveAllCurLogs(DriverDefault.WebDriverType webDriverType)
    {
        saveCurLogs(webDriverType, LogType.BROWSER);
        saveCurLogs(webDriverType, LogType.SERVER);
        saveCurLogs(webDriverType, LogType.CLIENT);
        saveCurLogs(webDriverType, LogType.DRIVER);
        saveCurLogs(webDriverType, LogType.PROFILER);
    }
}
