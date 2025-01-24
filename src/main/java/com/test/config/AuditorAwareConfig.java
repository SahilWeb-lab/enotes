package com.test.config;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;

import com.test.model.User;
import com.test.util.CommonUtils;

public class AuditorAwareConfig implements AuditorAware<Integer> {

	@Override
	public Optional<Integer> getCurrentAuditor() {
		User loggedInUser = CommonUtils.getLoggedInUser();
		return Optional.of(loggedInUser.getId());
	}
	
}
