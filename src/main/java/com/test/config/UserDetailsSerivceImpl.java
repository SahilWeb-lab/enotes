package com.test.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.test.model.User;
import com.test.repository.UserRepository;

@Service
public class UserDetailsSerivceImpl implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(username);
		
		if(ObjectUtils.isEmpty(user))
			throw new UsernameNotFoundException("Invalid Email Address!");
		
		return new CustomUserDetails(user);
	}

}
