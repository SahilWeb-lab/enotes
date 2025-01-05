package com.test.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.test.model.Notes;

public interface NotesRepository extends JpaRepository<Notes, Integer> {
	public Boolean existsByTitle(String title);
	
	public Page<Notes> findByCreatedByAndIsDeletedFalse(Integer userId, Pageable pageable);
	
	public List<Notes> findByCreatedByAndIsDeletedTrue(Integer userId);
	
	public List<Notes> findByIsDeletedTrueAndDeletedOnBefore(LocalDateTime cutOffDate);
}
