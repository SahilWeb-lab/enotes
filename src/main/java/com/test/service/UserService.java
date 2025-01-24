package com.test.service;

import com.test.dto.LoginRequest;
import com.test.dto.LoginResponse;
import com.test.dto.UserRequest;
import com.test.model.User;

public interface UserService {
	
//	Create a method to register the user:
	public Boolean registerUser(UserRequest userDTO, String url) throws Exception;
	
	public LoginResponse loginUser(LoginRequest loginRequest);
	
}
