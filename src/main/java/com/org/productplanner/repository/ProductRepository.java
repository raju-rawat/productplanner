package com.org.productplanner.repository;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import static com.org.productplanner.queries.Query.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.org.productplanner.beans.Product;

@Repository
@Transactional
public class ProductRepository {

	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	public boolean addProduct(Product product)
    {
    	int output=jdbcTemplate.update(ADD_PRODUCT, new Object[]
        			{
        					product.getObjid(),
            				product.getProductID(),
            				product.getProductDescription(),
            				product.getRate(),
            				product.getStatus(),
            				product.getGst(),
            				product.getProductType(),
            				product.getOtherProductCode(),
            				product.getOtherProductDetails(),
            				product.getEffectiveDate()
            		});
    	
    	return output==1?true:false;
    }
	
	public List<Product> getProducts()
    {
    	List<Product> products=jdbcTemplate.query(GET_PRODUCTS,
                				(rs, rowNum) -> new Product(
                						rs.getInt("OBJID"),
                						rs.getString("PROD_ID"),
                						rs.getString("PROD_DESC"),
                						rs.getInt("RATE"),
                						rs.getString("STATUS"),
                						rs.getInt("GST"),
                						rs.getString("PRODUCT_TYPE"),
                						rs.getString("OTHER_PRODUCT_CODE"),
                						rs.getDate("EFFECTIVE_DATE")));
    	
    	return products;
    }
	
	public void updateProducts(List<Product> listOfProducts)
    {
    	for (Object obj : listOfProducts) {
    		Product product=objectMapper.convertValue(obj, Product.class);
    		jdbcTemplate.update(UPDATE_PRODUCT, 
    				product.getProductDescription(),
    				product.getRate(),
    				product.getStatus(),
    				product.getGst(),
    				product.getProductType(),
    				product.getOtherProductCode(),
    				new Date(),
    				product.getProductID());
		}
    }
    
    public void deleteProducts(String listOfProductIds)
    {
    	jdbcTemplate.update(DELETE_PRODUCTS.replaceAll("#",listOfProductIds));
    }
    
    
}
