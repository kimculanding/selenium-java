package com.oberthur.tests.pages;

import com.oberthur.tests.selenium.WebDriverWrapper;
import org.openqa.selenium.By;

/**
 * Created with IntelliJ IDEA.
 * User: Viktozhu
 * Date: 03.09.13
 * Time: 13:39
 * To change this template use File | Settings | File Templates.
 */
public class HomePage extends PageBase{
    private static final String PAGE_URL = "/dashboard.action";

    private By Spaces = By.cssSelector("a[class='aui-nav-imagelink',title='Spaces']");

    public HomePage(WebDriverWrapper driver)
    {
        super(driver,PAGE_URL);
    }

    public void open()
    {
        driver.get(pageUrl);
    }
}
