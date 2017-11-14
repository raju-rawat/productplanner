package com.org.productplanner.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.org.productplanner.beans.Product;
import com.org.productplanner.service.ProductService;

@Controller
@RequestMapping("/product")
public class ProductController {

	@Autowired
	ProductService productService;
	
	@RequestMapping(value="/generate/Id",method = RequestMethod.GET)
	public @ResponseBody Map<String,String> generateProductId()
	{
		return productService.generateID("PROD_", "PRODUCT_TBL", "productID");
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody boolean add(@RequestBody Product product)
	{
		return productService.addProduct(product);
		
	}
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody List<Product> get()
	{
		return productService.getProducts();
	}
	
	@RequestMapping(value="/update",method = RequestMethod.POST)
	public @ResponseBody void updateProducts(@RequestBody Map<String,Object> productMap)
	{
		productService.update(productMap);
	}
}
