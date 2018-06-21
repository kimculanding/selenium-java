package com.oberthur.tests.util;

import org.testng.Assert;
import org.testng.Reporter;

import java.io.IOException;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: Viktozhu
 * Date: 10.07.13
 * Time: 9:23
 * To change this template use File | Settings | File Templates.
 */
public class PropertyLoader {
    private static final String PROPERTY_FILE = "/application.properties";

    public static String loadProperty(String name)
    {
        Properties props = new Properties();
        try {
            props.load(PropertyLoader.class.getResourceAsStream(PROPERTY_FILE));
        } catch (IOException e) {
            Assert.fail(ReportWriter.logPropertyNotLoaded(name));
        }
        String value = "";
        if (name != null)
        {
            value = props.getProperty(name);
        }
        return value;
    }
}
