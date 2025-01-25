package com.test.service;

import com.test.dto.PasswordChangeRequest;
import com.test.dto.PasswordResetRequest;

import jakarta.servlet.http.HttpServletRequest;

public interface UserService {
//	Create a method to change the password:
	public void changePassword(PasswordChangeRequest passwordChangeRequest);
	
//	Create a method to send password reset email:
	public void sendPasswordResetEmail(String email, HttpServletRequest request) throws Exception;
	
//	Create a method to verify password reset link:
	public void verifyPassResetLink(Integer uid, String vcode) throws Exception;
	
//	Create a method to reset password:
	public void passwordReset(PasswordResetRequest resetRequest) throws Exception;
}
