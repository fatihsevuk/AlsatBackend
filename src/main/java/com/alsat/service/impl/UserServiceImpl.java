package com.alsat.service.impl;

import java.util.Set;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alsat.domain.ShoppingCart;
import com.alsat.domain.User;
import com.alsat.domain.security.UserRole;
import com.alsat.repository.RoleRepository;
import com.alsat.repository.UserRepository;
import com.alsat.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	private static final Logger LOG=LoggerFactory.getLogger(UserServiceImpl.class);
	
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Transactional
	public User createUser(User user, Set<UserRole> userRoles) {
		
		User localUser=userRepository.findByUsername(user.getUsername());
		
		if (localUser!=null) {
			LOG.info("User with username {} already exist.", user.getUsername());
			
		}else{
			for (UserRole ur : userRoles) {
				roleRepository.save(ur.getRole());
			}
			user.getUserRoles().addAll(userRoles);
			
			ShoppingCart shoppingCart=new ShoppingCart();
			shoppingCart.setUser(user);
			user.setShoppingCart(shoppingCart);
			
			localUser=userRepository.save(user);
		}
		
		return localUser;
	}

	@Override
	public User findByUsername(String username) {
		// TODO Auto-generated method stub
		return userRepository.findByUsername(username);
	}

	@Override
	public User findByEmail(String email) {
		
		return userRepository.findByEmail(email);
	}

	@Override
	public User findOne(Long id) {
		// TODO Auto-generated method stub
		return userRepository.findOne(id);
	}

}
