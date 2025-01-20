package com.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	public Boolean existsByEmail(String email);
}
