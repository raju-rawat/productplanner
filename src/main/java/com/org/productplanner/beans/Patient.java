package com.org.productplanner.beans;

import java.sql.Timestamp;

@lombok.NoArgsConstructor
@lombok.Getter
@lombok.Setter
public class Patient {

	private long objid;
	
	private String id;
	
	private String name;
	
	private String mobile;
	
	private String address;
	
	private String status;
	
	private Timestamp effectiveDate;
}
