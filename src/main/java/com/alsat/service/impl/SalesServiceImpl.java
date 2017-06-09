package com.alsat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alsat.domain.Sales;
import com.alsat.repository.SalesRepository;
import com.alsat.service.SalesService;

@Service
public class SalesServiceImpl implements SalesService{
	
	@Autowired
	private SalesRepository salesRepository;
	
	
	
	@Override
	public void addSales(Sales sales) {
		
		salesRepository.save(sales);
		
	}

	@Override
	public List<Sales> getSalesByCustomerId(Long id) {
		
		return salesRepository.getSalesByCustomerId(id);
	}

	@Override
	public List<Sales> getSalesByOwnerId(Long id) {
		
		return salesRepository.getSalesByOwnerId(id);
	}

}
