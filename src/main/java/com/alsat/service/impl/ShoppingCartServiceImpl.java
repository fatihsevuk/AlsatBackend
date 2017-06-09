package com.alsat.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alsat.domain.CartItem;
import com.alsat.domain.ShoppingCart;
import com.alsat.repository.ShoppingCartRepository;
import com.alsat.service.CartItemService;
import com.alsat.service.ShoppingCartService;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService{
	
	@Autowired
	private CartItemService cartItemService;
	
	@Autowired
	private ShoppingCartRepository shoppingCartRepository;
	
	
	@Override
	public ShoppingCart updateShoppingCart(ShoppingCart shoppingCart) {
		
		BigDecimal cartTotal=new BigDecimal(0);
		
		List<CartItem> cartItemList=cartItemService.findByShoppingCart(shoppingCart);
		
		for (CartItem cartItem : cartItemList) {
			
			if(cartItem.getProduct().getInStockNumber()>0){
				cartItemService.updateCartItem(cartItem);
				cartTotal=cartTotal.add(cartItem.getSubtotal());
				
			}
		}
		
		shoppingCart.setGrandTotal(cartTotal);
		
		shoppingCartRepository.save(shoppingCart);
		
		return shoppingCart;
		
		
		
	}

	@Override
	public void clearShoppingCart(ShoppingCart shoppingCart) {
		List<CartItem> cartItemList=cartItemService.findByShoppingCart(shoppingCart);
		
		for (CartItem cartItem : cartItemList) {
			cartItem.setShoppingCart(null);
			cartItemService.save(cartItem);
		}
		
		shoppingCart.setGrandTotal(new BigDecimal(0));
		
		shoppingCartRepository.save(shoppingCart);
		
		
	}
	
}
