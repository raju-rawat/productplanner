package com.org.productplanner.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.org.productplanner.beans.Customer;
import com.org.productplanner.service.CustomerService;

@Controller
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	CustomerService customerService;
	
	@RequestMapping(value="/generate/Id",method = RequestMethod.GET)
	public @ResponseBody Map<String,String> generateCustomerId()
	{
		return customerService.generateID("CUST_", "CUSTOMER_TBL", "customerID");
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody void save(@RequestBody Customer customer)
	{
		customerService.addCustomer(customer);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody List<Customer> getCustomer()
	{
		return customerService.getCustomers();
	}
	
	@RequestMapping(value="/all",method = RequestMethod.GET)
	public @ResponseBody List<Customer> getAllCustomer()
	{
		return customerService.getAllCustomer();
	}
	
	@RequestMapping(value="/update",method = RequestMethod.POST)
	public @ResponseBody void updateCustomer(@RequestBody Map<String,Object> customerMap)
	{
		customerService.updateCustomer(customerMap);
	}
}
