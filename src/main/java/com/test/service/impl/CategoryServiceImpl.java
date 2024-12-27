package com.test.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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
		
		if(ObjectUtils.isEmpty(category.getId())) {			
			category.setIsDeleted(false);
			category.setCreatedBy(1);
		} else {
			updateCategory(category);
		}
				
		Category saveCategory = categoryRepository.save(category);
		if(!ObjectUtils.isEmpty(saveCategory)) {
			return true;
		}
		
		return false;
	}

	private void updateCategory(Category category) {
		Optional<Category> existingCategory = categoryRepository.findById(category.getId());
		if(existingCategory.isPresent()) {
			Category myCategory = existingCategory.get();
			category.setCreatedBy(myCategory.getCreatedBy());
			category.setCreatedOn(myCategory.getCreatedOn());
			category.setIsDeleted(myCategory.getIsDeleted());
			category.setUpdatedBy(1);
			category.setUpdatedOn(new Date());
		}
	}

	@Override
	public List<CategoryDTO> getAllCategories() {
		List<Category> categories = categoryRepository.findByIsDeletedFalse();
		
		List<CategoryDTO> list = categories.stream().map(cat -> modelMapper.map(cat, CategoryDTO.class)).toList();
		
		return list;
	}

	@Override
	public List<CategoryResponse> getActiveCategories() {
		List<Category> categories = categoryRepository.findByIsActiveTrueAndIsDeletedFalse();
		List<CategoryResponse> list = categories.stream().map(cat -> modelMapper.map(cat, CategoryResponse.class)).toList();
		return list;
	}

	@Override
	public CategoryDTO getCategoryById(Integer id) {
		Optional<Category> category = categoryRepository.findByIdAndIsDeletedFalse(id);
		
		if(category.isPresent()) {
			Category category2 = category.get();
			CategoryDTO categoryDTO = modelMapper.map(category2, CategoryDTO.class);
			return categoryDTO;
		}
		
		return null;
	}

	@Override
	public Boolean deleteCategory(Integer id) {
		Optional<Category> category = categoryRepository.findById(id);
		
		if(category.isPresent()) {
			Category category2 = category.get();
			category2.setIsDeleted(true);
			categoryRepository.save(category2);
			return true;
		}
		
		return false;
	}
	
	

}
