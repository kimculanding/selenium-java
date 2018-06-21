package com.oberthur.tests.util;

import org.testng.Assert;
import org.w3c.dom.Document;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Viktozhu
 * Date: 05.09.13
 * Time: 17:52
 * To change this template use File | Settings | File Templates.
 */
public class DataLoader {
    public static Document readXMLFile(String fileName)
    {
        try {
            File XmlFile = new File(fileName);
            DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder domBuilder = domFactory.newDocumentBuilder();
            Document doc = domBuilder.parse(XmlFile);
            return doc;
        }
        catch (Exception e)
        {
            Assert.fail(ReportWriter.loadDataError("XML file: " + e.toString()));
        }
        return null;
    }

    /**
     * Read csv file looks like this:
     * param1,0,4,7
     * param2,7,8,9
     * ...
     */
    public static Map<String, String[]> readCsvFile(String fileName, String delimiter)
    {
        Map<String,String[]> map = new HashMap<String, String[]>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line = null;
            while ((line = reader.readLine()) != null){
                String[] paramArray = line.split(delimiter);
                map.put(paramArray[0], Arrays.copyOfRange(paramArray,1,paramArray.length));
            }
            reader.close();
        }
        catch (FileNotFoundException e)
        {
            Assert.fail(ReportWriter.loadDataError("TXT file:" + e.toString()));

        } catch (IOException e) {
            Assert.fail(ReportWriter.loadDataError("TXT file: " + e.toString()));
        }
        return map;
    }
    public static String[][] readXLSFile(String fileName, String xlsSheetName) {
        String[][] data = null;
        try {
            File datafile = new File(fileName);
            InputStream fis = new BufferedInputStream(new FileInputStream(datafile));
            HSSFWorkbook book = new HSSFWorkbook(fis);
            HSSFSheet sheet = book.getSheet(xlsSheetName);
            int rowNum = sheet.getLastRowNum() + 1;
            int colNum = sheet.getRow(0).getLastCellNum();
            data = new String[rowNum][colNum];

            for (int i = 0; i < rowNum; i++) {
                HSSFRow row = sheet.getRow(i);
                for (int j = 0; j < colNum; j++) {
                    HSSFCell cell = row.getCell(j);
                    String value = cellToString(cell);
                    data[i][j] = value;
                }
            }
            return data;
        }
        catch (FileNotFoundException e) {
            Assert.fail(ReportWriter.loadDataError("XLS file:" + e.toString()));

        } catch (IOException e) {
            Assert.fail(ReportWriter.loadDataError("XLS file: " + e.toString()));
        }
        return data;
    }

    private static String cellToString(HSSFCell cell) {
        Object result = null;
        try {

            switch (cell.getCellType()) {

                case Cell.CELL_TYPE_NUMERIC:
                    result = cell.getNumericCellValue();
                    break;

                case Cell.CELL_TYPE_STRING:
                    result = cell.getStringCellValue();
                    break;

                case Cell.CELL_TYPE_BOOLEAN:
                    result = cell.getBooleanCellValue();
                    break;

                case Cell.CELL_TYPE_FORMULA:
                    result = cell.getCellFormula();
                    break;

                default:
                    Assert.fail(ReportWriter.loadDataError("XLS file: Could not read cell"));
            }
            return result.toString();
        }
        catch (Exception e)
        {
            Assert.fail(ReportWriter.loadDataError("XLS file: " + e.toString()));
        }

        return result.toString();
    }

}
