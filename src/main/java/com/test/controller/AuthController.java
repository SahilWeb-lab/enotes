package com.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.dto.LoginRequest;
import com.test.dto.LoginResponse;
import com.test.dto.UserRequest;
import com.test.service.UserService;
import com.test.util.CommonUtils;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

	@Autowired
	private UserService userService;
	
	@PostMapping("/save")
	public ResponseEntity<?> registerUser(@RequestBody UserRequest userDTO, HttpServletRequest request) throws Exception {
		String url = CommonUtils.getUrl(request);
		Boolean status = userService.registerUser(userDTO, url);
		
		if(status)
			return CommonUtils.createBuildResponseMessage("User registered successfully!", HttpStatus.CREATED);
		
		return CommonUtils.createErrorResponseMessage("Failed to register!", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
		LoginResponse loginUser = userService.loginUser(loginRequest);
		
		if(ObjectUtils.isEmpty(loginUser)) {
			return CommonUtils.createErrorResponseMessage("Invalid User!", HttpStatus.BAD_REQUEST);
		}
		
		return CommonUtils.createBuildResponse(loginUser, HttpStatus.OK);
	}
	
}
