package com.test.service.impl;

import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.test.config.CustomUserDetails;
import com.test.dto.EmailRequest;
import com.test.dto.LoginRequest;
import com.test.dto.LoginResponse;
import com.test.dto.UserRequest;
import com.test.dto.UserResponse;
import com.test.model.AccountStatus;
import com.test.model.Role;
import com.test.model.User;
import com.test.repository.RoleRepository;
import com.test.repository.UserRepository;
import com.test.service.JwtService;
import com.test.service.SendEmailService;
import com.test.service.AuthService;
import com.test.util.Validation;

@Service
public class AuthServiceImpl implements AuthService {

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
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtService jwtService;
	
	@Override
	public Boolean registerUser(UserRequest userDTO, String url) throws Exception {
		
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
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		User saveUser = userRepository.save(user);
		
		if(!ObjectUtils.isEmpty(saveUser)) {
//			Send email:
			sendEmailForRegistration(saveUser, url);
			return true;
		}
		
		return false;
	}

	private void sendEmailForRegistration(User saveUser, String url) throws Exception {
		
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

	private void setRole(UserRequest userDTO, User user) {
		List<Integer> roles = userDTO.getRoles().stream().map(role -> role.getId()).toList();
		List<Role> rolesById = roleRepository.findAllById(roles);
		user.setRole(rolesById);
	}

	@Override
	public LoginResponse loginUser(LoginRequest loginRequest) {
		Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
		
		if(authenticate.isAuthenticated()) {
			CustomUserDetails customUserDetails = (CustomUserDetails) authenticate.getPrincipal();
			
			String token = jwtService.generateToken(customUserDetails.getUser());
			
			LoginResponse loginResponse = LoginResponse.builder()
					.user(modelMapper.map(customUserDetails.getUser(), UserResponse.class))
					.token(token).build();
			
			return loginResponse;
		}
		
		return null;
	}

}
