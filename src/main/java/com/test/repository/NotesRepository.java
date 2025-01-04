package com.test.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.test.model.Notes;

public interface NotesRepository extends JpaRepository<Notes, Integer> {
	public Boolean existsByTitle(String title);
	
	public Page<Notes> findByCreatedBy(Integer userId, Pageable pageable);
}
