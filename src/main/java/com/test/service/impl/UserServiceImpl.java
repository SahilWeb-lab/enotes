package com.test.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.test.dto.EmailRequest;
import com.test.dto.PasswordChangeRequest;
import com.test.dto.PasswordResetRequest;
import com.test.exception.ResourceNotFoundException;
import com.test.model.User;
import com.test.repository.UserRepository;
import com.test.service.SendEmailService;
import com.test.service.UserService;
import com.test.util.CommonUtils;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private SendEmailService sendEmail;

	@Override
	public void changePassword(PasswordChangeRequest passwordChangeRequest) {
		User loggedInUser = CommonUtils.getLoggedInUser();

		if (!passwordEncoder.matches(passwordChangeRequest.getOldPassword(), loggedInUser.getPassword())) {
			throw new IllegalArgumentException("Old password is incorrect!");
		}

		loggedInUser.setPassword(passwordEncoder.encode(passwordChangeRequest.getNewPassword()));
		userRepository.save(loggedInUser);
	}

	@Override
	public void sendPasswordResetEmail(String email, HttpServletRequest request) throws Exception {
		User user = userRepository.findByEmail(email);

		if (ObjectUtils.isEmpty(user)) {
			throw new ResourceNotFoundException("Inavlid Email Address!");
		}

		String url = CommonUtils.getUrl(request);
		
//		Generate password reset token:
		String passwordResetToken = UUID.randomUUID().toString();
		
		user.getStatus().setPasswordResetToken(passwordResetToken);
		User saveUser = userRepository.save(user);
		
		sendEmailRequest(saveUser, url);
	}

	private void sendEmailRequest(User saveUser, String url) throws Exception {

		String message = "Hi <b>[[username]]</b> " + "<br><p>You have requested to reset your password.</p>"
				+ "<p>Click the link below to change your password:</p>"
				+ "<p><a href=[[url]]>Change my password</a></p>"
				+ "<p>Ignore this email if you do remember your password, "
				+ "or you have not made the request.</p><br>" + "Thanks,<br>Enotes.com";

		message = message.replace("[[username]]", saveUser.getFirstName());
		message = message.replace("[[url]]", url + "/api/v1/home/verify-password-reset-link?uid=" + saveUser.getId() + "&vcode=" + saveUser.getStatus().getPasswordResetToken());
		
		EmailRequest emailRequest = EmailRequest
				.builder()
				.to(saveUser.getEmail())
				.subject("Password Reset link")
				.title("Password Reset")
				.message(message)
				.build();
		
		sendEmail.send(emailRequest);
	}

	@Override
	public void verifyPassResetLink(Integer uid, String vcode) throws Exception {
		User user = userRepository.findById(uid).orElseThrow(() -> new ResourceNotFoundException("Invalid User!"));
		
		verifyPasswordResetToken(user.getStatus().getPasswordResetToken(), vcode);
	}

	private void verifyPasswordResetToken(String passwordResetToken, String vcode) {
		
		if(!StringUtils.hasText(passwordResetToken)) {
			throw new IllegalArgumentException("Already password reset!");
		}
		
		if(StringUtils.hasText(vcode)) {
			if(!vcode.equals(passwordResetToken)) {				
				throw new IllegalArgumentException("Invalid URL!");
			}
		} else {
			throw new IllegalArgumentException("Invalid Token!");
		}
		
	}

	@Override
	public void passwordReset(PasswordResetRequest resetRequest) throws Exception {
		User user = userRepository.findById(resetRequest.getUid()).orElseThrow(() -> new ResourceNotFoundException("Invalid User!"));
		user.setPassword(passwordEncoder.encode(resetRequest.getNewPassword()));
		user.getStatus().setPasswordResetToken(null);
		userRepository.save(user);
	}
	
}
