package com.alsat.service;

import java.util.List;

import com.alsat.domain.Sales;

public interface SalesService {
	
	void addSales(Sales sales);
	List<Sales> getSalesByCustomerId(Long id);
	List<Sales> getSalesByOwnerId(Long id);
	
	

}
