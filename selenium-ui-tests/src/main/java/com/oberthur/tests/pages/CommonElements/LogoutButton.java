package com.oberthur.tests.pages.CommonElements;

import com.oberthur.tests.selenium.WebDriverWrapper;
import org.openqa.selenium.By;

/**
 * Created with IntelliJ IDEA.
 * User: Viktozhu
 * Date: 05.09.13
 * Time: 9:04
 * To change this template use File | Settings | File Templates.
 */
public class LogoutButton {
    private static By userMenu= By.id("user-menu-link");
    private static By logoutUser= By.id("logout-link");

    public static void logout(WebDriverWrapper driver)
    {
        driver.findElement(userMenu).click();
        driver.findElement(logoutUser).click();
    }
}
