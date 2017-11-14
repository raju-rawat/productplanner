package com.org.productplanner.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.org.productplanner.beans.Invoice;
import com.org.productplanner.beans.Receipt;
import com.org.productplanner.beans.Report;
import com.org.productplanner.beans.Variant;
import com.org.productplanner.service.InvoiceService;
import com.org.productplanner.service.ReceiptService;
import com.org.productplanner.views.ReceiptReportView;
import com.org.productplanner.views.SalesReportView;

@Controller
@RequestMapping("/reports")
public class ReportController {
	@Autowired
	private ReceiptService receiptService;
	
	@Autowired
	private InvoiceService invoiceService;
	
	@RequestMapping(value="/receipt",method = RequestMethod.POST)
	public ModelAndView exportReceipt(@RequestBody Receipt receipt,HttpServletResponse response)
	{
		System.out.println("Generating monthly reports ...");
		Map<String, Object> model = new HashMap<String, Object>();
        //Sheet Name
        model.put("sheetname", "TestSheetName");
        //Headers List
        List<String> headers = new ArrayList<String>();
        headers.add("S.NO");
        headers.add("RECEIPT DATE");
        headers.add("RECEIPT NO");
        headers.add("PARTY NAME");
        headers.add("AMOUNT RECIVED");
        model.put("headers", headers);
        //Results Table (List<Object[]>)
        int sno=0;
        List<String> row=null;
        List<List<String>> results = new ArrayList<List<String>>();
        List<Receipt> listofReceipt=receiptService.getReceipts(receipt);
        for (Receipt recpt : listofReceipt) {
        	row = new ArrayList<String>();
        	row.add((sno+1)+"");
        	row.add(recpt.getReceiptDate().toString());
        	row.add(recpt.getReceiptID());
        	row.add(recpt.getCustomerID());
        	row.add(recpt.getTotalAmtPaid()+"");
        	results.add(row);
		}
        model.put("results",results);
        response.setContentType( "application/ms-excel" );
        response.setHeader( "Content-disposition", "attachment; filename=MonthlyReport.xls" );

		return new ModelAndView(new ReceiptReportView(), model);
	}
	
	@RequestMapping(value="/sales",method = RequestMethod.POST)
	public ModelAndView exportSales(@RequestBody Report report,HttpServletResponse response)
	{
		System.out.println("Generating monthly sales reports ...");
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("companyName", invoiceService.getCompany().getName());
		model.put("fromDate", report.getFromDate());
		model.put("toDate", report.getToDate());
        //Sheet Name
        model.put("sheetname", "Sales Report");
        //Headers List
        List<String> headers = new ArrayList<String>();
        headers.add("S.NO");
        headers.add("BILL DATE");
        headers.add("INVOICE NO");
        headers.add("CUSTOMER NAME");
        headers.add("CUSTOMER GSTIN");
        headers.add("GROSS AMOUNT");
        headers.add("DISCOUNT AMOUNT");
        headers.add("TAXABLE AMOUNT");
        headers.add("CGST AMOUNT");
        headers.add("SGST ANOUNT");
        headers.add("IGST AMOUNT");
        headers.add("NET AMOUNT");
        
        
        //Header end
        
        //Rows
        List<Object> row=null;
        List<List<Object>> results = new ArrayList<List<Object>>();
        List<Invoice> invoices=report.getInvoices();
        List<String> listOfInvoiceIds=new ArrayList<String>();
        System.out.println("Invoice In Report "+invoices!=null?invoices.toString():"Invoice is null");
        for (Invoice invoice : invoices) {
        	listOfInvoiceIds.add(invoice.getInvoiceID());
        	row = new ArrayList<Object>();
        	row.add(invoice.getSno());
        	row.add(invoice.getInvoiceDate());
        	row.add(invoice.getInvoiceID());
        	row.add(invoice.getCustomerID());
        	row.add(invoice.getCustomerGSTIN());
        	row.add(invoice.getGrossAmount());
        	row.add(invoice.getDiscountAmount());
        	row.add(invoice.getTaxableAmount());
        	row.add(invoice.getCgstAmount());
        	row.add(invoice.getSgstAmount());
        	row.add(invoice.getIgstAmount());
        	row.add(invoice.getNetAmount());
        	for(float variant: invoice.getVariants())
        	{
        		row.add(variant);
        	}
        	results.add(row);
        	System.out.println("Rows ==> "+row.toString());
		}
        model.put("results", results);
        List<String> variantHeader=invoiceService.getVariants(listOfInvoiceIds);
        for (String variant : variantHeader) {
			headers.add("V - "+variant);
		}
        model.put("headers", headers);
        //End Of The Report
        //Header
      //Headers List
        List<String> headerSum = new ArrayList<String>();
        headerSum.add("VARIANT");
        headerSum.add("CGST");
        headerSum.add("CGST AMOUNT");
        headerSum.add("SGST");
        headerSum.add("SGST AMOUNT");
        headerSum.add("IGST");
        headerSum.add("IGST AMOUNT");
        model.put("headerSum", headerSum);
        //Content
        List<Object> rowSum=null;
        List<List<Object>> resultsSum = new ArrayList<List<Object>>();
        List<Variant> listofVariantInstancesSum=report.getListofVariantInstancesSum();
        for (Variant variantSum : listofVariantInstancesSum) {
        	rowSum=new ArrayList<Object>();
        	rowSum.add("IF GST IS "+variantSum.getName()+"% IN PRODUCT");
        	rowSum.add(variantSum.getCgst());
        	rowSum.add(variantSum.getCgstAmount());
        	rowSum.add(variantSum.getSgst());
        	rowSum.add(variantSum.getSgstAmount());
        	rowSum.add(variantSum.getIgst());
        	rowSum.add(variantSum.getIgstAmount());
        	resultsSum.add(rowSum);
		}
        model.put("resultsSum", resultsSum);
        String currentDate=invoiceService.getFormattedDate(new Date(), "dd-MM-yyy_hh-mm-ss");
        response.setContentType( "application/ms-excel" );
        response.setHeader( "Content-disposition", "attachment; filename="+"SalesReport_"+currentDate+".xls");

		return new ModelAndView(new SalesReportView(), model);
	}
	
}
