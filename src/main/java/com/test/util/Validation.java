package com.test.util;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.test.dto.CategoryDTO;
import com.test.dto.NotesDTO;
import com.test.dto.TodoDTO;
import com.test.dto.UserDTO;
import com.test.dto.TodoDTO.StatusDTO;
import com.test.enums.TodoStatus;
import com.test.exception.ExistDataException;
import com.test.exception.ValidationException;
import com.test.model.Role;
import com.test.repository.RoleRepository;
import com.test.repository.UserRepository;

@Component
public class Validation {
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	
//	Create a method for validation:
//	Category Validation:
	public void categoryValidation(CategoryDTO categoryDTO) {
		Map<String, Object> errors = new LinkedHashMap<>();
	
		if(ObjectUtils.isEmpty(categoryDTO)) {
			throw new IllegalArgumentException("Category object/JSON shouldn't be null or empty!");
		} else {
			
//			Name field validation
			if(ObjectUtils.isEmpty(categoryDTO.getName())) {
				errors.put("name", "Name field is empty or null!");
			} else {
				if(categoryDTO.getName().length() <= 3) {
					errors.put("name", "Name length min 3!");
				} 
				
				if(categoryDTO.getName().length() > 100) {
					errors.put("name", "Name length max 100!");
				}
			}
			
//			Description field validation
			if(ObjectUtils.isEmpty(categoryDTO.getDescription())) {
				errors.put("description", "Description field is empty or null!");
			} 
//			else {
//				if(categoryDTO.getDescription().length() < 10) {
//					errors.put("description", "Name length min 10!");
//				} 
//				
//				if(categoryDTO.getDescription().length() > 500) {
//					errors.put("description", "Name length max 500!");
//				}
//			}
			
//			IsActive field validation:
			if(ObjectUtils.isEmpty(categoryDTO.getIsActive())) {
				errors.put("isActive", "IsActive field is empty or null!");
			} else {
				if(categoryDTO.getIsActive() != Boolean.TRUE.booleanValue() && categoryDTO.getIsActive() != Boolean.FALSE.booleanValue()) {
					errors.put("isActive", "IsActive field must be a boolean value either 'TRUE' or 'FALSE'"); 
			}
		}
			
			if(!errors.isEmpty()) {
				throw new ValidationException(errors);
			}
	}
}
	
	
//	Notes Validation:
	public void notesValidation(NotesDTO notesDTO) throws IllegalAccessException {
		Map<String, Object> errors = new LinkedHashMap<>();
		
		if(ObjectUtils.isEmpty(notesDTO)) {
			throw new IllegalAccessException("Notes object/JSON shouldn't be null or empty!");
		} else {
			if(ObjectUtils.isEmpty(notesDTO.getTitle())) {
				errors.put("title", "Title field is empty or null!");
			} 
			
			if(notesDTO.getTitle().length() < 5) {
				errors.put("title", "Title length min 5!");
			}
			
			if(ObjectUtils.isEmpty(notesDTO.getDescription())) {
				errors.put("description", "Description field is empty or null!");
			}
			
			if(ObjectUtils.isEmpty(notesDTO.getCategory().getId())) {
				errors.put("category", "Category field is empty or null!");
			}
			
			if(!errors.isEmpty()) {
				throw new ValidationException(errors);
			}
		}
	}


//	TODO Validation:
	public void todoValidation(TodoDTO todoDTO) {
		StatusDTO statusDTO = todoDTO.getStatus();
		TodoStatus[] todoStatus = TodoStatus.values();
		
		Boolean status = false;
		
		for(TodoStatus st : todoStatus) {
			if(st.getId().equals(statusDTO.getId())) {
				status = true;
			}
		}
		
		if(!status)
			throw new IllegalArgumentException("Invalid Status!");
	}
	
	
	
//	Create a method for validating user:
	public void userValidation(UserDTO userDTO) {
		
		if(!StringUtils.hasText(userDTO.getFirstName())) {
			throw new IllegalArgumentException("First name can't be null!");
		}
		
		if(!StringUtils.hasText(userDTO.getLastName())) {
			throw new IllegalArgumentException("Last name can't be null!");
		}
		
		if(!StringUtils.hasText(userDTO.getEmail()) || userDTO.getEmail().contains(Constants.EMAIL_REGEX)) {
			throw new IllegalArgumentException("Email address is invalid!");
		} else {
			
			Boolean existsByEmail = userRepository.existsByEmail(userDTO.getEmail());
			
			if(existsByEmail)
				throw new ExistDataException(userDTO.getEmail() +  " already exists!");
		}
		
		if(!StringUtils.hasText(userDTO.getMobileNo()) || userDTO.getMobileNo().contains(Constants.MOBNO_REGEX)) {
			throw new IllegalArgumentException("Mobile number is invalid!");
		}
		
		if(!StringUtils.hasText(userDTO.getPassword())) {
			throw new IllegalArgumentException("Password can't be empty!");
		} else {
			if(userDTO.getPassword().length() <= 6) {
				throw new IllegalArgumentException("Password must be atleat 6 characters long!");
			}
		}
		
		if(CollectionUtils.isEmpty(userDTO.getRoles())) {
			throw new IllegalArgumentException("Role is invalid!");
		} else {			
			List<Integer> roles = roleRepository.findAll().stream().map(role -> role.getId()).toList();
			List<Integer> invalidRoles = userDTO.getRoles().stream().map(role -> role.getId()).filter(roleId -> !roles.contains(roleId)).toList();
			
			if(!CollectionUtils.isEmpty(invalidRoles)) {
				throw new IllegalArgumentException("Invalid Roles : " + invalidRoles);
			}
			
		}
		
	}
}