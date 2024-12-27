package com.test.service;

import java.util.List;

import com.test.dto.CategoryDTO;
import com.test.dto.CategoryResponse;

public interface CategoryService {
//	Create a method to save the category:
	public Boolean saveCategory(CategoryDTO category);
	
//	Create a method to get all the categories:
	public List<CategoryDTO> getAllCategories();
	
//	Create a method to get active categories:
	public List<CategoryResponse> getActiveCategories();
}
