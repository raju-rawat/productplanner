package com.org.productplanner.service;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.org.productplanner.beans.Product;
import com.org.productplanner.repository.ProductRepository;

@Service
public class ProductService extends CommonService{
	
	@Autowired
	private ProductRepository productRepository;
	
    public boolean addProduct(Product product)
    {
    	product.setObjid(getNEXTObjId("PRODUCT_TBL"));
    	return productRepository.addProduct(product);
    }
    
    public List<Product> getProducts()
    {
    	return productRepository.getProducts();
    }
    
    @SuppressWarnings("unchecked")
	public void update(Map<String,Object> productMap)
    {
    	List<String> listOfProductIds=(List<String>) productMap.get("deletedProducts");
    	List<Product> listOfProducts=(List<Product>) productMap.get("updatedProducts");
    	if(listOfProductIds!=null && !listOfProductIds.isEmpty())
    	{
    		productRepository.deleteProducts(formatString(listOfProductIds));
    	}
    	if(listOfProducts!=null && !listOfProducts.isEmpty())
    	{
    		productRepository.updateProducts(listOfProducts);
    	}
    	
    }
    
    
}
