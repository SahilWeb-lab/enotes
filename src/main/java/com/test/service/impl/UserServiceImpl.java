package com.test.service.impl;

import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.test.dto.EmailRequest;
import com.test.dto.UserDTO;
import com.test.model.AccountStatus;
import com.test.model.Role;
import com.test.model.User;
import com.test.repository.RoleRepository;
import com.test.repository.UserRepository;
import com.test.service.SendEmailService;
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
	
	@Autowired
	private SendEmailService emailService;
	
	@Override
	public Boolean registerUser(UserDTO userDTO, String url) throws Exception {
		
//		Call the method to validate user:
		validation.userValidation(userDTO);
		User user = modelMapper.map(userDTO, User.class);
		
		setRole(userDTO, user);
		
		AccountStatus accountStatus = AccountStatus.
				builder()
				.isActive(false)
				.verificationCode(UUID.randomUUID().toString()).
				build();
		
		user.setStatus(accountStatus);
		
		User saveUser = userRepository.save(user);
		
		if(!ObjectUtils.isEmpty(saveUser)) {
//			Send email:
			sendEmail(saveUser, url);
			return true;
		}
		
		return false;
	}

	private void sendEmail(User saveUser, String url) throws Exception {
		
		String msg = "Hi,<b>[[username]]</b> "
				+ "<br> Your account register sucessfully.<br>"
				+"<br> Click the below link verify & Active your account <br>"
				+"<a href='[[url]]'>Click Here</a> <br><br>"
				+"Thanks,<br>Enotes.com"
				;
		
		msg=msg.replace("[[username]]", saveUser.getFirstName());
		msg=msg.replace("[[url]]", url+"/api/v1/home/verify?uid="+saveUser.getId()+"&vcode="+saveUser.getStatus().getVerificationCode());
		
		System.out.println(url);
		
		EmailRequest request = EmailRequest.builder()
								.to(saveUser.getEmail())
								.title("Account Creation Confirmation!!")
								.subject("Please confirm you account!!!")
								.message(msg)
								.build();
		
		emailService.send(request);
	}

	private void setRole(UserDTO userDTO, User user) {
		List<Integer> roles = userDTO.getRoles().stream().map(role -> role.getId()).toList();
		List<Role> rolesById = roleRepository.findAllById(roles);
		user.setRole(rolesById);
	}

}
