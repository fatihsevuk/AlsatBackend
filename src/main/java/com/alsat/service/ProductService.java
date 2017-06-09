package com.alsat.service;

import java.util.List;

import com.alsat.domain.Product;
import com.alsat.domain.ProductRequest;
import com.alsat.domain.User;

public interface ProductService {
	
	List<Product> findAll();
	
	Product findOne(Long id);
	
	List<Product> findByUser(User user);
	
	Product save(Product product);
	
	List<Product> search(String title);
	
	void removeOne(Long id);
	
	List<ProductRequest> findRequestedProduct(User user);
	
	List<ProductRequest> findDesiredProduct(User user);
	
	void removeProductRequest(Long id);
	
	
 
}
