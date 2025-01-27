package com.test.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.dto.PasswordChangeRequest;
import com.test.dto.UserResponse;
import com.test.endpoint.UserEndpoint;
import com.test.model.User;
import com.test.service.UserService;
import com.test.util.CommonUtils;

@RestController
public class UserController implements UserEndpoint {
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserService userService;

	@Override
	public ResponseEntity<?> userProfile() {
		User loggedInUser = CommonUtils.getLoggedInUser();
		UserResponse response = modelMapper.map(loggedInUser, UserResponse.class);
		
		if(!ObjectUtils.isEmpty(response)) {
			return CommonUtils.createBuildResponse(response, HttpStatus.OK);
		}
		
		return CommonUtils.createBuildResponse(null, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@Override
	public ResponseEntity<?> changePassword(PasswordChangeRequest changeRequest) {
		userService.changePassword(changeRequest);
		return CommonUtils.createBuildResponseMessage("Password changed successfully!", HttpStatus.OK);
	}
 	
}
