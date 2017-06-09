package com.alsat.controller;

import java.math.BigDecimal;
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
import com.alsat.domain.Sales;
import com.alsat.domain.User;
import com.alsat.service.ProductService;
import com.alsat.service.SalesService;
import com.alsat.service.UserService;

@RestController
@RequestMapping("/sales")
public class SalesController {
	
	@Autowired
	private SalesService salesService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ResponseEntity addSales(@RequestBody Sales sales, Principal principal){
			
		Product product=productService.findOne(sales.getProductId());
		User ownerUser=userService.findByUsername(principal.getName());
		
		if(product.getInStockNumber()!=0){
			
			int stockNumber=product.getInStockNumber();
			stockNumber--;
			product.setInStockNumber(stockNumber);
			
			sales.setTotalCost(new BigDecimal(product.getPrice()));
			sales.setOwnerId(ownerUser.getId());
			productService.save(product);
			salesService.addSales(sales);
			
			return new ResponseEntity("Satış onaylandı" , HttpStatus.OK);
			
			
		}else{
			return new ResponseEntity("Ürün Stokta yok" ,HttpStatus.BAD_REQUEST);
		}
		
		
		
		
	}

	@RequestMapping(value = "/sold", method = RequestMethod.GET)
	public List<Sales> findOwnerSales(Principal principal){
		
		User user=userService.findByUsername(principal.getName());
		
		return salesService.getSalesByOwnerId(user.getId());
	}
	
	@RequestMapping(value = "/got", method = RequestMethod.GET)
	public List<Sales> findCustomerSales(Principal principal){
		
		User user=userService.findByUsername(principal.getName());
		
		return salesService.getSalesByCustomerId(user.getId());
	}
	
	
	

}
