package com.org.productplanner.rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.org.productplanner.beans.Patient;
import com.org.productplanner.service.CommonService;

public class PatientRowMapper extends CommonService implements RowMapper<Patient>{

	@Override
	public Patient mapRow(ResultSet rs, int rowNum) throws SQLException 
	{
		Patient patient=new Patient();
		patient.setId(rs.getString(2));
		patient.setName(rs.getString(3));
		patient.setMobile(rs.getString(4));
		patient.setStatus(replaceForGUI(rs.getString(5)));
		patient.setAddress(rs.getString(6));
		patient.setEffectiveDate(rs.getTimestamp(7));
		return patient;
	}

}
