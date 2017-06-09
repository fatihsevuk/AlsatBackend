package com.alsat.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.alsat.domain.Sales;

@Repository
public interface SalesRepository extends CrudRepository<Sales, Long>{
	List<Sales> getSalesByCustomerId(Long id);
	List<Sales> getSalesByOwnerId(Long id);

}
