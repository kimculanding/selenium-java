package com.oberthur.tests.util;

import org.testng.Assert;
import javax.mail.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static javax.mail.Session.getInstance;


public class MailReader {
    protected Message messages[];
    private Properties connectionProperties;
    String emailServer;
    String emailPort;
    String emailTransport;
    String emailUser;
    String emailPassword;


    public MailReader(String emailUserStr, String emailPasswordStr) {
        // Create all the needed properties - empty!
        try {
            emailServer = PropertyLoader.loadProperty("james.server.ip");
            emailPort = PropertyLoader.loadProperty("james.server.port");
            emailTransport = PropertyLoader.loadProperty("james.server.transport");
            emailUser = emailUserStr;
            emailPassword = emailPasswordStr;

            connectionProperties = new Properties();
            connectionProperties.setProperty("mail.pop3.host", emailServer);
            connectionProperties.setProperty("mail.pop3.port", emailPort);
            connectionProperties.setProperty("mail.pop3.user", emailUser);
            connectionProperties.setProperty("mail.debug", "true");
            // Create the session

            getMessages();
        } catch (Exception e) {
            Assert.fail(ReportWriter.errorGettingMail(e.toString()));
        }
    }

    public void getMessages() {
        try {
            Session session = getInstance(connectionProperties);
            ReportWriter.info("Connecting to the " + emailTransport + " server...");
            // Connecting to the server
            // Set the store depending on the parameter flag value
            Store store = session.getStore(emailTransport);
            // Set the server depending on the parameter flag value
            String server = emailServer;
            store.connect(server, emailUser, emailPassword);
            ReportWriter.info("done!");
            // Get the Inbox folder
            Folder inbox = store.getFolder("Inbox");
            // Set the mode to the read-only mode
            inbox.open(Folder.READ_ONLY);
            // Get messages
            messages = inbox.getMessages();
            ReportWriter.info("Reading messages...");
        }
        catch (Exception e)
        {
            Assert.fail(ReportWriter.errorGettingMail(e.toString()));
        }

    }


    public String getToken() {
        String urlWithToken = "";
        try {
            String letterContent = "";
            if (messages.length > 0) {
    
                Message lastMessage = messages[0];
    
                SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                String dt = "1970/01/01";
                Date last = df.parse(dt);
    
                Pattern patt = Pattern.compile("https:.*\\?token=.*");
                for (Message message : messages) {
                    Date a = message.getSentDate();
    
                    if (a.after(last) && message.getContent().toString().contains("token")) {
                        lastMessage = message;
                        letterContent = message.getContent().toString();
                        last = a;
    
                    }
                }
                if (!letterContent.isEmpty()) {
                    Matcher m = patt.matcher(letterContent);
                    StringBuffer sb = new StringBuffer(letterContent.length());
                    while (m.find()) {
                        urlWithToken = m.group(0);
                        ReportWriter.info("TokenUrl: " + urlWithToken);
                    }
    
                }
            }
        }
        catch (Exception e)
        {
            Assert.fail(ReportWriter.errorGettingMail(e.toString())); 
        }
        return urlWithToken;
    }


    public String getPassword() {
        String passwd = "";
        try {
            String letterContent = "";
            Message lastMessage = messages[0];
    
            SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
            String dt = "1970/01/01";
            Date last = df.parse(dt);
    
            Pattern patt = Pattern.compile("Your new password : (.*)");
            for (Message message : messages) {
                Date a = message.getSentDate();
    
                if (a.after(last) && message.getContent().toString().contains("token")) {
                    lastMessage = message;
                    letterContent = message.getContent().toString();
                    last = a;
    
                }
            }
            if (!letterContent.isEmpty()) {
                Matcher m = patt.matcher(letterContent);
                StringBuffer sb = new StringBuffer(letterContent.length());
                while (m.find()) {
                    passwd = m.group(1);
                    ReportWriter.info("Password: " + passwd);
                }
    
            }
        }
        catch (Exception e)
        {
            Assert.fail(ReportWriter.errorGettingMail(e.toString()));  
        }

        return passwd;
    }
}