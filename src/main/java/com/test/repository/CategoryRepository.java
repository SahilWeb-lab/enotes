package com.test.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
	public List<Category> findByIsActiveTrueAndIsDeletedFalse();

	public Optional<Category> findByIdAndIsDeletedFalse(Integer id);
	
	public List<Category> findByIsDeletedFalse();
}
