package com.test.endpoint;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.test.dto.PasswordChangeRequest;

@RequestMapping("/api/v1/user")
public interface UserEndpoint {
	
	@GetMapping("/profile")
	public ResponseEntity<?> userProfile();
	
	@PostMapping("/change-password")
	public ResponseEntity<?> changePassword(@RequestBody PasswordChangeRequest changeRequest);
	
}
