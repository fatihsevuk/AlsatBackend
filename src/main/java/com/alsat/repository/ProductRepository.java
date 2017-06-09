package com.alsat.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.alsat.domain.Product;
import com.alsat.domain.User;

public interface ProductRepository extends CrudRepository<Product, Long>{
	List<Product> findByUser(User user);
	List<Product> findByTitleContaining(String keyword);
	

}
