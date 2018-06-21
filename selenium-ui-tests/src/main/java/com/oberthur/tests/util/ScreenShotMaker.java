package com.oberthur.tests.util;

import com.oberthur.tests.selenium.WebDriverWrapper;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.Augmenter;
import org.testng.Assert;
import org.testng.Reporter;

import java.io.File;
import java.io.IOException;

/*
 * Created with IntelliJ IDEA.
 * User: Viktozhu
 * Date: 26.06.13
 * Time: 14:45
 * To change this template use File | Settings | File Templates.
 */
public class ScreenShotMaker {
    protected WebDriverWrapper driver;
    private static String screenShotDirectory;

    public static void clearScreenShotSubDirectory(String screenShotSubDirectory)
    {
        File scrSubDir = new File(PropertyLoader.loadProperty("project.dir"),(new File(screenShotDirectory,screenShotSubDirectory)).toString());

        if (scrSubDir.exists()) {
            try {
                FileUtils.cleanDirectory(scrSubDir);
            } catch (IOException e) {
                Assert.fail(ReportWriter.error("ScreenShot not done:" + e.toString()));
            }
        }
    }

    public ScreenShotMaker(WebDriverWrapper driver)
    {
        this.driver = driver ;
        screenShotDirectory = PropertyLoader.loadProperty("screenshot.folder");
        File scrDir = new File (screenShotDirectory);
        if (!scrDir.exists())
            scrDir.mkdirs();
    }

    public void takeScreenShot(String scrName)
    {
        String scrFormat = PropertyLoader.loadProperty("screenshot.format");
        try {
            Augmenter augmenter = new Augmenter();
            File scrFile = ((TakesScreenshot)augmenter.augment(driver)).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(scrFile, new File(screenShotDirectory + scrName + scrFormat));
        }
        catch (Exception e)
        {
            Assert.fail(ReportWriter.error("ScreenShot not done:" + e.toString()));
        }
    }
}
