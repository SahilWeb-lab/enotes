package com.test.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.exception.ResourceNotFoundException;
import com.test.exception.SuccessException;
import com.test.model.AccountStatus;
import com.test.model.User;
import com.test.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class HomeServiceImpl implements HomeService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public Boolean verifyAccount(Integer userId, String verificationCode) throws Exception {
		log.info("HomeServiceImpl : verifyAccount() : Execution Start");
		User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Invalid User!"));
		
		if(user.getStatus().getVerificationCode() == null) {
			log.info("Message : Account already verified!");
			throw new SuccessException("Account already verified!");
		}
		
		if(user.getStatus().getVerificationCode().equals(verificationCode)) {
			AccountStatus status = user.getStatus();
			status.setIsActive(true);
			status.setVerificationCode(null);
			
			userRepository.save(user);
			log.info("Message : Account verification success");
			return true;
		}
		
		log.info("HomeServiceImpl : verifyAccount() : End Execution");
		return false;
	}

}
