package com.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.model.FileDetails;

public interface FileRepository extends JpaRepository<FileDetails, Integer> {
	
}
