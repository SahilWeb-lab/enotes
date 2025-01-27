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
import com.test.endpoint.AuthEnpoint;
import com.test.service.AuthService;
import com.test.util.CommonUtils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class AuthController implements AuthEnpoint {

	@Autowired
	private AuthService userService;
	
	@Override
	public ResponseEntity<?> registerUser(UserRequest userDTO, HttpServletRequest request) throws Exception {
		log.info("AuthController : registerUser() : Execution Start");
		String url = CommonUtils.getUrl(request);
		Boolean status = userService.registerUser(userDTO, url);
		
		if(!status) {
			log.info("Error : Failed to register");
			return CommonUtils.createErrorResponseMessage("Failed to register!", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		log.info("AuthController : registerUser() : Execution End");
		return CommonUtils.createBuildResponseMessage("User registered successfully!", HttpStatus.CREATED);
	}
	
	@Override
	public ResponseEntity<?> loginUser(LoginRequest loginRequest) throws Exception {
		log.info("AuthController : loginUser() : Execution Start");
		LoginResponse loginUser = userService.loginUser(loginRequest);
		
		if(ObjectUtils.isEmpty(loginUser)) {
			log.info("Error: Invalid User!");
			return CommonUtils.createErrorResponseMessage("Invalid User!", HttpStatus.BAD_REQUEST);
		}
		
		log.info("AuthController : loginUser() : Execution End");
		return CommonUtils.createBuildResponse(loginUser, HttpStatus.OK);
	}
	
}
