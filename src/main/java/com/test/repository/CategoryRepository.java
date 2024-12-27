package com.test.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
	public List<Category> findByIsActiveTrue();
}
