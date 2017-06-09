package com.alsat.service;

import java.util.Set;

import com.alsat.domain.User;
import com.alsat.domain.security.UserRole;

public interface UserService {
	
	User createUser(User user , Set<UserRole> userRole);
	User findByUsername(String username);
	User findByEmail(String email);
	User findOne(Long id);
	
	
	
	
}
