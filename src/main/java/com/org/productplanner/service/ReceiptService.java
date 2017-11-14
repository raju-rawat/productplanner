package com.org.productplanner.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.org.productplanner.beans.Invoice;
import com.org.productplanner.beans.Receipt;
import com.org.productplanner.rowmappers.ReceiptRowMapper;

import net.sf.jasperreports.engine.JRException;

import static com.org.productplanner.queries.Query.*;

@Service
public class ReceiptService extends CommonService{

	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	public Map<String,String> generateReceiptId(Date receiptDate,boolean simple)
    {
		System.out.println("Invoice Date: "+receiptDate+" Simple: "+simple);
    	if(simple)
    	{
    		return generateID("S_REC_", "TABLE_RECEIPT", "receiptID", receiptDate);
    	}
    	else
    	{
    		return generateID("REC_", "TABLE_RECEIPT", "receiptID", receiptDate);
    	}
		
    }
	
	public boolean saveReceipt(Receipt receipt,boolean simple)
	{
		System.out.println("Receipt : "+receipt.toString());
		String updateInvoice=simple?UPDATE_X_INVOICE:UPDATE_INVOICE;
		receipt.setObjid(getNEXTObjId("TABLE_RECEIPT"));
		int output= jdbcTemplate.update(SAVE_RECEIPT, 
				receipt.getObjid(),
				receipt.getReceiptID(),
				receipt.getCustomerID(),
				receipt.getOpeningBalance(),
				receipt.getTotalAmtPaid(),
				receipt.getClosingBalance(),
				receipt.getTransactionType(),
				receipt.getComment(),
				receipt.getReceiptDate()
				);
		
		for (Invoice invoice : receipt.getInvoices()) {
			jdbcTemplate.update(updateInvoice,invoice.getCurrentBalance(),receipt.getReceiptID(),invoice.getInvoiceID());
		}
		return output==1?true:false;
	}
	
	public List<Receipt> getReceipts(Receipt receipt)
	{
		return jdbcTemplate.query(GET_RECEIPT_REPORT, 
				new Object[]{receipt.getFromDate(),receipt.getToDate()},
				new ReceiptRowMapper());
	}

	public void downloadReceipt(Receipt receipt,HttpServletResponse response) 
	{
		System.out.println("Downloading receipt for : "+receipt.toString());
		try {
			generatePDF(receipt, response, false);
		} catch (JRException | IOException | SQLException e) {
			
			e.printStackTrace();
		}
	}
}
