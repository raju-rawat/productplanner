package com.example.productplanner;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.org.productplanner.beans.DeliveryNote;

public class PDFTest {
	private static Map<String,Font> fonts=new HashMap<String, Font>();
	private static List<String> invoiceHeader=new ArrayList<String>();
	private static List<String> deliveryNoteHeader=new ArrayList<String>();
	static{
		
		//Adding fonts
		fonts.put("Header", new Font(Font.FontFamily.TIMES_ROMAN,16,Font.BOLD));
		fonts.put("TableHeader", new Font(Font.FontFamily.TIMES_ROMAN,10,Font.BOLD));
		fonts.put("Para1", new Font(Font.FontFamily.TIMES_ROMAN,Font.DEFAULTSIZE,Font.NORMAL));
		
		//Adding Invoice table header
		invoiceHeader.add("Order No./Date");
		invoiceHeader.add("D.C No./Date");
		invoiceHeader.add("Product Name");
		invoiceHeader.add("Notation");
		invoiceHeader.add("Units");
		invoiceHeader.add("Rate");
		invoiceHeader.add("Total(Rs)");
		
		//Adding Delivery Note Header
		deliveryNoteHeader.add("S. no.");
		deliveryNoteHeader.add("Product Id");
		deliveryNoteHeader.add("Particulars");
		deliveryNoteHeader.add("Quantity");
		deliveryNoteHeader.add("Rate");
		deliveryNoteHeader.add("Discount");
		deliveryNoteHeader.add("Total");
		deliveryNoteHeader.add("GST");
		deliveryNoteHeader.add("Net Price");
}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		generatePDF();

	}

	public static void generatePDF()
	{
		
		
		
		System.out.println("===== Start ======");
		  
		  
		 Document document = new Document();
	     PdfWriter writer=null;
		try {
			writer = PdfWriter.getInstance(document, new FileOutputStream("pdf/DeliveryNoteDemo.pdf"));
			document.open();
			
			setDocumentHeader(document);
		    PdfPTable table = createTable(9);
	        addTableHeader(table);
	        
	        document.add(table);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			document.close();
		    writer.close();
		}
	     
	     System.out.println("===== End ======");
	}
	
	public static void addCol(PdfPTable table,Font font,String content)
	{
		PdfPCell cell = new PdfPCell(new Paragraph(content,font));
		cell.setBorderColor(BaseColor.BLACK);
		cell.setPaddingLeft(5);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);
	}
	
	public static void addTableHeader(PdfPTable table)
	{
		for(String header: deliveryNoteHeader)
		{
			addCol(table,fonts.get("TableHeader"),header);
		}
	}
	
	public static PdfPTable createTable(int colSize) throws DocumentException
	{
		PdfPTable table=new PdfPTable(colSize); // 3 columns.
        table.setWidthPercentage(100); //Width 100%
        table.setSpacingBefore(10f); //Space before table
        table.setSpacingAfter(10f); //Space after table
 
        //Set Column widths
        float[] columnWidths = new float[colSize];
        for(int i=0;i<columnWidths.length;i++)
        {
        	columnWidths[i]=1f;
        }
        table.setWidths(columnWidths);
		return table;
	}
	
	public static void setDocumentHeader(Document document) throws DocumentException
	{
		Paragraph header=new Paragraph("Confident Dental Laboratory Pvt. Ltd",fonts.get("Header"));
		header.setAlignment(Element.ALIGN_CENTER);
		document.add(header);
	    Rectangle pageSize=new Rectangle(PageSize.A4);
	    document.setPageSize(pageSize);
	}
	
	public static void addRow(PdfPTable table,DeliveryNote note)
	{
		//addCol(table,fonts.get("TableRow"),header);
	}
}
