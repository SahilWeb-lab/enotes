package com.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

}
