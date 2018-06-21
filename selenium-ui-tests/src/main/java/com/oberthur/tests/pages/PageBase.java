package com.oberthur.tests.pages;

import com.oberthur.tests.selenium.WebDriverWrapper;
import com.oberthur.tests.util.PropertyLoader;
import com.oberthur.tests.util.ReportWriter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

/**
 * Created with IntelliJ IDEA.
 * User: Viktozhu
 * Date: 03.09.13
 * Time: 13:40
 * To change this template use File | Settings | File Templates.
 */
public abstract class PageBase {
    protected WebDriverWrapper driver;
    protected String pageUrl;
    protected String websiteUrl;

    abstract public void open();

    public boolean isOpened()
    {
        return driver.waitAndVerifyURL(pageUrl);
    }

    public PageBase(WebDriverWrapper driver, String pageUrl)
    {
        websiteUrl = PropertyLoader.loadProperty("site.url");
        this.pageUrl = websiteUrl + pageUrl;
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public String getPageUrl()
    {
        return pageUrl;
    }
}
