package com.org.productplanner.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.org.productplanner.beans.Patient;
import com.org.productplanner.repository.PatientRepository;

@Service
public class PatientService extends CommonService{

	@Autowired
	private PatientRepository patientRepository;
	
	public List<Patient> getPatients() 
	{
		return patientRepository.getPatients();
	}
	
	public void addPatient(Patient patient) 
	{
		patient.setObjid(getNEXTObjId("TABLE_PATIENT"));
		patient.setStatus(replaceForDB(patient.getStatus()));
		patientRepository.addPatient(patient);
	}
	
	public void deletePatient(String patientID) 
	{
		patientRepository.deleteProducts(patientID);
	}

	public void update(Patient patient) 
	{
		patient.setStatus(replaceForDB(patient.getStatus()));
		patientRepository.updateProducts(patient);
	}

}
