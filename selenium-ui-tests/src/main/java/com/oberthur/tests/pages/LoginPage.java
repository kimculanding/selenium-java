package com.oberthur.tests.pages;

import com.oberthur.tests.selenium.WebDriverWrapper;
import org.openqa.selenium.By;

/**
 * Created with IntelliJ IDEA.
 * User: Viktozhu
 * Date: 05.09.13
 * Time: 8:42
 * To change this template use File | Settings | File Templates.
 */
public class LoginPage extends PageBase{
    public static final String PAGE_URL="/login.action";

    private By userEdit = By.id("os_username");
    private By passEdit = By.id("os_password");
    private By loginButton = By.id("loginButton");

    public LoginPage(WebDriverWrapper driver)
    {
        super(driver, PAGE_URL);
    }

    public void open()
    {
        driver.get(pageUrl);
    }

    public void loginAs(String user, String passwd)
    {
        driver.findElement(userEdit).clear();
        driver.findElement(userEdit).sendKeys(user);
        driver.findElement(passEdit).clear();
        driver.findElement(passEdit).sendKeys(passwd);
        driver.findElement(loginButton).click();
    }
}
