package com.alsat.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alsat.domain.Product;
import com.alsat.domain.ProductRequest;
import com.alsat.domain.User;
import com.alsat.service.ProductService;
import com.alsat.service.UserService;

@RestController
@RequestMapping("/shop")
public class MyShopController{
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/productList")
	public List<Product> getUserProductList(Principal  principal) {
		
		User user=userService.findByUsername(principal.getName());
		
		return productService.findByUser(user);
	}
	
	@RequestMapping("/user")
	public User getUserInfo(Principal  principal) {
		
		User user=userService.findByUsername(principal.getName());
		
		return user;
	}
	

	
	@RequestMapping("/requestedProducts")
	public List<ProductRequest> getRequestedProductList(Principal  principal){
		
		User user=userService.findByUsername(principal.getName());
		
		return productService.findRequestedProduct(user);
	}
	
	@RequestMapping("/desiredProducts")
	public List<ProductRequest> getDesiredProductList(Principal  principal){
		
		User user=userService.findByUsername(principal.getName());
		
		return productService.findDesiredProduct(user);
	}
	
	@RequestMapping(value = "/removeProductRequest", method = RequestMethod.POST)
	public ResponseEntity removeRequest(@RequestBody String id) {

		productService.removeProductRequest(Long.parseLong(id));

		return new ResponseEntity("Satış isteği iptal edildi.", HttpStatus.OK);
	}
	
	
	
	
	
	
}
