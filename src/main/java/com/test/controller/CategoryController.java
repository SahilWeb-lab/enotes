package com.test.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.dto.CategoryDTO;
import com.test.dto.CategoryResponse;
import com.test.model.Category;
import com.test.service.CategoryService;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	@PostMapping("/save-category")
	public ResponseEntity<?> saveCategory(@RequestBody CategoryDTO category) {
		Boolean saveCategory = categoryService.saveCategory(category);
		
		if(saveCategory) {
			return new ResponseEntity<>("Category Saved Successfully!", HttpStatus.CREATED);
		}
		
		return new ResponseEntity<>("Failed to save category!", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping("/categories")
	public ResponseEntity<?> getAllCategories() {
		List<CategoryDTO> allCategories = categoryService.getAllCategories();
		
		if(CollectionUtils.isEmpty(allCategories)) {
			return ResponseEntity.noContent().build();
		} 
		
		return new ResponseEntity<>(allCategories, HttpStatus.OK);
	}
	
//	Create a handler to show active categories:
	@GetMapping("/active-categories")
	public ResponseEntity<?> getActiveCategories() {
List<CategoryResponse> allCategories = categoryService.getActiveCategories();
		
		if(CollectionUtils.isEmpty(allCategories)) {
			return ResponseEntity.noContent().build();
		} 
		
		return new ResponseEntity<>(allCategories, HttpStatus.OK);
	}
}
