package com.oberthur.tests.util;

import br.eti.kinoshita.testlinkjavaapi.TestLinkAPI;
import br.eti.kinoshita.testlinkjavaapi.constants.ExecutionStatus;
import br.eti.kinoshita.testlinkjavaapi.constants.ExecutionType;
import br.eti.kinoshita.testlinkjavaapi.constants.TestCaseDetails;
import br.eti.kinoshita.testlinkjavaapi.model.Attachment;
import br.eti.kinoshita.testlinkjavaapi.model.ReportTCResultResponse;
import br.eti.kinoshita.testlinkjavaapi.model.TestCase;
import br.eti.kinoshita.testlinkjavaapi.util.TestLinkAPIException;
import com.oberthur.tests.util.PropertyLoader;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.testng.Assert;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class TestlinkIntegration {
    String url;
    String devKey;
    Integer testplanId;

    public TestlinkIntegration() {
        url = PropertyLoader.loadProperty("testLink.url");
        devKey = PropertyLoader.loadProperty("testLink.devkey");
        testplanId = Integer.parseInt(PropertyLoader.loadProperty("testLink.testplanId"));
    }

    public ArrayList<Integer> getTestcaseByTestplanId(int testPlanID)
    {
        ArrayList<Integer> testcaseNames = new ArrayList<Integer>();
        try {
            TestLinkAPI testlinkAPIClient = new TestLinkAPI(new URL(url), devKey);
            TestCase[] testcases = testlinkAPIClient.getTestCasesForTestPlan(testPlanID, null, null, null, null, null, null, null, ExecutionType.AUTOMATED, true, TestCaseDetails.FULL);

            for (TestCase testCase : testcases) {
                TestCase finalTestcase = testlinkAPIClient.getTestCase(testCase.getId(), null, null);
                testcaseNames.add(finalTestcase.getId());
            }
        }
        catch (Exception e)
        {
            Assert.fail(ReportWriter.errorTestLinkOperation(e.toString()));
        }

        return testcaseNames;
    }


    public void setResult(String testcaseId, ExecutionStatus status)
    {
        try {
            TestLinkAPI testlinkAPIClient = new TestLinkAPI(new URL(url), devKey);
            ReportTCResultResponse result = testlinkAPIClient.setTestCaseExecutionResult(Integer.parseInt(testcaseId), null, testplanId, status, null, null, null, true, null, null, null, null, false);
        }
        catch (Exception e)
        {
            Assert.fail(ReportWriter.errorTestLinkOperation(e.toString()));
        }

    }

    public void uploadAttachment(String testcaseId, String filename, String title, String description, String filetype)
    {
         try {
             TestLinkAPI testlinkAPIClient = new TestLinkAPI(new URL(url), devKey);

             File attachmentFile = new File(filename);
             String fileContents = null;
             byte[] byteArray = FileUtils.readFileToByteArray(attachmentFile);
             fileContents = new String(Base64.encodeBase64(byteArray));

             Attachment attachment = testlinkAPIClient.uploadExecutionAttachment(Integer.parseInt(testcaseId), title, description, filename, filetype, fileContents);

         }
        catch (Exception e)
        {
            Assert.fail(ReportWriter.errorTestLinkOperation(e.toString()));
        }
    }

    public void reportTestLinkSuccess(String tesId) {
        try {
            setResult(tesId, ExecutionStatus.PASSED);
        }
        catch (Exception e)
        {
            ReportWriter.errorTestLinkOperation(e.toString());
        }
    }

    public void reportTestLinkFailed(String tesId) {
        try {
            setResult(tesId, ExecutionStatus.FAILED);
        }
        catch (Exception e)
        {
            ReportWriter.errorTestLinkOperation(e.toString());
        }
    }
}


