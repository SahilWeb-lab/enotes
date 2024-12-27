package com.test.service;

import java.util.List;

import com.test.model.Category;

public interface CategoryService {
//	Create a method to save the category:
	public Boolean saveCategory(Category category);
	
//	Create a method to get all the categories:
	public List<Category> getAllCategories();
}
