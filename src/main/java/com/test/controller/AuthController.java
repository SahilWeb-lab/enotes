package com.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.dto.UserDTO;
import com.test.service.UserService;
import com.test.util.CommonUtils;

@RestController
@RequestMapping("/api/v1/user")
public class AuthController {

	@Autowired
	private UserService userService;
	
	@PostMapping("/save")
	public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO) {
		Boolean status = userService.registerUser(userDTO);
		
		if(status)
			return CommonUtils.createBuildResponseMessage("User registered successfully!", HttpStatus.CREATED);
		
		return CommonUtils.createErrorResponseMessage("Failed to register!", HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
}
