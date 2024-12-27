package com.test.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.test.model.Category;
import com.test.repository.CategoryRepository;
import com.test.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public Boolean saveCategory(Category category) {
		category.setIsDeleted(false);
		category.setCreatedBy(1);
		category.setIsActive(true);
		Category saveCategory = categoryRepository.save(category);
		
		if(!ObjectUtils.isEmpty(saveCategory)) {
			return true;
		}
		
		return false;
	}

	@Override
	public List<Category> getAllCategories() {
		List<Category> categories = categoryRepository.findAll();
		return categories;
	}

}
