package com.org.productplanner.beans;

import java.util.Date;

@lombok.Setter
@lombok.Getter
@lombok.ToString
@lombok.NoArgsConstructor
public class Product {
	
	private int objid;
	
	private String productID;
	
	private String productDescription;
	
	private float rate;

	private String status;
	
	private float gst;
	
	private String productType;
	
	private String otherProductCode;
	
	private String otherProductDetails;
	
	private Date effectiveDate;

}
