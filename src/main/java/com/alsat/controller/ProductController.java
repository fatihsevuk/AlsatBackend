package com.alsat.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alsat.domain.Product;
import com.alsat.domain.User;
import com.alsat.service.ProductService;
import com.alsat.service.UserService;

@RestController
@RequestMapping("/product")
public class ProductController {

	

	@Autowired
	private ProductService productService;

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public Product addNewProduct(@RequestBody Product product){

		User user = userService.findByUsername(product.getUsername());
		product.setUser(user);
		product.setShopName(user.getShopName());

		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
		String date = formatter.format(new Date());

		product.setAddedDate(date);
		return productService.save(product);

	}

	@RequestMapping(value = "/add/image", method = RequestMethod.POST)
	public ResponseEntity upload(@RequestParam("id") Long id, HttpServletResponse response,
			HttpServletRequest request) {

		try {

			Product product = productService.findOne(id);

			MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;

			
				
				Iterator<String> it = multipartHttpServletRequest.getFileNames();
			
				MultipartFile multipartFile = multipartHttpServletRequest.getFile(it.next());
			
			
				String fileName = id + ".png";
				
				byte[] bytes = multipartFile.getBytes();
				BufferedOutputStream stream = new BufferedOutputStream(
						
						new FileOutputStream(new File("src/main/resources/static/image/product/" + fileName)));
				stream.write(bytes);
				stream.close();
				return new ResponseEntity("Upload Successfully", HttpStatus.OK);

			

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity("Upload failed", HttpStatus.BAD_REQUEST);

		}
	}
	
	@RequestMapping("/productList")
	public List<Product> getProductList() {
		return productService.findAll();
	}
	
	@RequestMapping("/{id}")
	public Product getBook(@PathVariable("id") Long id){
		Product product = productService.findOne(id);
		return product;
	}

}
