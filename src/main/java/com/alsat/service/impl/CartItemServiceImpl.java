package com.alsat.service.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alsat.domain.CartItem;
import com.alsat.domain.Product;
import com.alsat.domain.ProductRequest;
import com.alsat.domain.ProductToCartItem;
import com.alsat.domain.ShoppingCart;
import com.alsat.domain.User;
import com.alsat.repository.CartItemRepository;
import com.alsat.repository.ProductRequestRepository;
import com.alsat.repository.ProductToCartItemRepository;
import com.alsat.service.CartItemService;

@Service
public class CartItemServiceImpl implements CartItemService{
	
	@Autowired
	private CartItemRepository cartItemRepository;
	
	@Autowired
	private ProductToCartItemRepository productToCartItemRepository;
	
	@Autowired
	private ProductRequestRepository productRequestRepository;
	
	
	@Override
	public CartItem addProductToCartItem(Product product, User user, int qty) {
		
		List<CartItem> cartItemList=findByShoppingCart(user.getShoppingCart());
		
		for (CartItem cartItem : cartItemList) {
			if(product.getId() == cartItem.getProduct().getId()){
				cartItem.setQty(cartItem.getQty()+qty);
				cartItem.setSubtotal(new BigDecimal(product.getPrice()).multiply(new BigDecimal(qty)));
				cartItemRepository.save(cartItem);
				return cartItem;
			}
		}
		
		CartItem cartItem=new CartItem();
		cartItem.setShoppingCart(user.getShoppingCart());
		cartItem.setProduct(product);
		cartItem.setQty(qty);
		cartItem.setSubtotal(new BigDecimal(product.getPrice()).multiply(new BigDecimal(qty)));
		
		cartItem=cartItemRepository.save(cartItem);
		
		ProductToCartItem productToCartItem=new ProductToCartItem();
		productToCartItem.setCartItem(cartItem);
		productToCartItem.setProduct(product);
		
		productToCartItemRepository.save(productToCartItem);
		
		
		return cartItem;
	}

	@Override
	public List<CartItem> findByShoppingCart(ShoppingCart shoppingCart) {
		
		return cartItemRepository.findByShoppingCart(shoppingCart);
	}

	@Override
	public CartItem updateCartItem(CartItem cartItem) {
		BigDecimal bigDecimal=new BigDecimal(cartItem.getProduct().getPrice()).multiply(new BigDecimal(cartItem.getQty()));
		bigDecimal = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
		cartItem.setSubtotal(bigDecimal);
		
		cartItemRepository.save(cartItem);
		return cartItem;
	}
	
	
	
	
	
	
	
	
	

	@Transactional
	public void removeCartItem(CartItem cartItem) {
		productToCartItemRepository.deleteByCartItem(cartItem);
		cartItemRepository.delete(cartItem);
			
	}

	@Override
	public CartItem findById(Long id) {
		return cartItemRepository.findOne(id);
	}

	@Override
	public CartItem save(CartItem cartItem) {
		// TODO Auto-generated method stub
		return cartItemRepository.save(cartItem);
	}

	@Override
	public ProductRequest sendProductRequest(ProductRequest productRequest) {
		
		return productRequestRepository.save(productRequest);
	}

}
