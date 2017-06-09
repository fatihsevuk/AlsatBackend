package com.alsat.repository;

import org.springframework.data.repository.CrudRepository;

import com.alsat.domain.CartItem;
import com.alsat.domain.ProductRequest;
import com.alsat.domain.ProductToCartItem;

public interface ProductToCartItemRepository extends CrudRepository<ProductToCartItem, Long>{
	void deleteByCartItem(CartItem cartItem);
	
}
