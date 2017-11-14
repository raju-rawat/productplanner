package com.org.productplanner.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.org.productplanner.beans.Customer;
import com.org.productplanner.rowmappers.CustomerRowMapper;

import static com.org.productplanner.queries.Query.*;

import java.util.Date;
import java.util.List;

@Repository
@Transactional
public class CustomerRepository{

	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	public void save(Customer customer)
    {
    	jdbcTemplate.update(ADD_CUSTOMER, new Object[]
        			{
        					customer.getObjid(),
        					customer.getCustomerID(),
        					customer.getCustomerName(),
        					customer.getAddress(),
        					customer.getPhone(),
        					customer.getGst(),
        					customer.getPanNumber(),
        					customer.getCustomerSPOC(),
        					customer.getState(),
        					customer.getStartDate()
            			});
    }
    
    public List<Customer> getCustomerIdAndName()
    {
    	List<Customer> customers=jdbcTemplate.query("SELECT CUSTOMER_ID,CUSTOMER_NAME FROM CUSTOMER_TBL",
                				(rs, rowNum) -> new Customer(rs.getString("CUSTOMER_ID"),rs.getString("CUSTOMER_NAME")));
    	
    	return customers;
    }
    public void delete(String listOfCustomerIds)
	{
    	jdbcTemplate.update(DELETE_CUSTOMERS.replaceAll("#",listOfCustomerIds));
	}
    
	public void update(List<Customer> listOfCustomers)
	{
		for (Object obj : listOfCustomers) {
			Customer customer=objectMapper.convertValue(obj, Customer.class);
    		jdbcTemplate.update(UPDATE_CUSTOMER, 
    				customer.getCustomerName(),
    				customer.getAddress(),
    				customer.getPhone(),
    				customer.getGst(),
    				customer.getPanNumber(),
    				customer.getCustomerSPOC(),
    				customer.getState(),
    				new Date(),
    				customer.getCustomerID());
		}
	}
	
	public List<Customer> getAll()
	{
		return jdbcTemplate.query(GET_CUSTOMER, new CustomerRowMapper());
	}
	
}
