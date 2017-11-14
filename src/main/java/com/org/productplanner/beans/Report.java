package com.org.productplanner.beans;

import java.sql.Date;
import java.util.List;

public class Report {

	private Date fromDate;
	
	private Date toDate;
	
	private List<Invoice> invoices;
	
	private List<Variant> listofVariantInstancesSum;
	
	private List<Float> variants;

	public Report()
	{
		
	}
	
	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public List<Invoice> getInvoices() {
		return invoices;
	}

	public void setInvoices(List<Invoice> invoices) {
		this.invoices = invoices;
	}

	public List<Variant> getListofVariantInstancesSum() {
		return listofVariantInstancesSum;
	}

	public void setListofVariantInstancesSum(List<Variant> listofVariantInstancesSum) {
		this.listofVariantInstancesSum = listofVariantInstancesSum;
	}

	public List<Float> getVariants() {
		return variants;
	}

	public void setVariants(List<Float> variants) {
		this.variants = variants;
	}
	
	
}
