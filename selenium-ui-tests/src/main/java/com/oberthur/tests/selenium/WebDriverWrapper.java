package com.oberthur.tests.selenium;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import com.oberthur.tests.util.PropertyLoader;
import com.oberthur.tests.util.ReportWriter;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Viktozhu
 * Date: 30.08.13
 * Time: 13:00
 * To change this template use File | Settings | File Templates.
 */
public class WebDriverWrapper implements WebDriver, TakesScreenshot{
    // ****************** Configuration ********************
    private static final long MAX_TIMEOUT = Long.parseLong(PropertyLoader.loadProperty("selenium.max.timeout"));
    private static final long RETRY_TIME = Long.parseLong(PropertyLoader.loadProperty("selenium.retry.time"));

    private WebDriver driver;
    private  static Wait<WebDriver> wait;
    private int SELENIUM_DELAY = 50; //delay in milliseconds. Fix me if you can


    // ****************** Base wait before act methods ********************

    public boolean waitAndVerifyElementPresent(String name, final By by) {

        wait = new WebDriverWait(driver, MAX_TIMEOUT, RETRY_TIME * 1000);
        try {
            wait.until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver dr) {
                    return dr.findElement(by) != null;
                }
            });
        } catch (Exception e) {
            ReportWriter.logElementNotPresent(name);
            return false;
        }
        ReportWriter.logElementPresent(name);
        return true;
    }

    public boolean waitAndAssertElementPresent(String name, final By by) {

        wait = new WebDriverWait(driver, MAX_TIMEOUT, RETRY_TIME * 1000);
        try {
            wait.until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver dr) {
                    return dr.findElement(by) != null;
                }
            });
        } catch (Exception e) {
            Assert.fail(ReportWriter.logElementNotPresent(name));
        }
        ReportWriter.logElementPresent(name);
        return true;
    }

    public boolean waitAndAssertURL(final String url) {
        try {
            wait = new WebDriverWait(driver, MAX_TIMEOUT, RETRY_TIME * 1000);
            wait.until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver dr) {
                    return driver.getCurrentUrl().contains(url);
                }
            });
        } catch (Exception e) {
            Assert.fail(ReportWriter.logPageNotLoaded(url));
        }
        return true;
    }


    public boolean waitAndVerifyURL(final String url) {
        try {
            wait = new WebDriverWait(driver, MAX_TIMEOUT, RETRY_TIME * 1000);
            wait.until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver dr) {
                    return driver.getCurrentUrl().contains(url);
                }
            });
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public WebDriverWrapper(WebDriver driver)
    {
        this.driver = driver;
    }

    public void get(String url) {
        try {
            driver.get(url);
        } catch (UnhandledAlertException e) {
            clickCertChoiceWindow();
        }
    }

    public String getCurrentUrl() {

        try {
            return driver.getCurrentUrl();
        } catch (UnhandledAlertException e) {
            clickCertChoiceWindow();
        }
        return driver.getCurrentUrl();
    }

    public void clickCertChoiceWindow() {
        int sertNumber = Integer.parseInt(PropertyLoader.loadProperty("webUsers.ie.profileNum"));
        int i = 1;
        try {
            Robot robot = new Robot();
            while (i < sertNumber) {
                robot.keyPress(KeyEvent.VK_DOWN);
                i++;
                robot.delay(SELENIUM_DELAY);
            }

            robot.keyPress(KeyEvent.VK_ENTER);
            robot.delay(SELENIUM_DELAY);

        } catch (AWTException e) {
            Assert.fail(ReportWriter.error("Cannot switch certificate in IE"+e.toString()));
        }
    }

    public String getTitle() {
        return driver.getTitle();
    }

    public List<WebElement> findElements(By by) {
        return driver.findElements(by);
    }

    public WebElement findElement(By by) {
        try{
            return driver.findElement(by);
        }
        catch (Exception e)
        {
            Assert.fail(ReportWriter.logElementNotPresent(by.toString()));
        }
        return null;
    }

    public String getPageSource() {
        return getPageSource();
    }

    public void close() {
        driver.close();
    }

    public void quit() {
        driver.quit();
    }

    public Set<String> getWindowHandles() {
        return driver.getWindowHandles();
    }

    public String getWindowHandle() {
        return driver.getWindowHandle();
    }

    public TargetLocator switchTo() {
        return driver.switchTo();
    }

    public Navigation navigate() {
        return driver.navigate();
    }

    public Options manage() {
        return driver.manage();
    }

    public <X> X getScreenshotAs(OutputType<X> outType) {
        try {
            if (driver instanceof FirefoxDriver) {
                return ((FirefoxDriver) driver).getScreenshotAs(outType);
            } else if (driver instanceof ChromeDriver) {
                return ((ChromeDriver) driver).getScreenshotAs(outType);
            } else if (driver instanceof InternetExplorerDriver) {
                return ((InternetExplorerDriver) driver).getScreenshotAs(outType);
            } else {
                return null;
            }
        }
        catch (Exception e)
        {
            Assert.fail(ReportWriter.error("ScreenShot making error:"+ e.toString()));
        }
        return null;
    }
}
