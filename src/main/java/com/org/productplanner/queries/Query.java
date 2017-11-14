package com.org.productplanner.queries;

public class Query {

	public static final String GET_PASSWORD="SELECT password FROM table_user WHERE user_id= ?";
	
	/*
	 * Products
	 */
	
	public static final String GET_PRODUCTS="SELECT * FROM PRODUCT_TBL";
	
	public static final String ADD_PRODUCT=new StringBuilder()
										   .append("INSERT INTO PRODUCT_TBL(OBJID,PROD_ID,PROD_DESC,RATE,STATUS,")
										   .append("GST,PRODUCT_TYPE,OTHER_PRODUCT_CODE,OTHER_PRODUCT_DETAILS,EFFECTIVE_DATE)")
										   .append("VALUES(?,?,?,?,?,?,?,?,?,?)").toString();
	
	public static final String DELETE_PRODUCTS="DELETE FROM PRODUCT_TBL WHERE PROD_ID in (#) ";
	
	public static final String UPDATE_PRODUCT="UPDATE PRODUCT_TBL SET PROD_DESC= ? ,RATE = ? ,STATUS = ?,GST= ? ,PRODUCT_TYPE = ?,OTHER_PRODUCT_CODE = ?,MODIFIED_DATE =  ? where PROD_ID = ? ";
	
	public static final String GET_VARIANTS="select TAX from view_billing_details where invoice_id in(#) group by (TAX) order by TAX";
	
	/*
	 * Customer
	 */
	public static final String ADD_CUSTOMER=new StringBuilder()
											.append("INSERT INTO CUSTOMER_TBL(OBJID,CUSTOMER_ID,CUSTOMER_NAME,")
											.append("ADDRESS,PHONE_NUMBER,GST,PAN_NUMBER,CUSTOMER_SPOC,")
											.append("STATE,START_DATE) VALUES(?,?,?,?,?,?,?,?,?,?)").toString();
	
	public static final String DELETE_CUSTOMERS="DELETE FROM CUSTOMER_TBL WHERE CUSTOMER_ID in (#)";
	
	public static final String UPDATE_CUSTOMER="UPDATE CUSTOMER_TBL SET CUSTOMER_NAME = ?,ADDRESS = ?,PHONE_NUMBER = ?,GST = ?,PAN_NUMBER = ?,CUSTOMER_SPOC = ?,STATE = ?,MODIFIED_DATE = ? WHERE CUSTOMER_ID = ?";
	
	public static final String GET_CUSTOMER_STATE="SELECT STATE FROM CUSTOMER_TBL WHERE CUSTOMER_ID= ? ";
	
	public static final String GET_CUSTOMER="SELECT * FROM CUSTOMER_TBL";
	
	public static final String GET_CUSTOMER_ID_FROM_INVOICE_ID = "select CUSTOMER_ID from INVOICE_TBL where INVOICE_ID= ?  ";
	
	/*
	 * User
	 */
	public static final String ADD_USER=new StringBuilder()
										.append("INSERT INTO table_user(OBJID,USER_ID,USER_NAME,PASSWORD,")
										.append("ADDRESS,PHONE,STATUS,USER_TYPE,CREATE_DATE)")
										.append("values(?,?,?,?,?,?,?,?,?)").toString();
	
	/*
	 * Note with tax
	 */
	public static final String SAVE_NOTE=new StringBuilder()
			.append(" INSERT INTO NOTE_TBL(OBJID,PRODUCT_ID,NOTATION,QUANTITY,")
			.append(" RATE,TOTAL,DISCOUNT,NET_TOTAL,TAX_1,TAX_1_AMT,TAX_2,TAX_2_AMT,NET_AMOUNT,DELIVERY_NOTE_ID) ")
			.append(" VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)").toString();

	public static final String GET_NOTES="select * from NOTE_TBL where DELIVERY_NOTE_ID= ? ";

	public static final String GET_NOTES_FROM_ID="select prod.PROD_DESC, prod.OTHER_PRODUCT_CODE,note.* from NOTE_TBL note,PRODUCT_TBL prod where note.PRODUCT_ID=prod.PROD_ID and note.DELIVERY_NOTE_ID= ? ";

