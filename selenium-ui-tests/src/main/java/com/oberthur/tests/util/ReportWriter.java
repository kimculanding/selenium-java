package com.oberthur.tests.util;

import br.eti.kinoshita.testlinkjavaapi.constants.ExecutionStatus;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;
import testlink.api.java.client.TestLinkAPIException;

import java.io.IOException;
import java.net.MalformedURLException;

/**
 * Created with IntelliJ IDEA.
 * User: Viktozhu
 * Date: 30.08.13
 * Time: 13:16
 * To change this template use File | Settings | File Templates.
 */
public class ReportWriter {
    private static final Logger LOGGER = Logger.getLogger(ReportWriter.class);

    // ****************** Default message content ********************
    private static final String SEPARATOR = "***********************************************************************";
    private static final String ELEMENT_PRESENT = "PASS: element \"%s\" presents!";
    private static final String ELEMENT_NOT_FOUND = "FAIL: element \"%s\" not found!";
    private static final String PROPERTY_NOT_LOADED = "FAIL: Could not load property \"%s\"!";
    private static final String PAGE_NOT_LOADED = "FAIL: Could not load page \"%s\"!";
    private static final String DRIVER_UNKNOWN = "ERROR: Unknown web-driver \"%s\"!";
    private static final String TEST_FINISHED_SUCCESS = "INFO: test \"%s\" is finished SUCCESSFULLY";
    private static final String TEST_FINISHED_FAILURE = "INFO: test \"%s\" is finished with VALIDATION FAILURES";
    private static final String TESTLINK_ERROR = "ERROR: TestLink reporting fail: \"%s\"!";
    private static final String MAIL_GITTING_ERROR = "ERROR: Error getting email \"%s\"!";
    private static final String DB_ERROR = "ERROR: DB operation failed: \"%s\"!";
    private static final String DATA_LOADED = "INFO: Load data from \"%s\"!";
    private static final String DATA_LOAD_ERROR = "ERROR: Error loading data from \"%s\"!";
    private static final String INFO_LOG = "INFO: \"%s\"";
    private static final String ERROR_LOG = "ERROR: \"%s\"";

    public static String errorTestLinkOperation (String error)
    {
        LOGGER.error(String.format(TESTLINK_ERROR, error));
        Reporter.log(String.format(TESTLINK_ERROR, error));
        return String.format(TESTLINK_ERROR, error);
    }

    public static String errorGettingMail (String fileName)
    {
        LOGGER.error(String.format(MAIL_GITTING_ERROR, fileName));
        Reporter.log(String.format(MAIL_GITTING_ERROR, fileName));
        return String.format(MAIL_GITTING_ERROR, fileName);

    }

    public static String loadDataInfo (String fileName)
    {
        LOGGER.info(String.format(DATA_LOADED, fileName));
        Reporter.log(String.format(DATA_LOADED, fileName));
        return String.format(DATA_LOADED, fileName);
    }

    public static String loadDataError (String fileName)
    {
        LOGGER.error(String.format(DATA_LOAD_ERROR, fileName));
        Reporter.log(String.format(DATA_LOAD_ERROR, fileName));
        return String.format(DATA_LOAD_ERROR, fileName);
    }

    public static String info (String str)
    {
        LOGGER.info(String.format(INFO_LOG, str));
        Reporter.log(String.format(INFO_LOG, str));
        return String.format(INFO_LOG, str);
    }

    public static String error (String str)
    {
        LOGGER.error(String.format(ERROR_LOG, str));
        Reporter.log(String.format(ERROR_LOG, str));
        return String.format(ERROR_LOG, str);
    }

    public static String errorDriverUnknown (String name)
    {
        LOGGER.error(String.format(DRIVER_UNKNOWN, name));
        Reporter.log(String.format(DRIVER_UNKNOWN, name));
        return String.format(DRIVER_UNKNOWN, name);
    }

    public static String errorSQL (String name)
    {
        LOGGER.error(String.format(DB_ERROR, name));
        Reporter.log(String.format(DB_ERROR, name));
        return String.format(DB_ERROR, name);
    }

    public static String logElementPresent (String name)
    {
        LOGGER.info(String.format(ELEMENT_PRESENT, name));
        Reporter.log(String.format(ELEMENT_PRESENT, name));
        return String.format(ELEMENT_PRESENT, name);
    }

    public static String logElementNotPresent(String name) {
        LOGGER.info(String.format(ELEMENT_NOT_FOUND, name));
        Reporter.log(String.format(ELEMENT_NOT_FOUND, name));
        return String.format(ELEMENT_NOT_FOUND, name);
    }

    public static String logPropertyNotLoaded(String s)
    {
        LOGGER.error(String.format(PROPERTY_NOT_LOADED, s));
        Reporter.log(String.format(PROPERTY_NOT_LOADED, s));
        return String.format(PROPERTY_NOT_LOADED, s);
    }

    public static String logPageNotLoaded(String s)
    {
        LOGGER.error(String.format(PAGE_NOT_LOADED, s));
        Reporter.log(String.format(PAGE_NOT_LOADED, s));
        return String.format(PAGE_NOT_LOADED, s);
    }



    public static void logTestFinishSuccess(Class<?> clazz) {
        LOGGER.info(String.format(TEST_FINISHED_SUCCESS, clazz.getSimpleName()));
        LOGGER.info(SEPARATOR);
        Reporter.log(String.format(TEST_FINISHED_SUCCESS, clazz.getSimpleName()));
        Reporter.log(SEPARATOR);
    }

    public static void logTestFinishFailure(Class<?> clazz) {
        LOGGER.info(String.format(TEST_FINISHED_FAILURE, clazz.getSimpleName()));
        LOGGER.info(SEPARATOR);
        Reporter.log(String.format(TEST_FINISHED_FAILURE, clazz.getSimpleName()));
        Reporter.log(SEPARATOR);
    }
}
