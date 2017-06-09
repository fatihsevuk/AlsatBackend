package com.alsat.domain;

import java.io.Serializable;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;



@Entity
public class ProductRequest implements Serializable{
	
	private static final long serialVersionUID=12312121L; 
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="user_id",  nullable=false)
	private User customer;
	
	@ManyToOne
	@JoinColumn(name="product_id" , nullable=false)
	private Product product;
	
	
	private Long productOwner;

	public Long getId(){
		return id;
	}

	public void setId(Long id){
		this.id = id;
	}

	public User getCustomer(){
		return customer;
	}

	public void setCustomer(User customer){
		this.customer = customer;
	}

	public Long getProductOwner(){
		return productOwner;
	}

	public void setProductOwner(Long productOwner){
		this.productOwner = productOwner;
	}

	public Product getProduct(){
		return product;
	}

	public void setProduct(Product product){
		this.product = product;
	}
	
	
	
	
}
