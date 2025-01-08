package com.test.util;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.test.dto.CategoryDTO;
import com.test.dto.NotesDTO;
import com.test.dto.TodoDTO;
import com.test.dto.TodoDTO.StatusDTO;
import com.test.enums.TodoStatus;
import com.test.exception.ValidationException;

@Component
public class Validation {
	
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
}