package com.org.productplanner.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.org.productplanner.beans.Patient;
import com.org.productplanner.service.PatientService;

@Controller
@RequestMapping("/patient")
public class PatientController {

	@Autowired
	PatientService patientService;
	
	@RequestMapping(value="/{generateId}",method = RequestMethod.GET)
	public @ResponseBody Map<String,String> generateProductId()
	{
		return patientService.generateID("PNT_", "TABLE_PATIENT", "patientID");
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody List<Patient> get()
	{
		return patientService.getPatients();
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody void add(@RequestBody Patient patient)
	{
		patientService.addPatient(patient);
		
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	public @ResponseBody void update(@RequestBody Patient patient)
	{
		patientService.update(patient);
	}
	
	@RequestMapping(value="/{patientID}",method = RequestMethod.DELETE)
	public @ResponseBody void updateProducts(@PathVariable(value="patientID") String patientID)
	{
		patientService.deletePatient(patientID);
	}
}
