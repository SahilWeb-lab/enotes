package com.test.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.test.dto.CategoryDTO;
import com.test.dto.CategoryResponse;
import com.test.model.Category;
import com.test.repository.CategoryRepository;
import com.test.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public Boolean saveCategory(CategoryDTO categoryDTO) {
//		Category category = new Category();
//		category.setName(categoryDTO.getName());
//		category.setDescription(categoryDTO.getDescription());
//		category.setIsActive(categoryDTO.getIsActive());
		
		Category category = modelMapper.map(categoryDTO, Category.class);
		
		category.setIsDeleted(false);
		category.setCreatedBy(1);
		Category saveCategory = categoryRepository.save(category);
		
		if(!ObjectUtils.isEmpty(saveCategory)) {
			return true;
		}
		
		return false;
	}

	@Override
	public List<CategoryDTO> getAllCategories() {
		List<Category> categories = categoryRepository.findAll();
		
		List<CategoryDTO> list = categories.stream().map(cat -> modelMapper.map(cat, CategoryDTO.class)).toList();
		
		return list;
	}

	@Override
	public List<CategoryResponse> getActiveCategories() {
		List<Category> categories = categoryRepository.findByIsActiveTrue();
		List<CategoryResponse> list = categories.stream().map(cat -> modelMapper.map(cat, CategoryResponse.class)).toList();
		return list;
	}
	
	

}
