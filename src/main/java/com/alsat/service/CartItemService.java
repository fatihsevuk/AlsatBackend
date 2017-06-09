package com.alsat.service;

import java.util.List;

import com.alsat.domain.CartItem;
import com.alsat.domain.Product;
import com.alsat.domain.ProductRequest;
import com.alsat.domain.ShoppingCart;
import com.alsat.domain.User;

public interface CartItemService {
	
	CartItem addProductToCartItem(Product product , User user , int qty);
	
	List<CartItem> findByShoppingCart(ShoppingCart shoppingCart);
	
	CartItem updateCartItem(CartItem cartItem);
	
	void removeCartItem(CartItem cartItem);
	
	CartItem findById(Long id);
	
	CartItem save(CartItem cartItem);
	
	ProductRequest sendProductRequest(ProductRequest productRequest);
	
	
	
}
