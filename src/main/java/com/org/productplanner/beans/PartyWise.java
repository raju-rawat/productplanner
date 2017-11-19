package com.org.productplanner.beans;

import java.sql.Date;

public class PartyWise {
	
	private String transactionType;
	
	private Date transactionDate;
	
	private String referenceNumber;
	
	private float transactionAmount;
	
	private float transactionBalance;
	
	public PartyWise()
	{
		
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getReferenceNumber() {
		return referenceNumber;
	}

	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}

	public float getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(float transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public float getTransactionBalance() {
		return transactionBalance;
	}

	public void setTransactionBalance(float transactionBalance) {
		this.transactionBalance = transactionBalance;
	}

	@Override
	public String toString() {
		return "PartyWise [transactionType=" + transactionType + ", transactionDate=" + transactionDate
				+ ", referenceNumber=" + referenceNumber + ", transactionAmount=" + transactionAmount
				+ ", transactionBalance=" + transactionBalance + "]";
	}
	

}
