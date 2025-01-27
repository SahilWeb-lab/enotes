package com.test.endpoint;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.test.dto.LoginRequest;
import com.test.dto.UserRequest;

import jakarta.servlet.http.HttpServletRequest;

@RequestMapping("/api/v1/auth")
public interface AuthEnpoint {
	
	@PostMapping("/save")
	public ResponseEntity<?> registerUser(@RequestBody UserRequest userDTO, HttpServletRequest request) throws Exception;
	
	@PostMapping("/login")
	public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) throws Exception;
	
}
