package com.test.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.test.dto.PasswordChangeRequest;
import com.test.model.User;
import com.test.repository.UserRepository;
import com.test.service.UserService;
import com.test.util.CommonUtils;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public void changePassword(PasswordChangeRequest passwordChangeRequest) {
		User loggedInUser = CommonUtils.getLoggedInUser();
		
		if(!passwordEncoder.matches(passwordChangeRequest.getOldPassword(), loggedInUser.getPassword())) {
			throw new IllegalArgumentException("Old password is incorrect!");
		}
		
		loggedInUser.setPassword(passwordEncoder.encode(passwordChangeRequest.getNewPassword()));
		userRepository.save(loggedInUser);
	}

}