	public static final String DELETE_NOTE="DELETE FROM NOTE_TBL WHERE DELIVERY_NOTE_ID = ? ";
	
	public static final String DELETE_NOTE_BY_OBJID="DELETE FROM NOTE_TBL WHERE OBJID = ? ";
	
	public static final String UPDATE_NOTE="UPDATE NOTE_TBL SET NOTATION = ? , QUANTITY = ? ,RATE = ? ,TOTAL = ? ,DISCOUNT = ? ,NET_TOTAL = ? ,TAX_1_AMT = ? ,TAX_2_AMT = ? ,NET_AMOUNT = ? WHERE OBJID = ? ";
	/*
	 * Note without tax
	 */
	public static final String X_SAVE_NOTE=new StringBuilder()
			.append(" INSERT INTO NOTE_X_TBL(OBJID,X_PRODUCT_ID,X_NOTATION,X_QUANTITY,")
			.append(" X_RATE,X_TOTAL,X_DISCOUNT,X_NET_TOTAL,X_DELIVERY_NOTE_ID) ")
			.append(" VALUES(?,?,?,?,?,?,?,?,?)").toString();
	
	
	public static final String GET_X_NOTES="select * from NOTE_X_TBL where X_DELIVERY_NOTE_ID= ? ";
	
	public static final String GET_X_NOTES_FROM_ID="select prod.PROD_DESC, prod.OTHER_PRODUCT_CODE,note.* from NOTE_X_TBL note,PRODUCT_TBL prod where note.X_PRODUCT_ID=prod.PROD_ID and note.X_DELIVERY_NOTE_ID= ? ";

	public static final String DELETE_X_NOTE="DELETE FROM NOTE_X_TBL WHERE X_DELIVERY_NOTE_ID = ? ";
	
	public static final String DELETE_X_NOTE_BY_OBJID="DELETE FROM NOTE_X_TBL WHERE OBJID = ? ";
	
	public static final String UPDATE_X_NOTE="UPDATE NOTE_X_TBL SET X_NOTATION = ? , X_QUANTITY = ? ,X_RATE = ? ,X_TOTAL = ? ,X_DISCOUNT = ? ,X_NET_TOTAL = ? WHERE OBJID = ? ";
	/*
	 * Delivery Note with tax
	 */
	
	public static final String GET_VARIANT_INSTANCES="select sum(TAX_AMOUNT) from view_billing_details where invoice_id= ? group by (TAX) order by TAX";
	
	public static final String GET_SUMMATION_OF_VARIANTS=new StringBuilder()
															.append(" select GST,round(sum(cgst),2) as CGST,round(sum(sgst),2) as SGST,round(sum(igst),2) as IGST ")
															.append(" from (select p.gst as gst, 0 as cgst ,0 as sgst, sum(n.tax_1_amt+n.TAX_2_AMT) as igst ")
															.append(" from note_tbl n,product_tbl p where n.product_id =p.prod_id ")
															.append(" and n.delivery_note_id  in ")
															.append(" (select d1. delivery_note_id from delivery_note_tbl d1 ")
															.append(" where d1.invoice_id  in ( # ) ")
															.append(" and customer_id in (select customer_id from customer_tbl where state  <> (select state from company_tbl))) ")
															.append(" group by p.gst union all ")
															.append(" (select p.gst as gst,sum(n.tax_1_amt) as cgst ,sum(n.TAX_2_AMT) as sgst, 0 as igst ")
															.append(" from note_tbl n,product_tbl p ")
															.append(" where n.product_id =p.prod_id and n.delivery_note_id  in ")
															.append(" (select d1. delivery_note_id from delivery_note_tbl d1 ")
															.append(" where d1.invoice_id  in  ( # ) ")
															.append(" and customer_id in (select customer_id from customer_tbl where state  = (select state from company_tbl)) ")
															.append(" ) group by p.gst ) ) tab group by GST ")
															.toString();
	
	public static final String SAVE_DELIVERY_NOTE="INSERT INTO DELIVERY_NOTE_TBL(OBJID,DELIVERY_NOTE_ID,CUSTOMER_ID,GRAND_TOTAL,DELIVERY_DATE,STATUS) VALUES(?,?,?,?,?,'ACTIVE')";

