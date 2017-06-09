package com.alsat.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.alsat.domain.Product;
import com.alsat.domain.ProductRequest;
import com.alsat.domain.User;



public interface ProductRequestRepository extends CrudRepository<ProductRequest, Long>{
	List<ProductRequest> findProductRequestByCustomer(User customer);
	List<ProductRequest> findProductRequestByProductOwner(Long ownerId);
	List<ProductRequest> findProductRequestByProduct(Product product);
	
	
	
}
