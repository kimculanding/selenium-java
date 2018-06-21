package com.oberthur.tests.firsttest;

import com.oberthur.tests.dbConnector.DBHandler;
import com.oberthur.tests.TestBase;
import com.oberthur.tests.pages.CommonElements.LogoutButton;
import com.oberthur.tests.pages.HomePage;
import com.oberthur.tests.pages.LoginPage;
import com.oberthur.tests.util.*;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.awt.*;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Viktozhu
 * Date: 30.08.13
 * Time: 9:52
 * To change this template use File | Settings | File Templates.
 */
public class SmokeTest extends TestBase{

    @DataProvider
    public Object[][] inputDataTypes() {
        return new Object[][] {
                new Object[] {"./src/main/resources/t1Indata.csv"},
                new Object[] {"./src/main/resources/t1Indata.xml"},
                new Object[] {"./src/main/resources/t1Indata.xls"},
        };
    }

    @BeforeMethod
    public void preConditions()
    {
        String dbUser = PropertyLoader.loadProperty("db.user");
        String dbPassword = PropertyLoader.loadProperty("db.passwd");
        dbHandler = new DBHandler(dbUser,dbPassword);
        ReportWriter.info("DB working OK, 1=" + dbHandler.get_1());

        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        String user = PropertyLoader.loadProperty("default.user");
        String password = PropertyLoader.loadProperty("default.password");
        loginPage.loginAs(user,password);
    }

   // @Test
    public void smokeTest()
    {
        HomePage homePage = new HomePage(driver);
        Assert.assertTrue(homePage.isOpened());
    }

   // @Test (dependsOnMethods = {"smokeTest"}, dataProvider = "inputDataTypes") //better not use such dependency, only if you have to!
    public void readDataTest(String fileName)
    {
        int iccid=0;
        int imsi=0;
        if (fileName.contains(".csv"))
        {
            Map<String,String[]> csvFile = DataLoader.readCsvFile(PropertyLoader.loadProperty("project.dir") + fileName,
                                            PropertyLoader.loadProperty("default.delimiter"));
            iccid= Integer.parseInt(csvFile.get("iccid")[0]);
            imsi = Integer.parseInt(csvFile.get("imsi")[0]);
        }
        if (fileName.contains(".xml"))
        {
            Document xmlFile = DataLoader.readXMLFile(fileName);
            NodeList nodeList = xmlFile.getElementsByTagName("iccid");
            iccid = Integer.parseInt(nodeList.item(0).getTextContent());

            nodeList = xmlFile.getElementsByTagName("imsi");
            imsi = Integer.parseInt(nodeList.item(0).getTextContent());
        }
        if (fileName.contains(".xls"))
        {
            String[][] xlsFile = DataLoader.readXLSFile(fileName,"Sheet1");
            iccid = Integer.parseInt(xlsFile[0][1]);
            imsi = Integer.parseInt(xlsFile[1][1]);
        }

        ReportWriter.info("iccid="+iccid);
        ReportWriter.info("imsi="+imsi);
        Assert.assertNotEquals(iccid,imsi);
    }

    @Test(testName = "132")
    public void TestLinkTest()
    {
        ScreenShotMaker screenShotMaker = new ScreenShotMaker(driver);
        screenShotMaker.takeScreenShot("a123");
        Assert.assertTrue(true);
    }

}
