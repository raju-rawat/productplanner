package com.org.productplanner.repository;

import static com.org.productplanner.queries.Query.*;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.org.productplanner.beans.Patient;
import com.org.productplanner.rowmappers.PatientRowMapper;

@Repository
public class PatientRepository {

	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	public void addPatient(Patient patient) {
		jdbcTemplate.update(ADD_PATIENT, new Object[]
    			{
    				patient.getObjid(),
    				patient.getId(),
    				patient.getName(),
    				patient.getMobile(),
    				patient.getStatus(),
    				patient.getAddress(),
    				patient.getEffectiveDate()
        		});
	}

	public List<Patient> getPatients() 
	{
		return jdbcTemplate.query(GET_PATIENTS,new PatientRowMapper());
	}

	public void updateProducts(Patient patient) 
	{
		jdbcTemplate.update(UPDATE_PATIENT, 
				patient.getName(),
				patient.getMobile(),
				patient.getStatus(),
				patient.getAddress(),
				new Date(),
				patient.getId());
	}

	public void deleteProducts(String patientID) 
	{
		jdbcTemplate.update(DELETE_PATIENT,patientID);
	}

}
