package com.test.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.test.dto.PasswordResetRequest;
import com.test.service.UserService;
import com.test.service.impl.HomeService;
import com.test.util.CommonUtils;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/home")
public class HomeController {
	
	private static final Logger log = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private HomeService homeService;
	
	@Autowired
	private UserService userService;

	@GetMapping("/verify")
	public ResponseEntity<?> verifyUserAccount(@RequestParam Integer uid, @RequestParam String vcode) throws Exception {
		log.info("HomeController : verifyUserAccount : Execution Start");
		System.out.println(vcode);
		Boolean verifyAccount = homeService.verifyAccount(uid, vcode);
		
		if(verifyAccount)
			return CommonUtils.createBuildResponseMessage("Account verification success!", HttpStatus.OK);
		
		log.info("HomeController : verifyUserAccount : Execution End");
		return CommonUtils.createErrorResponseMessage("Invalid verification link!!", HttpStatus.BAD_REQUEST);
	}
	
//	Creating some handlers for reseting password:
	@GetMapping("/send-password-reset-email")
	public ResponseEntity<?> sendEmailForPasswordReset(@RequestParam String email, HttpServletRequest request) throws Exception {
		userService.sendPasswordResetEmail(email, request);
		return CommonUtils.createBuildResponseMessage("Reset password email sent successfully to " + email, HttpStatus.OK);
	}
	
	@GetMapping("/verify-password-reset-link")
	public ResponseEntity<?> verifyPasswordResetLink(@RequestParam Integer uid, @RequestParam
			 String vcode) throws Exception {
		userService.verifyPassResetLink(uid, vcode);
		return CommonUtils.createBuildResponseMessage("Verification success!", HttpStatus.OK);
	}
	
	@PostMapping("/reset-password")
	public ResponseEntity<?> resetPassword(@RequestBody PasswordResetRequest resetRequest) throws Exception {
			userService.passwordReset(resetRequest);
			return CommonUtils.createBuildResponseMessage("Password Reset Successfully!", HttpStatus.OK);
	}
}
