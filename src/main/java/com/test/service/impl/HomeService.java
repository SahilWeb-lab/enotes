package com.test.service.impl;

public interface HomeService {
	public Boolean verifyAccount(Integer userId, String verificationCode) throws Exception;
}
