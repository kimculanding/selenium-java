package com.oberthur.tests.dbConnector;

import com.oberthur.tests.util.PropertyLoader;
import com.oberthur.tests.util.ReportWriter;
import org.testng.Assert;

import java.sql.*;

/**
 * Created with IntelliJ IDEA.
 * User: Viktozhu
 * Date: 26.03.13
 * Time: 11:10
 * To change this template use File | Settings | File Templates.
 */
public class JdbcConnector {

    private static int PORT;
    private static String ORACLE_DRIVER;
    private static String HOST;
    private static String SID;
    private static String ORACLE_LINK;

    protected Connection connection;
    protected Statement statement;



    public JdbcConnector()
    {
        ORACLE_DRIVER = new String();
        PORT = Integer.parseInt(PropertyLoader.loadProperty("db.port"));
        ORACLE_DRIVER = PropertyLoader.loadProperty("db.oracle_driver");
        HOST = PropertyLoader.loadProperty("db.host");
        SID = PropertyLoader.loadProperty("db.sid");
        ORACLE_LINK = PropertyLoader.loadProperty("db.oracle_link");
        try {
            Class.forName(ORACLE_DRIVER);
        } catch (ClassNotFoundException e) {
            Assert.fail(ReportWriter.errorSQL("Failed to create JDBC connector: " + e.toString()));
        }
    }

    public void initConnection(String name, String password)
    {
        //'jdbc:oracle:thin:/@10.200.41.103:1521:SMDB10'
        String link = ORACLE_LINK + HOST + ':' + PORT + ':' + SID;
        try {
            connection = DriverManager.getConnection(link, name, password);
            statement = connection.createStatement();
        } catch (SQLException e) {
            Assert.fail(ReportWriter.errorSQL("Failed to create JDBC connector: " + e.toString()));
        }
    }

    public void execute(String sql)
    {
        try {
            statement.execute(sql);

        } catch (SQLException e) {
            Assert.fail(ReportWriter.errorSQL("Failed to create JDBC connector: " + e.toString()));
        }
    }

    public int update(String sql)
    {
        try {
            return statement.executeUpdate(sql);
        } catch (SQLException e) {
            Assert.fail(ReportWriter.errorSQL("Failed to create JDBC connector: " + e.toString()));
        }
        return 0;
    }

    public ResultSet select(String sql)
    {
        try {
            ResultSet resultSet = statement.executeQuery(sql);
            return resultSet;
        } catch (SQLException e) {
            Assert.fail(ReportWriter.errorSQL("Failed to create JDBC connector: " + e.toString()));
        }
        return null;
    }

    public void closeConnection()
    {
        try {
            connection.close();
        } catch (SQLException e) {
            Assert.fail(ReportWriter.errorSQL("Failed to create JDBC connector: " + e.toString()));
        }
    }


}
