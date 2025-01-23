package com.test.service;

import com.test.model.User;

public interface JwtService {
	
	public String generateToken(User user);
	
}
