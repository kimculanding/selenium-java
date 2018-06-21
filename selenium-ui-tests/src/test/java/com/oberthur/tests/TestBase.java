package com.oberthur.tests;

import com.oberthur.tests.dbConnector.DBHandler;
import com.oberthur.tests.pages.CommonElements.LogoutButton;
import com.oberthur.tests.selenium.WebDriverFactory;
import com.oberthur.tests.selenium.WebDriverWrapper;
import com.oberthur.tests.util.PropertyLoader;
import com.oberthur.tests.util.ReportWriter;
import com.oberthur.tests.util.TestlinkIntegration;
import org.apache.log4j.PropertyConfigurator;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import testlink.api.java.client.TestLinkAPIException;

import java.io.IOException;
import java.lang.reflect.Method;

/**
 * Created with IntelliJ IDEA.
 * User: Viktozhu
 * Date: 29.08.13
 * Time: 17:41
 * To change this template use File | Settings | File Templates.
 */
public class TestBase{
    protected static WebDriverWrapper driver;
    protected static final String LOGGER_CONFIG = "./src/main/resources/log4j.properties";
    protected DBHandler dbHandler;

    @BeforeSuite
    public void initEnv ()
    {
        PropertyConfigurator.configure(PropertyLoader.loadProperty("project.dir") + LOGGER_CONFIG);
        String browserName = PropertyLoader.loadProperty("browser.name");
        driver = new WebDriverFactory().initDriver(browserName, PropertyLoader.loadProperty("webUsers.ff.profileName"));
    }

    //Test Report Method
    @AfterMethod
    public void AfterMethod(Method test, ITestResult result)
    {
        LogoutButton.logout(driver);
        dbHandler.closeConnection();

        String testName = test.getAnnotation(Test.class).testName();
        Boolean useTestLink = Boolean.parseBoolean(PropertyLoader.loadProperty("testLink.integrated"));
        TestlinkIntegration tl = new TestlinkIntegration();

        if (result.isSuccess()) {
            ReportWriter.logTestFinishSuccess(this.getClass());
            if (!testName.equals("") && useTestLink) tl.reportTestLinkSuccess(testName);
        } else {
            ReportWriter.logTestFinishFailure(this.getClass());
            if (!testName.equals("") && useTestLink) tl.reportTestLinkFailed(testName);
        }
    }

    @AfterSuite (alwaysRun=true)
    public void shutEnv() {
        if (driver != null) {
            driver.quit();
        }
        if (dbHandler !=null)
        {
            dbHandler.closeConnection();
        }
    }
}
