package com.test.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.test.dto.UserDTO;
import com.test.model.Role;
import com.test.model.User;
import com.test.repository.RoleRepository;
import com.test.repository.UserRepository;
import com.test.service.UserService;
import com.test.util.Validation;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private Validation validation;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public Boolean registerUser(UserDTO userDTO) {
		
//		Call the method to validate user:
		validation.userValidation(userDTO);
		User user = modelMapper.map(userDTO, User.class);
		
		setRole(userDTO, user);
		
		User saveUser = userRepository.save(user);
		
		if(!ObjectUtils.isEmpty(saveUser))
			return true;
		
		return false;
	}

	private void setRole(UserDTO userDTO, User user) {
		List<Integer> roles = userDTO.getRoles().stream().map(role -> role.getId()).toList();
		List<Role> rolesById = roleRepository.findAllById(roles);
		user.setRole(rolesById);
	}

}
