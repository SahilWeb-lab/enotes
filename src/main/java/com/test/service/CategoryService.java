package com.test.service;

import java.util.List;

import com.test.dto.CategoryDTO;
import com.test.dto.CategoryResponse;
import com.test.exception.ResourceNotFoundException;

public interface CategoryService {
//	Create a method to save the category:
	public Boolean saveCategory(CategoryDTO category);
	
//	Create a method to get all the categories:
	public List<CategoryDTO> getAllCategories();
	
//	Create a method to get active categories:
	public List<CategoryResponse> getActiveCategories();
	
//	Create a method to get category by id:
	public CategoryDTO getCategoryById(Integer id) throws Exception;
	
//	Create a method to delete the category:
	public Boolean deleteCategory(Integer id);
}
