package com.org.productplanner.beans;

import java.util.Date;

public class Customer {
	
	private int objid;
	
	private String customerID;
	
	private String customerName;
	
	private String address;
	
	private String phone;
	
	private String gst;
	
	private String panNumber;
	
	private String customerSPOC;
	
	private String state;
	
	private Date startDate;

	//Default constructor
	public Customer()
	{
		
	}
	
	public Customer(String customerID, String customerName)
	{
		this.customerID = customerID;
		this.customerName = customerName;
	}
	
	//Parametrized constructor
	public Customer(int objid, String customerID, String customerName, String address, String phone, String gst,
			String panNumber, String customerSPOC, String state, Date startDate) {

		this.objid = objid;
		this.customerID = customerID;
		this.customerName = customerName;
		this.address = address;
		this.phone = phone;
		this.gst = gst;
		this.panNumber = panNumber;
		this.customerSPOC = customerSPOC;
		this.state = state;
		this.startDate = startDate;
	}

	public int getObjid() {
		return objid;
	}

	public void setObjid(int objid) {
		this.objid = objid;
	}

	public String getCustomerID() {
		return customerID;
	}

	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getGst() {
		return gst;
	}

	public void setGst(String gst) {
		this.gst = gst;
	}

	public String getPanNumber() {
		return panNumber;
	}

	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}

	public String getCustomerSPOC() {
		return customerSPOC;
	}

	public void setCustomerSPOC(String customerSPOC) {
		this.customerSPOC = customerSPOC;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Override
	public String toString() {
		return "Customer [objid=" + objid + ", customerID=" + customerID + ", customerName=" + customerName
				+ ", address=" + address + ", phone=" + phone + ", gst=" + gst + ", panNumber=" + panNumber
				+ ", customerSPOC=" + customerSPOC + ", state=" + state + ", startDate=" + startDate + "]";
	}
	
	
	
}
