package com.org.productplanner.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.org.productplanner.beans.Customer;
import com.org.productplanner.repository.CustomerRepository;

@Service
public class CustomerService extends CommonService{
	
	@Autowired
	private CustomerRepository customerRepository;
	
    public void addCustomer(Customer customer)
    {
    	customer.setObjid(getNEXTObjId("CUSTOMER_TBL"));
    	customerRepository.save(customer);
    }
    
    public List<Customer> getCustomers()
    {
    	return customerRepository.getCustomerIdAndName();
    }
    
    public List<Customer> getAllCustomer()
    {
    	return customerRepository.getAll();
    }
    
    @SuppressWarnings("unchecked")
	public void updateCustomer(Map<String,Object> customerMap)
    {
    	List<String> listOfCustomerIds=(List<String>) customerMap.get("deletedCustomers");
    	List<Customer> listOfCustomers=(List<Customer>) customerMap.get("updatedCustomers");
    	if(listOfCustomerIds!=null && !listOfCustomerIds.isEmpty())
    	{
    		customerRepository.delete(formatString(listOfCustomerIds));
    	}
    	if(listOfCustomers!=null && !listOfCustomers.isEmpty())
    	{
    		customerRepository.update(listOfCustomers);
    	}
    }
}
