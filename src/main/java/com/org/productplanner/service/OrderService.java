package com.org.productplanner.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import static com.org.productplanner.queries.Query.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.org.productplanner.beans.DeliveryNote;
import com.org.productplanner.beans.Notes;
import com.org.productplanner.rowmappers.DeliveryNoteRowMapper;
import com.org.productplanner.rowmappers.NotesRowMapper;
import com.org.productplanner.rowmappers.SimpleNotesRowMapper;

import net.sf.jasperreports.engine.JRException;

@Service
public class OrderService extends CommonService{

	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	@Autowired
	ObjectMapper objectMapper;
	
	public Map<String,String> generateDeliveryNoteID(Date orderDate,boolean simple)
    {
    	if(simple)
    	{
    		return generateID("S_ORD_", "DELIVERY_X_NOTE_TBL", "deliveryNoteID", orderDate);
    	}
    	else
    	{
    		return generateID("ORD_", "DELIVERY_NOTE_TBL", "deliveryNoteID", orderDate);
    	}
    }
	
	public boolean download(HttpServletResponse response,DeliveryNote deliveryNote,boolean simple)
    {
		  try
		  {
			  System.out.println(deliveryNote.toString());
			  generatePDF(deliveryNote, response,simple);
			  
		  } 
		  catch (SQLException e)
		  {
		     e.printStackTrace();
		     
		  } 
		  catch (IOException ioe) {
			System.out.println("IOException in download Delivery Note ==> "+ioe);
		} catch (JRException jre) {
			System.out.println("JRException in download Delivery Note ==> "+jre);
		}
		  
		  return true;
    }
    
    public boolean save(DeliveryNote deliveryNote,boolean simple)
    {
    	System.out.println(deliveryNote.toString());
    	String saveDeliveryNote=simple?SAVE_X_DELIVERY_NOTE:SAVE_DELIVERY_NOTE;
    	String deliveryNoteTable=simple?"DELIVERY_X_NOTE_TBL":"DELIVERY_NOTE_TBL";
    	int output=0;
    	if(simple)
    	{
    		for(Notes note: deliveryNote.getNotes())
        	{
    			note.setObjid(getNEXTObjId("NOTE_X_TBL"));
        		output=jdbcTemplate.update(X_SAVE_NOTE,
        				note.getObjid(),
        				note.getProductID(),
        				note.getNotation(),
        				note.getQuantity(),
        				note.getRate(),
        				note.getTotal(),
        				note.getDiscount(),
        				note.getNetTotal(),
        				deliveryNote.getDeliveryNoteID()
        				);
        	}
    	}
    	else
    	{
    		for(Notes note: deliveryNote.getNotes())
        	{
    			note.setObjid(getNEXTObjId("NOTE_TBL"));
        		output=jdbcTemplate.update(SAVE_NOTE,
        				note.getObjid(),
        				note.getProductID(),
        				note.getNotation(),
        				note.getQuantity(),
        				note.getRate(),
        				note.getTotal(),
        				note.getDiscount(),
        				note.getNetTotal(),
        				note.getCgst(),
        				note.getCgstAmount(),
        				note.getSgst(),
        				note.getSgstAmount(),
        				note.getNetPrice(),
        				deliveryNote.getDeliveryNoteID()
        				);
        	}
    	}
    	deliveryNote.setObjid(getNEXTObjId(deliveryNoteTable));
    	output=jdbcTemplate.update(saveDeliveryNote, 
    			deliveryNote.getObjid(),
    			deliveryNote.getDeliveryNoteID(),
    			deliveryNote.getCustomerID(),
    			deliveryNote.getGrandTotal(),
    			deliveryNote.getDeliveryDate()
				);
	
	return output==1?true:false;
    }
    
    public void addNotes(List<Notes> listOfNotes,boolean simple)
    {
    	System.out.println("Adding notes in OrderId ...");
    	if(simple)
    	{
    		for(Object noteObj: listOfNotes)
        	{
    			Notes note=objectMapper.convertValue(noteObj, Notes.class);
    			note.setObjid(getNEXTObjId("NOTE_X_TBL"));
        		jdbcTemplate.update(X_SAVE_NOTE,
        				note.getObjid(),
        				note.getProductID(),
        				note.getNotation(),
        				note.getQuantity(),
        				note.getRate(),
        				note.getTotal(),
        				note.getDiscount(),
        				note.getNetTotal(),
        				note.getDeliveryNoteID()
        				);
        	}
    	}
    	else
    	{
    		for(Object noteObj: listOfNotes)
        	{
    			Notes note=objectMapper.convertValue(noteObj, Notes.class);
    			note.setObjid(getNEXTObjId("NOTE_TBL"));
        		jdbcTemplate.update(SAVE_NOTE,
        				note.getObjid(),
        				note.getProductID(),
        				note.getNotation(),
        				note.getQuantity(),
        				note.getRate(),
        				note.getTotal(),
        				note.getDiscount(),
        				note.getNetTotal(),
        				note.getCgst(),
        				note.getCgstAmount(),
        				note.getSgst(),
        				note.getSgstAmount(),
        				note.getNetPrice(),
        				note.getDeliveryNoteID()
        				);
        	}
    	}
    	System.out.println("Added Notes.");
    }
   
