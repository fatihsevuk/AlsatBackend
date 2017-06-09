package com.alsat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alsat.domain.UserInfo;
import com.alsat.service.UserService;
import com.alsat.domain.Product;
import com.alsat.domain.User;

@RestController
@RequestMapping("/userInfo")
public class UserInfoController {
	
	@Autowired
	private UserService userService;
	
	
	@RequestMapping(value="/{id}")
	public UserInfo getInfo(@PathVariable("id") Long id){
		User user = userService.findOne(id);
		UserInfo userInfo=new UserInfo();
		
		userInfo.setUsername(user.getUsername());
		userInfo.setShopName(user.getShopName());
		userInfo.setEmail(user.getEmail());
		userInfo.setPhone(user.getPhone());
		userInfo.setFirstname(user.getFirstname());
		userInfo.setLastname(user.getLastname());
		
		return userInfo;
	}

	
}
