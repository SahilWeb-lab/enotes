package com.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.test.service.impl.HomeService;
import com.test.util.CommonUtils;

@RestController
@RequestMapping("/api/v1/home")
public class HomeController {
	
	@Autowired
	private HomeService homeService;

	@GetMapping("/verify")
	public ResponseEntity<?> verifyUserAccount(@RequestParam Integer uid, @RequestParam String vcode) throws Exception {
		
		System.out.println(vcode);
		Boolean verifyAccount = homeService.verifyAccount(uid, vcode);
		
		if(verifyAccount)
			return CommonUtils.createBuildResponseMessage("Account verification success!", HttpStatus.OK);
		
		return CommonUtils.createErrorResponseMessage("Invalid verification link!!", HttpStatus.BAD_REQUEST);
	}
	
}
