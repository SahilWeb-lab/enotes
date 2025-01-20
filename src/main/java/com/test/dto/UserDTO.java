package com.test.dto;

import java.util.List;

import com.test.model.Role;

import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserDTO {
	
	private Integer id;

	private String firstName;

	private String lastName;

	private String email;

	private String mobileNo;

	private String password;

	private List<Role> roles;
	
	@AllArgsConstructor
	@NoArgsConstructor
	@Getter
	@Setter
	@Builder
	public static class RoleDTO {
		private Integer id;
		
		private String name;
	}
}
