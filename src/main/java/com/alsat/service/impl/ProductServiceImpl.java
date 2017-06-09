package com.alsat.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alsat.domain.Product;
import com.alsat.domain.ProductRequest;
import com.alsat.domain.User;
import com.alsat.repository.ProductRepository;
import com.alsat.repository.ProductRequestRepository;
import com.alsat.repository.UserRepository;
import com.alsat.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ProductRequestRepository productRequestRepository;

	@Override
	public List<Product> findAll() {

		List<Product> productLis = (List<Product>) productRepository.findAll();

		List<Product> activeProductList = new ArrayList<>();

		for (Product product : productLis) {
			if (product.isActive()) {
				activeProductList.add(product);
			}
		}

		return activeProductList;
	}

	@Override
	public Product findOne(Long id) {
		// TODO Auto-generated method stub
		return productRepository.findOne(id);
	}

	@Override
	public Product save(Product product) {

		// TODO Auto-generated method stub
		return productRepository.save(product);
	}

	@Override
	public List<Product> search(String keyword) {

		List<Product> productLis = (List<Product>) productRepository.findByTitleContaining(keyword);

		List<Product> activeProductList = new ArrayList<>();

		for (Product product : productLis) {
			if (product.isActive()) {
				activeProductList.add(product);
			}
		}

		return activeProductList;
	}

	@Override
	public void removeOne(Long id) {
		productRepository.delete(id);

	}

	@Override
	public List<Product> findByUser(User user) {
		return productRepository.findByUser(user);
	}

	@Override
	public List<ProductRequest> findRequestedProduct(User user) {

		return productRequestRepository.findProductRequestByCustomer(user);
	}


	
	@Override
	public List<ProductRequest> findDesiredProduct(User user){

		return productRequestRepository.findProductRequestByProductOwner(user.getId());
	}

	@Override
	public void removeProductRequest(Long id) {
		productRequestRepository.delete(id);
		
	}

	
	
	


}
