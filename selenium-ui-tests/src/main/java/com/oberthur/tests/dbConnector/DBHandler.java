package com.oberthur.tests.dbConnector;

import com.oberthur.tests.dbConnector.JdbcConnector;
import com.oberthur.tests.util.PropertyLoader;
import com.oberthur.tests.util.ReportWriter;
import org.testng.Assert;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: Viktozhu
 * Date: 05.09.13
 * Time: 16:32
 * To change this template use File | Settings | File Templates.
 */
public class DBHandler {
    private JdbcConnector jdbcConnector;

    public DBHandler (String dbUser, String dbPassword)
    {
        jdbcConnector = new JdbcConnector();
        jdbcConnector.initConnection(dbUser,dbPassword);
    }

    public String get_1()
    {
        try {
            ResultSet res = jdbcConnector.select("select 1 from dual");
            res.next();
            return res.getString(1);
        }
        catch (SQLException e)
        {
            Assert.fail(ReportWriter.errorSQL(e.toString()));
        }
        return "";
    }

    public void closeConnection()
    {
        jdbcConnector.closeConnection();
    }
}
