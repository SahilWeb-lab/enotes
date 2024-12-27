package com.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