	public static final String CANCEL_DELIVERY_NOTE="UPDATE DELIVERY_NOTE_TBL SET STATUS = 'CANCELLED',GRAND_TOTAL = 0 WHERE DELIVERY_NOTE_ID = ? ";
	
	public static final String UPDATE_ORDER="UPDATE DELIVERY_NOTE_TBL SET GRAND_TOTAL = ? ,UPDATED_DATE = ? WHERE OBJID = ?";
	
	public static final String UPDATE_DELIVERY_NOTE="UPDATE DELIVERY_NOTE_TBL SET INVOICE_GENERATED='Y',INVOICE_ID= ? where DELIVERY_NOTE_ID= ? ";

	public static final String GET_DELIVERY_NOTE="select * from DELIVERY_NOTE_TBL where CUSTOMER_ID= ? and DATE_FORMAT(DELIVERY_DATE, '%Y-%m-%d') >= ? and DATE_FORMAT(DELIVERY_DATE, '%Y-%m-%d') <= ? and INVOICE_GENERATED='N' ";
	
	public static final String GET_DN_FOR_INV_GEN="select * from DELIVERY_NOTE_TBL where INVOICE_ID= ? and INVOICE_GENERATED='Y' ";
	
	public static final String GET_DELIVERY_NOTES="select OBJID,DELIVERY_NOTE_ID,GRAND_TOTAL,INVOICE_GENERATED,DELIVERY_DATE,STATUS from DELIVERY_NOTE_TBL where CUSTOMER_ID= ? order by DELIVERY_DATE desc ";
	
	/*
	 * Delivery Note without tax
	 */
	public static final String SAVE_X_DELIVERY_NOTE="INSERT INTO DELIVERY_X_NOTE_TBL(OBJID,X_DELIVERY_NOTE_ID,X_CUSTOMER_ID,X_GRAND_TOTAL,X_DELIVERY_DATE,X_STATUS) VALUES(?,?,?,?,?,'ACTIVE')";	
	
	public static final String CANCEL_X_DELIVERY_NOTE="UPDATE DELIVERY_X_NOTE_TBL SET X_STATUS = 'CANCELLED',X_GRAND_TOTAL = 0 WHERE X_DELIVERY_NOTE_ID = ? ";
	
	public static final String UPDATE_X_ORDER="UPDATE DELIVERY_X_NOTE_TBL SET X_GRAND_TOTAL = ? ,X_UPDATED_DATE = ? WHERE OBJID = ? ";
	
	public static final String UPDATE_X_DELIVERY_NOTE="UPDATE DELIVERY_X_NOTE_TBL SET X_INVOICE_GENERATED='Y',X_INVOICE_ID= ? where X_DELIVERY_NOTE_ID= ? ";
	
	public static final String GET_X_DELIVERY_NOTE="select * from DELIVERY_X_NOTE_TBL where X_CUSTOMER_ID= ? and DATE_FORMAT(X_DELIVERY_DATE, '%Y-%m-%d') >= ? and DATE_FORMAT(X_DELIVERY_DATE, '%Y-%m-%d') <= ? and X_INVOICE_GENERATED='N' ";
	
	public static final String GET_X_DN_FOR_INV_GEN="select * from DELIVERY_X_NOTE_TBL where X_INVOICE_ID= ? and X_INVOICE_GENERATED='Y' ";
	
	public static final String GET_X_DELIVERY_NOTES="select OBJID,X_DELIVERY_NOTE_ID,X_GRAND_TOTAL,X_INVOICE_GENERATED,X_DELIVERY_DATE,X_STATUS from DELIVERY_X_NOTE_TBL where X_CUSTOMER_ID= ? order by X_DELIVERY_DATE desc ";
	
	/*
	 * Invoice with tax
	 */
	public static final String GET_INVOICE_DISCOUNT="select sum(TOTAL) - sum(NET_TOTAL) from view_billing_details where invoice_id= ? ";
	
	public static final String SAVE_INVOICE="insert into INVOICE_TBL(OBJID,INVOICE_ID,CUSTOMER_ID,GROSS_AMOUNT,CGST_AMOUNT,SGST_AMOUNT,GST_AMOUNT,INVOICE_AMOUNT,INVOICE_DATE) values(?,?,?,?,?,?,?,?,?)";