    public List<DeliveryNote> getDeliveryNote(String customerId,boolean simple)
    {
    	System.out.println("Customer Id: "+customerId+" taxable: "+simple);
    	List<DeliveryNote> deliveryNotes=null;
    	String query=simple?GET_X_DELIVERY_NOTES:GET_DELIVERY_NOTES;
    	
    	deliveryNotes=jdbcTemplate.query(query, new Object[]{customerId}, new DeliveryNoteRowMapper());
    	
    	for(DeliveryNote dn:deliveryNotes)
		{
			System.out.println("Delivery Note==> "+dn.toString());
		}
    	return deliveryNotes;
    }
    
    public List<Notes> getNotes(String deliveryNoteId,boolean simple)
    {
    	List<Notes> notes=null;
    	if(simple)
    	{
    		notes=jdbcTemplate.query(GET_X_NOTES_FROM_ID,new Object[]{deliveryNoteId},new SimpleNotesRowMapper());
    	}
    	else
    	{
    		notes=jdbcTemplate.query(GET_NOTES_FROM_ID,new Object[]{deliveryNoteId},new NotesRowMapper());
    	}
    	return notes;
    }
    
    @SuppressWarnings("unchecked")
	public void updateOrder(Map<String,Object> orderMap)
    {
    	boolean simple=(boolean) orderMap.get("strSimple");
    	
    	
    	/**
    	 * Updating Orders with grand tatol
    	 */
    	List<DeliveryNote> listOforders=(List<DeliveryNote>) orderMap.get("updatedOrders");
    	if(listOforders!=null)
    	{
        	updateOrder(listOforders, simple);
    	}
    	/**
    	 * Adding notes to the existing orders
    	 */
    	List<Notes> listOfNotes= (List<Notes>) orderMap.get("newNotes");
		if(listOfNotes!=null)
		{
        	addNotes(listOfNotes, simple);
		}
    	/**
    	 * Deleting Orders and related Notes
    	 */
    	List<String> listOfOrderId=(List<String>) orderMap.get("deletedOrders");
    	if(listOfOrderId!=null)
    	{
    		deleteOrder(listOfOrderId, simple);
    	}
    	
    	/**
    	 * Removing Notes from existing Orders
    	 */
    	List<Integer> listOfNoteId=(List<Integer>) orderMap.get("deletedOrderNotes");
    	if(listOfNoteId!=null)
    	{
    		deleteNotes(listOfNoteId,simple);
    	}
    	
    	/**
    	 * Updating Note in existing Orders
    	 */
    	List<Notes> updatablelistOfNotes=(List<Notes>) orderMap.get("updatedOrderNotes");
    	if(listOfNotes!=null)
    	{
    		updateNotes(updatablelistOfNotes, simple);
    	}
    	
    }
    
    public void updateOrder(List<DeliveryNote> listOfOrders,boolean simple)
    {
		System.out.println("Updating Order ...");
		for (Object orderMap : listOfOrders) {
			DeliveryNote order=objectMapper.convertValue(orderMap, DeliveryNote.class);
			String updateOrder=simple?UPDATE_X_ORDER:UPDATE_ORDER;
	    	jdbcTemplate.update(updateOrder, order.getGrandTotal(),new Date(),order.getObjid());
		}
    	
    	System.out.println("Order Updated.");
    }
    
    private void deleteNotes(List<Integer> listOfNotes, boolean simple) 
    {
    	System.out.println("Deleting Notes ...");
    	String deleteNote=simple ? DELETE_X_NOTE_BY_OBJID : DELETE_NOTE_BY_OBJID;
    	for (Integer orderId : listOfNotes) {
    		jdbcTemplate.update(deleteNote, orderId);
		}
    	System.out.println("Deleted Notes.");
	}
    
    public void updateNotes(List<Notes> notes,boolean simple)
    {
    	System.out.println("Updating Note in existing Order ...");
    	if(simple)
    	{
    		for(Object noteObj: notes)
    		{
    			Notes note=objectMapper.convertValue(noteObj, Notes.class);
    			System.out.println("Note without tax ==> "+note.toString());
    			jdbcTemplate.update(UPDATE_X_NOTE,
    					note.getNotation(),
    					note.getQuantity(),
    					note.getRate(),
    					note.getTotal(),
    					note.getDiscount(),
    					note.getNetTotal(),
    					note.getObjid()
        				);
    		}
    	}
    	else
    	{
    		for(Object noteObj: notes)
    		{
    			Notes note=objectMapper.convertValue(noteObj, Notes.class);
    			System.out.println("Note with tax ==> "+note.toString());
    			jdbcTemplate.update(UPDATE_NOTE,
    					note.getNotation(),
    					note.getQuantity(),
    					note.getRate(),
    					note.getTotal(),
    					note.getDiscount(),
    					note.getNetTotal(),
    					note.getCgstAmount(),
    					note.getSgstAmount(),
    					note.getNetPrice(),
    					note.getObjid() 					
    					);
    		}
    	}
    	System.out.println("Updated Notes.");
    }
    
    public void deleteOrder(List<String> listOfOrders,boolean simple)
    {
    	System.out.println("Deleting Orders ...");
    	String deleteNote=simple?DELETE_X_NOTE:DELETE_NOTE;
    	String deleteDeliveryNote=simple?CANCEL_X_DELIVERY_NOTE:CANCEL_DELIVERY_NOTE;
    	for(String orderId: listOfOrders)
    	{
    		jdbcTemplate.update(deleteNote, orderId);
    		jdbcTemplate.update(deleteDeliveryNote, orderId);
    	}
    	System.out.println("Orders Deleted.");
    }
    
}
