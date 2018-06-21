package com.oberthur.tests.selenium;


import com.oberthur.tests.util.PropertyLoader;
import com.oberthur.tests.util.ReportWriter;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Platform;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.browserlaunchers.locators.InternetExplorerLocator;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.Optional;

import java.io.File;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: Viktozhu
 * Date: 22.03.13
 * Time: 10:39
 * To change this template use File | Settings | File Templates.
 */
public class WebDriverFactory {

    /* Browsers constants */
    public static final String CHROME = "chrome";
    public static final String FIREFOX = "firefox";
    public static final String OPERA = "opera";
    public static final String INTERNET_EXPLORER = "ie";
    public static final String HTML_UNIT = "htmlunit";
    public static final String IPHONE = "iphone";

    /* Platform constants */
    public static final String WINDOWS = "windows";
    public static final String ANDROID = "android";
    public static final String XP = "xp";
    public static final String VISTA = "vista";
    public static final String MAC = "mac";
    public static final String LINUX = "linux";

    /* Firefox profiles */
    public static final String DEFAULT_PROFILE = "default";

    public WebDriverWrapper initDriver (String browser, String profileName)
    {
        WebDriverWrapper testDriver = setDefaultConfiguration(browser, profileName);
        return testDriver;
    }

    private WebDriverWrapper setDefaultConfiguration(String browser, String profileName)
    {
        WebDriver driver = null;
        DesiredCapabilities capabilities = setDefaultCapabilities(browser, profileName);

        if (browser.equals(FIREFOX))
        {
            driver = new FirefoxDriver(capabilities);
        } else
        if (browser.equals(CHROME))
        {
            File file = new File(PropertyLoader.loadProperty("selenium.chrome.driver.path"));
            System.setProperty("webdriver.chrome.driver",file.getAbsolutePath());
            driver = new ChromeDriver(capabilities);
        } else
        if (browser.equals(INTERNET_EXPLORER))
        {
            File file = new File(PropertyLoader.loadProperty("selenium.ie.driver.path"));
            System.setProperty("webdriver.ie.driver",file.getAbsolutePath());
            driver = new InternetExplorerDriver(capabilities);
        } else
        if (browser.equals(HTML_UNIT))
        {
            driver = new HtmlUnitDriver(capabilities);
        } else
            Assert.fail(ReportWriter.errorDriverUnknown(browser));

        //additional configuration
        driver.manage().timeouts().implicitlyWait(Integer.parseInt(PropertyLoader.loadProperty("selenium.implicitly.wait")), TimeUnit.SECONDS);
        driver = setScreenReolution(driver);

        WebDriverWrapper testDriver = new WebDriverWrapper(driver);
        return testDriver;
    }

    private WebDriver setScreenReolution(WebDriver driver)
    {
        int xDimension = Integer.parseInt(PropertyLoader.loadProperty("selenium.screen.x"));
        int yDimension = Integer.parseInt(PropertyLoader.loadProperty("selenium.screen.y"));
        driver.manage().window().setSize(new Dimension(xDimension,yDimension));
        return driver;
    }

    private DesiredCapabilities setDefaultCapabilities(String browser, String profileName)
    {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities = setPlatform(capabilities,"");

        if (browser.contains(FIREFOX)){
            capabilities = DesiredCapabilities.firefox();
            FirefoxProfile profile;
            profile = new ProfilesIni().getProfile(profileName);
            profile.setAcceptUntrustedCertificates(true);
            profile.setAssumeUntrustedCertificateIssuer(false);
            profile.setPreference("setAcceptUntrustedCertificates", "true");
            profile.setPreference("setAssumeUntrustedCertificateIssuer", "false");
            capabilities.setCapability(FirefoxDriver.PROFILE, profile);
        } else
        if (browser.contains(INTERNET_EXPLORER)){
            capabilities = DesiredCapabilities.internetExplorer();

            capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
            capabilities.setCapability("ignoreProtectedModeSettings", "true");
            capabilities.setCapability("ignoreCertificateErrors", "true");
            capabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.ACCEPT);

            capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, "true");
        } else
        if (browser.contains(CHROME)){
            capabilities = DesiredCapabilities.chrome();
        } else
        if (browser.contains(HTML_UNIT)){
            capabilities = DesiredCapabilities.htmlUnit();
        }else
            Assert.fail(ReportWriter.errorDriverUnknown(browser));

        return capabilities;
    }

    private DesiredCapabilities setPlatform(DesiredCapabilities capabilities,String platformName)
    {
        if (platformName.equals(WINDOWS))
        {
            capabilities.setPlatform(Platform.WINDOWS);
        } else
        if (platformName.equals(LINUX))
        {
            capabilities.setPlatform(Platform.LINUX);
        } else
        {
            capabilities.setPlatform(Platform.WINDOWS);
        }
        return capabilities;
    }
}