	public static final String GET_INVOICE="SELECT INVOICE_ID,INVOICE_DATE,INVOICE_AMOUNT,BALANCE FROM INVOICE_TBL WHERE CUSTOMER_ID= ? ";

	public static final String GET_INVOICE_REPORT=new StringBuilder()
			.append(" select cust.CUSTOMER_NAME,cust.GST,inv.* from INVOICE_TBL inv,CUSTOMER_TBL cust where inv.CUSTOMER_ID=cust.CUSTOMER_ID")
			.append(" and DATE_FORMAT(inv.INVOICE_DATE, '%Y-%m-%d') >= ? and DATE_FORMAT(inv.INVOICE_DATE, '%Y-%m-%d') <= ? ").toString();

	public static final String UPDATE_INVOICE="update INVOICE_TBL set BALANCE= ? , RECEIPT_ID = ? where INVOICE_ID= ? ";
	/*
	 * Invoice without tax
	 */
	public static final String GET_X_INVOICE_DISCOUNT="select sum(X_TOTAL) - sum(X_NET_TOTAL) from x_view_billing_details where x_invoice_id= ? ";
	
	public static final String GET_X_INVOICE="SELECT X_INVOICE_ID,X_INVOICE_DATE,X_GROSS_AMOUNT,X_BALANCE FROM INVOICE_X_TBL WHERE X_CUSTOMER_ID= ? ";
	
	public static final String GET_X_INVOICE_REPORT=new StringBuilder()
			.append(" select cust.CUSTOMER_NAME,cust.GST,inv.* from INVOICE_X_TBL inv,CUSTOMER_TBL cust where inv.X_CUSTOMER_ID=cust.CUSTOMER_ID")
			.append(" and DATE_FORMAT(inv.X_INVOICE_DATE, '%Y-%m-%d') >= ? and DATE_FORMAT(inv.X_INVOICE_DATE, '%Y-%m-%d') <= ? ").toString();
	
	public static final String SAVE_X_INVOICE="insert into INVOICE_X_TBL(OBJID,X_INVOICE_ID,X_CUSTOMER_ID,X_GROSS_AMOUNT,X_INVOICE_DATE) values(?,?,?,?,?)";

	public static final String UPDATE_X_INVOICE="update INVOICE_X_TBL set X_BALANCE= ? , X_RECEIPT_ID = ? where X_INVOICE_ID= ? ";
	/*
	 * Receipt
	 */
	public static final String SAVE_RECEIPT="insert into TABLE_RECEIPT(OBJID,RECEIPT_ID,CUSTOMER_ID,OPENING_BAL,RECEIVED_AMT,CLOSING_BAL,TRANSACTION_TYPE,COMMENTS,RECEIPT_DATE) values(?,?,?,?,?,?,?,?,?)";

	public static final String GET_RECEIPT_REPORT=new StringBuilder()
			.append("select cust.CUSTOMER_NAME,rec.RECEIPT_ID,rec.RECEIVED_AMT,rec.RECEIPT_DATE ")
			.append("from TABLE_RECEIPT rec,CUSTOMER_TBL cust ")
			.append("where cust.CUSTOMER_ID=rec.CUSTOMER_ID ")
			.append("and DATE_FORMAT(rec.RECEIPT_DATE, '%Y-%m-%d') >= ? and DATE_FORMAT(rec.RECEIPT_DATE, '%Y-%m-%d') <= ?").toString();
	/*
	 * Company
	 */
	public static final String GET_COMPANY_STATE="SELECT STATE FROM COMPANY_TBL";
	
	public static final String GET_COMPANY="select company.COMPANY_ID,company.COMPANY_NAME,company.ADDRESS,company.GSTIN,company.MOBILE,company.PRIMARY_PHONE,company.SECONDARY_PHONE,company.EMAIL,company.STATE from COMPANY_TBL company";
	
	/*
	 * Next ObjID
	 */
	public static final String GET_NEXT_OBJID="SELECT IFNULL(MAX(OBJID),0)+1 FROM ";
	
	/*
	 * States
	 */
	public static final String GET_STATES="SELECT state FROM STATE_TBL";
	
}
