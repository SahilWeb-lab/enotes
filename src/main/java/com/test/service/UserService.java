package com.test.service;

import com.test.dto.LoginRequest;
import com.test.dto.LoginResponse;
import com.test.dto.UserDTO;
import com.test.model.User;

public interface UserService {
	
//	Create a method to register the user:
	public Boolean registerUser(UserDTO userDTO, String url) throws Exception;
	
	public LoginResponse loginUser(LoginRequest loginRequest);
	
}
