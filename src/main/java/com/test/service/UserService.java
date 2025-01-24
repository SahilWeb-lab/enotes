package com.test.service;

import com.test.dto.PasswordChangeRequest;

public interface UserService {
//	Create a method to change the password:
	public void changePassword(PasswordChangeRequest passwordChangeRequest);
}
