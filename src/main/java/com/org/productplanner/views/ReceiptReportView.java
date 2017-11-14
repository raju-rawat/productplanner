package com.org.productplanner.views;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;


@SuppressWarnings("deprecation")
public class ReceiptReportView extends AbstractExcelView {
	@SuppressWarnings("unchecked")
    protected void buildExcelDocument(Map<String, Object> model,
            HSSFWorkbook workbook,
            HttpServletRequest request,
            HttpServletResponse response)
    {
        //VARIABLES REQUIRED IN MODEL
        String sheetName = (String)model.get("sheetname");
        List<String> headers = (List<String>)model.get("headers");
        List<List<String>> results = (List<List<String>>)model.get("results");
        List<String> numericColumns = new ArrayList<String>();
        if (model.containsKey("numericcolumns"))
            numericColumns = (List<String>)model.get("numericcolumns");
        //BUILD DOC
        HSSFSheet sheet = workbook.createSheet(sheetName);
        sheet.setDefaultColumnWidth((short) 12);
        int currentRow = 0;
        short currentColumn = 0;
        //CREATE STYLE FOR HEADER
        HSSFCellStyle headerStyle = workbook.createCellStyle();
        HSSFFont headerFont = workbook.createFont();
        headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        headerStyle.setFont(headerFont); 
        //POPULATE HEADER COLUMNS
        HSSFRow headerRow = sheet.createRow(currentRow);
        for(String header:headers){
            HSSFRichTextString text = new HSSFRichTextString(header);
            HSSFCell cell = headerRow.createCell(currentColumn); 
            cell.setCellStyle(headerStyle);
            cell.setCellValue(text);            
            currentColumn++;
        }
        //POPULATE VALUE ROWS/COLUMNS
        currentRow++;//exclude header
        for(List<String> result: results){
            currentColumn = 0;
            HSSFRow row = sheet.createRow(currentRow);
            for(String value : result){//used to count number of columns
                HSSFCell cell = row.createCell(currentColumn);
                if (numericColumns.contains(headers.get(currentColumn))){
                    cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                    cell.setCellValue(0);
                } else {
                    HSSFRichTextString text = new HSSFRichTextString(value);                
                    cell.setCellValue(text);                    
                }
                currentColumn++;
            }
            currentRow++;
        }
    }
}