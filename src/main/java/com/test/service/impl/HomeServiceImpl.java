package com.test.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.exception.ResourceNotFoundException;
import com.test.exception.SuccessException;
import com.test.model.AccountStatus;
import com.test.model.User;
import com.test.repository.UserRepository;

@Service
public class HomeServiceImpl implements HomeService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public Boolean verifyAccount(Integer userId, String verificationCode) throws Exception {
		User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Invalid User!"));
		
		if(user.getStatus().getVerificationCode() == null) {
			throw new SuccessException("User already verified!");
		}
		
		if(user.getStatus().getVerificationCode().equals(verificationCode)) {
			AccountStatus status = user.getStatus();
			status.setIsActive(true);
			status.setVerificationCode(null);
			
			userRepository.save(user);
			return true;
		}
		
		return false;
	}

}
