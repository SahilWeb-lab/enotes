package com.test.dto;

import com.test.model.User;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
	
	private User user;
	
	private String token;
	
}
