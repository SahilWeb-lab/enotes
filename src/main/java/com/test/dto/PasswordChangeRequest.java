package com.test.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PasswordChangeRequest {
	
	private String oldPassword;
	
	private String newPassword;
	
}
