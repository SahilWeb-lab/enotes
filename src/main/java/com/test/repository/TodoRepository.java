package com.test.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.model.Todo;

public interface TodoRepository extends JpaRepository<Todo, Integer> {
	public List<Todo> findByCreatedBy(Integer userId);
}
