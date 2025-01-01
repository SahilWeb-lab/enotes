package com.test.util;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.test.dto.CategoryDTO;
import com.test.exception.ValidationException;

@Component
public class Validation {
	
	Logger logger = Logger.getLogger(Validation.class.getName());
	
//	Create a method for validation:
	public void categoryValidation(CategoryDTO categoryDTO) {
		Map<String, Object> errors = new LinkedHashMap<>();
	
		if(ObjectUtils.isEmpty(categoryDTO)) {
			throw new IllegalArgumentException("Category object/JSON shouldn't be null or empty!");
		} else {
			
//			Name field validation
			if(ObjectUtils.isEmpty(categoryDTO.getName())) {
				errors.put("name", "Name field is empty or null!");
			} else {
				if(categoryDTO.getName().length() < 10) {
					errors.put("name", "Name length min 10!");
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
}