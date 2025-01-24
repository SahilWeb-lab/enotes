package com.test.dto;

import java.util.List;

import com.test.model.Role;

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
public class UserResponse {
	
	private Integer id;

	private String firstName;

	private String lastName;

	private String email;

	private String mobileNo;
	
	private StatusDTO status;

	private List<RoleDTO> role;
	
	@AllArgsConstructor
	@NoArgsConstructor
	@Getter
	@Setter
	@Builder
	public static class RoleDTO {
		private Integer id;
		
		private String name;
	}
	
	@AllArgsConstructor
	@NoArgsConstructor
	@Getter
	@Setter
	@Builder
	public static class StatusDTO {
		private Integer id;
		
		private Boolean isActive;
	}
}
